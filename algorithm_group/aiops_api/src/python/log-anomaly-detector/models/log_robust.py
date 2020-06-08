# -*- coding: utf-8 -*-

import torch.nn as nn
from torch.nn.utils.rnn import pack_padded_sequence, pad_packed_sequence

from tools import *


class VariableLstm(nn.Module):
    """
    Dynamic LSTM module, which can handle variable length input sequence.

    Parameters
    ----------
    input_size : input size
    hidden_size : hidden size
    num_layers : number of hidden layers. Default: 1
    dropout : dropout rate. Default: 0.25

    Inputs
    ------
    input: tensor, shaped [batch, max_step, input_size]
    seq_lens: tensor, shaped [batch], sequence lengths of batch

    Outputs
    -------
    output: tensor, shaped (batch, max_step, num_directions * hidden_size),
         tensor containing the output features (h_t) from the last layer
         of the LSTM, for each t.
    """
    def __init__(self,
                 input_size,
                 hidden_size=128,
                 num_layers=2,
                 dropout=0.):
        super(VariableLstm, self).__init__()

        self.lstm = nn.LSTM(
            input_size, hidden_size, num_layers, bias=True,
            batch_first=True, dropout=dropout, bidirectional=True)

    def forward(self, x, seq_lens):
        # sort input by descending length
        _, idx_sorted = torch.sort(seq_lens, dim=0, descending=True)
        _, idx_unsorted = torch.sort(idx_sorted, dim=0)
        x_sort = torch.index_select(x, dim=0, index=idx_sorted)
        seq_lens_sort = torch.index_select(seq_lens, dim=0, index=idx_sorted)

        # pack input
        x_packed = pack_padded_sequence(
            x_sort, seq_lens_sort, batch_first=True)

        # pass through rnn
        y_packed, _ = self.lstm(x_packed)

        # unpack output
        y_sort, length = pad_packed_sequence(y_packed, batch_first=True)

        # unsort output to original order
        y = torch.index_select(y_sort, dim=0, index=idx_unsorted)

        return y


class LogRobust(nn.Module):
    def __init__(self, **config):
        super(LogRobust, self).__init__()

        hidden_size = config.get("hidden_size", 128)
        dropout = config.get("dropout", 0.35)
        num_layers = config.get("num_layers", 2)

        self.rnn = VariableLstm(config["embed_dim"],
                                hidden_size=hidden_size,
                                num_layers=num_layers,
                                dropout=dropout)

        self.fc_att = nn.Linear(hidden_size * 2, 1)

        self.fc = nn.Linear(hidden_size * 6, hidden_size)
        self.act = nn.ReLU()
        self.drop = nn.Dropout(dropout)
        self.out = nn.Linear(hidden_size, 2)

        # self.loss = nn.BCEWithLogitsLoss()

    def forward(self, e, seq_len):
        # mask
        # [batch_size, max_seq_len]
        mask = seq_mask(seq_len, torch.max(seq_len).item())

        # Bi-LSTM
        # [batch_size, max_seq_len, d] -> [batch_size, max_seq_len, h * 2]
        r = self.rnn(e, seq_len)

        # Attention
        # [b, msl, h * 2] -> [b, msl, 1] -> [b, msl]
        att = self.fc_att(r).squeeze(-1)
        # [b, msl]
        att = mask_softmax(att, mask)
        #    (b, msl)
        # -> (batch_size, msl, 1) (broadcast)
        # -> (batch_size, h * 2)
        r_att = torch.sum(att.unsqueeze(-1) * r, dim=1)

        # pooling
        # (batch_size, h * 2)
        r_avg = mask_mean(r, mask)
        # (batch_size, h * 2)
        r_max = mask_max(r, mask)
        # (batch_size, h * 6)
        r = torch.cat([r_avg, r_max, r_att], dim=-1)

        # feed-forward
        # [b, h * 6] -> [b, h]
        f = self.drop(self.act(self.fc(r)))
        # (b, h) -> (b, 2)
        logits = self.out(f).squeeze(-1)

        return logits

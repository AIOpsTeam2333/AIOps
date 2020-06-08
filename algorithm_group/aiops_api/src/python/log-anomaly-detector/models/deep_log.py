# -*- coding: utf8 -*-

import torch
import torch.nn as nn


class DeepLog(nn.Module):
    def __init__(self,
                 input_size,
                 hidden_size,
                 num_layers,
                 num_keys,
                 bidirectional=True):
        super(DeepLog, self).__init__()
        self.hidden_size = hidden_size
        self.num_layers = num_layers
        self.lstm = nn.LSTM(input_size,
                            hidden_size,
                            num_layers,
                            batch_first=True,
                            bidirectional=bidirectional)
        self.num_directions = 2 if bidirectional else 1
        self.fc = nn.Linear(hidden_size * 2, num_keys)

    def forward(self, features, device):
        inp = features[0]
        # num_layers * num_directions
        h0 = torch.randn(self.num_layers * self.num_directions,
                         inp.size(0), self.hidden_size).to(device)
        c0 = torch.randn(self.num_layers * self.num_directions,
                         inp.size(0), self.hidden_size).to(device)
        out, _ = self.lstm(inp, (h0, c0))
        out = self.fc(out[:, -1, :])
        return out

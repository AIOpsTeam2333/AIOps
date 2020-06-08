# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/6/5 12:34
@desc:
"""

import numpy as np
import torch
import torch.nn as nn
import torchtext
from torch.utils.data import DataLoader


class Predictor:
    """An interface for predicting outputs based on trained models.
    """
    def __init__(self, **config):
        self.batch_size = config.get("batch_size", 32)
        self.input_size = config['input_size']

        self.embed = nn.Embedding.from_pretrained(config["pretrained_embed"])
        self.embed.padding_idx = config["padding_idx"]

        self.model = config["model"]
        self.model_path = config['model_path']

        self.sequential_patterns = config['sequential_features']
        self.quantitative_patterns = config['quantitative_features']
        self.semantic_features = config['semantic_features']

        # self.device = torch.device(config['device'])
        self.device = "cuda" if torch.cuda.is_available() else "cpu"
        self.num_workers = config["num_workers"]

    def predict(self, dataset):
        # transfer model to gpu if available
        network = self.model.to(self.device)
        # network.load_state_dict(torch.load(self.model_path)['state_dict'])
        network.load_state_dict(torch.load(self.model_path))

        # turn on the testing mode; clean up the history
        network.eval()
        batch_output = []

        # data_iter = DataLoader(
        #     data, batch_size=self.batch_size,
        #     shuffle=False, num_workers=self.num_workers, pin_memory=True
        # )
        data_iter = torchtext.data.Iterator(
            dataset=dataset, batch_size=self.batch_size,
            train=False, device=self.device, sort=False
        )

        for batch in data_iter:
            seq, seq_len = batch.sequence
            with torch.no_grad():
                prediction = network(self.embed(seq), seq_len)
            batch_output.append(torch.argmax(prediction, dim=1).detach())

        return self._post_processor(batch_output)

    @staticmethod
    def _post_processor(batch_output):
        """Convert logit tensor to label."""
        y_pred = []
        for logit in batch_output:
            y_pred.append(logit.cpu().numpy())
        y_pred = np.concatenate(y_pred, axis=0)

        return y_pred

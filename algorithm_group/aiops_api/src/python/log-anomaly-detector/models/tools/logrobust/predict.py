# -*- coding: utf8 -*-

import numpy as np
import torch
import torchtext


class Predictor:
    """An interface for predicting outputs based on trained models.
    """

    def __init__(self, batch_size=8):
        self.batch_size = batch_size
        self.device = "cuda" if torch.cuda.is_available() else "cpu"

    def predict(self, network, data, threshold=0.33):
        # transfer model to gpu if available
        network = network.to(self.device)

        # turn on the testing mode; clean up the history
        network.eval()
        batch_output = []

        # define batch iterator
        data_iter = torchtext.data.Iterator(dataset=data,
                                            batch_size=self.batch_size,
                                            device=self.device,
                                            train=False,
                                            sort=False)

        for batch in data_iter:
            text = batch.text

            with torch.no_grad():
                prediction = network(*text)

            batch_output.append(prediction.detach())

        return self._post_processor(batch_output, threshold)

    @staticmethod
    def _post_processor(batch_output, threshold=0.5):
        """Convert logit tensor to label."""
        y_preds = []
        for logit in batch_output:
            y_pred = (torch.sigmoid(logit) > threshold).long().cpu().numpy()
            y_preds.append(y_pred)
        y_pred = np.concatenate(y_preds, axis=0)

        return y_pred

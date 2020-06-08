# -*- coding: utf8 -*-

import numpy as np
from sklearn import metrics
import torch
import torchtext


class Tester:
    def __init__(self, **kwargs):
        self.batch_size = kwargs["batch_size"]
        self.device = "cuda" if torch.cuda.is_available() else "cpu"

    def test(self, network, dev_data, threshold=0.33):
        # transfer model to gpu if available
        network = network.to(self.device)

        # turn on the testing mode; clean up the history
        network.eval()
        output_list = []
        truth_list = []

        # define batch iterator
        data_iter = torchtext.data.Iterator(
            dataset=dev_data, batch_size=self.batch_size,
            train=False, device=self.device, sort=False)

        # predict
        for batch in data_iter:
            text, target = batch.text, batch.target

            with torch.no_grad():
                prediction = network(*text)

            output_list.append(prediction.detach())
            truth_list.append(target.detach())

        # evaluate
        eval_results = self.evaluate(output_list, truth_list, threshold)
        print("[tester] {}".format(self.print_eval_results(eval_results)))

        return eval_results

    def evaluate(self, predict, truth, threshold=0.33):
        """Compute evaluation metrics.

        :param predict: list of Tensor
        :param truth: list of dict
        :param threshold: threshold of positive probability
        :return eval_results: dict, format {name: metrics}.
        """
        y_trues, y_preds = [], []
        for y_true, logit in zip(truth, predict):
            y_pred = (torch.sigmoid(logit) > threshold).long().cpu().numpy()
            y_true = y_true.cpu().numpy()
            y_trues.append(y_true)
            y_preds.append(y_pred)
        y_true = np.concatenate(y_trues, axis=0)
        y_pred = np.concatenate(y_preds, axis=0)

        precision = metrics.precision_score(y_true, y_pred, pos_label=1)
        recall = metrics.recall_score(y_true, y_pred, pos_label=1)
        f1 = metrics.f1_score(y_true, y_pred, pos_label=1)

        metrics_dict = {"precision": precision, "recall": recall, "f1": f1}

        return metrics_dict

    def print_eval_results(self, results):
        """Override this method to support more print formats.
        :param results: dict, (str: float) is (metrics name: value)
        """
        return ", ".join([str(key) + "=" + "{:.4f}".format(value)
                          for key, value in results.items()])

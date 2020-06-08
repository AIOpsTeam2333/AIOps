# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/6/5 12:29
@desc:
"""

import sys

import torch
import torch.nn as nn
import torchtext
from sklearn import metrics
import numpy as np


class Evaluator(object):
    def __init__(self, **kwargs):
        self.batch_size = kwargs["batch_size"]

        self.embed = nn.Embedding.from_pretrained(kwargs["pretrained_embed"])
        self.embed.padding_idx = kwargs["padding_idx"]

        self.model = kwargs["model"]

        self.device = "cuda" if torch.cuda.is_available() else 'cpu'

    def eval(self, data, output_file=sys.stdout):
        # transfer model to gpu if available
        network = self.model.to(self.device)

        # turn on the testing mode; clean up the history
        network.eval()
        output_list = []
        truth_list = []

        # define batch iterator
        data_iter = torchtext.data.Iterator(
            dataset=data, batch_size=self.batch_size,
            train=False, device=self.device, sort=False
        )

        # predict
        for batch in data_iter:
            (seq, seq_len), target = batch.sequence, batch.label
            with torch.no_grad():
                prediction = network(self.embed(seq), seq_len)
            output_list.append(torch.argmax(prediction, dim=1).detach())
            truth_list.append(target.detach())

        # evaluate
        eval_results = self._calculate_eval_metrics(output_list, truth_list)
        print(f"[eval] {self._eval_results_str(eval_results)}", file=output_file)

        return eval_results

    @staticmethod
    def _calculate_eval_metrics(predict, truth):
        """Compute evaluation metrics.

        :param predict: list of Tensor
        :param truth: list of dict
        :return eval_results: dict, format {name: metrics}.
        """
        y_trues, y_preds = [], []
        for y_true, logit in zip(truth, predict):
            y_trues.append(y_true.cpu().numpy())
            y_preds.append(logit.cpu().numpy())
        y_true = np.concatenate(y_trues, axis=0)
        y_pred = np.concatenate(y_preds, axis=0)
        TP, FP, TN, FN = (0,) * 4
        for x, y in zip(y_true, y_pred):
            if x == 1 and y == 1:
                TP += 1
            elif x == 0 and y == 0:
                TN += 1
            elif x == 1 and y == 0:
                FN += 1
            else:
                FP += 1
        print(y_true.sum(), y_pred.sum())
        print(f"TP = {TP}, FP = {FP}, TN = {TN}, FN = {FN}")
        diff = []
        for i, (x, y) in enumerate(zip(y_true, y_pred)):
            xor = x ^ y
            if xor == 1:
                diff.append(i)
        print(f"Differences at {diff}")
        precision = metrics.precision_score(y_true, y_pred, pos_label=1)
        recall = metrics.recall_score(y_true, y_pred, pos_label=1)
        f1 = metrics.f1_score(y_true, y_pred, pos_label=1)

        metrics_dict = {"precision": precision, "recall": recall, "f1": f1}

        return metrics_dict

    @staticmethod
    def _eval_results_str(results):
        """Override this method to support more print formats.
        :param results: dict, (str: float) is (metrics name: value)
        """
        return ", ".join([
            f"{str(key)}={value:.4f}" for key, value in results.items()])

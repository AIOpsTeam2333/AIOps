# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/6/5 12:29
@desc:
"""

from datetime import timedelta
import logging
import time

import torch
import torch.nn as nn
import torchtext

from .eval import Evaluator
from tools import save_model


class Trainer:
    def __init__(self, **config):
        self.n_epochs = config["epochs"]
        self.batch_size = config["batch_size"]
        self.validate = config["validate"]
        self.save_best_dev = config["save_best_dev"]
        self.print_every_step = config["print_every_step"]
        self.optimizer = config["optimizer"]
        self.eval_metrics = config["eval_metrics"]

        self.embed = nn.Embedding.from_pretrained(config["pretrained_embed"])
        self.embed.padding_idx = config["padding_idx"]

        self.model = config["model"]
        self.model_path = config["model_path"]
        self.model_name = config["model_name"]

        self._best_accuracy = 0.0

        self.device = 'cuda' if torch.cuda.is_available() else 'cpu'
        # self.use_cuda = True

    def train(self, train_data, dev_data=None):
        # transfer model to gpu if available
        network = self.model.to(self.device)

        # define batch iterator
        train_iter = torchtext.data.Iterator(
            dataset=train_data, batch_size=self.batch_size,
            train=True, shuffle=True, sort=False,
            device=self.device
        )

        # define Evaluator over dev data
        if self.validate:
            default_valid_args = {
                "batch_size": max(8, self.batch_size // 10),
                # "use_cuda": self.use_cuda
            }
            validator = Evaluator(**default_valid_args)

        criterion = nn.BCEWithLogitsLoss()

        start = time.time()
        # turn on network training mode
        network.train()
        for epoch in range(1, self.n_epochs + 1):
            # initialize iterator
            train_iter.init_epoch()

            # one forward and backward pass
            self._train_step(
                train_iter, network, criterion, start=start,
                n_print=self.print_every_step, epoch=epoch
            )

            # validation
            if self.validate:
                if dev_data is None:
                    raise RuntimeError(
                        "self.validate is True in trainer, "
                        "but dev_data is None."
                        " Please provide the validation data.")
                eval_results = validator.eval(network, dev_data)

                if self.save_best_dev and self._best_eval_result(eval_results):
                    save_model(network, self.model_path)
                    logging.info("Saved better model selected by validation")
        save_model(network, self.model_path)

    def _train_step(self, data_iter, network, criterion, **kwargs):
        """Training process in one epoch.
        """
        step = 0
        for batch in data_iter:
            (seq, seq_len), label = batch.sequence, batch.label

            self.optimizer.zero_grad()
            logits = network(self.embed(seq), seq_len)
            y_one_hot = torch.zeros(len(label), 2)\
                .to("cuda" if torch.cuda.is_available() else "cpu")\
                .scatter_(1, label.view(-1, 1), 1)
            loss = criterion(logits, y_one_hot)
            loss.backward()
            self.optimizer.step()

            if kwargs["n_print"] > 0 and step % kwargs["n_print"] == 0:
                end = time.time()
                diff = timedelta(seconds=round(end - kwargs["start"]))
                print_output = f"[epoch: {kwargs['epoch']:>3} step: {step:>4}] " \
                               f"train loss: {loss.item():>4.6} time: {diff}"
                print(print_output)

            step += 1

    def _best_eval_result(self, eval_results):
        """Check if the current epoch yields better validation results.

        :param eval_results: dict, format {metrics_name: value}
        :return: bool, True means current results on dev set is the best.
        """
        assert self.eval_metrics in eval_results, \
            f"Evaluation does not contain metrics '{self.eval_metrics}'"

        accuracy = eval_results[self.eval_metrics]
        if accuracy > self._best_accuracy:
            self._best_accuracy = accuracy
            return True
        else:
            return False

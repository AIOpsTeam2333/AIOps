# -*- coding: utf8 -*-

from datetime import timedelta
import time

import torch
import torchtext

from .evaluate import Tester
from tools import save_model


class Trainer:
    """Trainer."""
    def __init__(self, **kwargs):
        self.n_epochs = kwargs["epochs"]
        self.batch_size = kwargs["batch_size"]
        self.validate = kwargs["validate"]
        self.save_best_dev = kwargs["save_best_dev"]
        self.print_every_step = kwargs["print_every_step"]
        self.optimizer = kwargs["optimizer"]
        self.model_path = kwargs["model_path"]
        self.eval_metrics = kwargs["eval_metrics"]
        self.device = "cuda" if torch.cuda.is_available() else "cpu"
        self._best_accuracy = 0.0

    def train(self, network, train_data, dev_data=None):
        # transfer model to gpu if available
        network = network.to(self.device)

        # define batch iterator
        train_iter = torchtext.data.Iterator(
            dataset=train_data, batch_size=self.batch_size,
            train=True, shuffle=True, sort=False,
            device=self.device)

        # define Tester over dev data
        if self.validate:
            default_valid_args = {
                "batch_size": max(8, self.batch_size // 10)
            }
            validator = Tester(**default_valid_args)

        start = time.time()
        for epoch in range(1, self.n_epochs + 1):
            # turn on network training mode
            network.train()

            # initialize iterator
            train_iter.init_epoch()

            # one forward and backward pass
            self._train_step(
                train_iter, network, start=start,
                n_print=self.print_every_step, epoch=epoch)

            # validation
            if self.validate:
                if dev_data is None:
                    raise RuntimeError(
                        "self.validate is True in trainer, "
                        "but dev_data is None."
                        " Please provide the validation data.")
                eval_results = validator.test(network, dev_data)

                if self.save_best_dev and self.best_eval_result(eval_results):
                    save_model(network, self.model_path)
                    print("Saved better model selected by validation.")

    def _train_step(self, data_iterator, network, **kwargs):
        """Training process in one epoch.
        """
        step = 0
        for batch in data_iterator:
            (text, text_len), target = batch.text, batch.target

            self.optimizer.zero_grad()
            logits = network(text, text_len)
            loss = network.loss(logits, target.float())
            loss.backward()
            self.optimizer.step()

            if kwargs["n_print"] > 0 and step % kwargs["n_print"] == 0:
                end = time.time()
                diff = timedelta(seconds=round(end - kwargs["start"]))
                print_output = "[epoch: {:>3} step: {:>4}]" \
                    " train loss: {:>4.6} time: {}".format(
                        kwargs["epoch"], step, loss.item(), diff)
                print(print_output)

            step += 1

    def best_eval_result(self, eval_results):
        """Check if the current epoch yields better validation results.

        :param eval_results: dict, format {metrics_name: value}
        :return: bool, True means current results on dev set is the best.
        """
        assert self.eval_metrics in eval_results, \
            "Evaluation doesn't contain metrics '{}'." \
            .format(self.eval_metrics)

        accuracy = eval_results[self.eval_metrics]
        if accuracy > self._best_accuracy:
            self._best_accuracy = accuracy
            return True
        else:
            return False

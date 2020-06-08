# -*- coding: utf8 -*-

from models.log_robust import LogRobust
from models.tools.predict import Predictor
from pipeline.abstract_command import AbstractCommand


class DefaultDetector(AbstractCommand):
    def __init__(self, command_id: str):
        super(DefaultDetector, self).__init__(command_id)

    def __call__(self, **kwargs):
        model = LogRobust(
            hidden_dim=kwargs.get("hidden_dim", 128),
            dropout=kwargs.get("dropout", 0.35),
            embed_dim=kwargs["embed_dim"],
            num_layers=kwargs.get("num_layers", 2)
        )
        predictor = Predictor(model=model, **kwargs)
        return {
            **kwargs,
            "predictions": predictor.predict(kwargs["dataset"])
        }

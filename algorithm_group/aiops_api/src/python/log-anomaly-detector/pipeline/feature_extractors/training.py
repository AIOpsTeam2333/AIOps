# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/6/4 20:17
@desc:
"""

import pandas as pd

from pipeline import AbstractCommand
from server.feedback import AbstractFeedbackServer
from tools import (read_yaml, load_class)


class TrainingFeatureExtractor(AbstractCommand):
    def __init__(self, command_id):
        super(TrainingFeatureExtractor, self).__init__(command_id)

    def __call__(self, **kwargs):
        feature_cfg = read_yaml("config/feature.yml")["feature_store"]
        class_obj = load_class(["storage", "feature", feature_cfg["class_name"]])
        ctor_args = feature_cfg["constructor_args"]
        feature_store = class_obj(
            ctor_args["host"], ctor_args["port"],
            ctor_args["db_name"], ctor_args["coll_name"])
        df_feedback = kwargs["data"]
        df_feature = feature_store.get()
        joined = df_feedback.merge(df_feature, left_on="window_id", right_on="window_id", how="inner")
        features = joined[["window_id", "sequence", "label"]]
        features.columns = ["BlockId", "Sequence", "Label"]
        return {
            **kwargs,
            "features": features
        }

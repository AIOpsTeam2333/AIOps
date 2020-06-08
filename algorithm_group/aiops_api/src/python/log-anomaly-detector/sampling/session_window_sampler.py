# -*- coding: utf8 -*-

from collections import OrderedDict
import regex as re

import numpy as np
import pandas as pd

from sampling import Sampler


class SessionWindowSampler(Sampler):
    def __init__(self,
                 id_regex,
                 content_column_name,
                 event_id_column_name,
                 obj_id_column_name,
                 seq_column_name,
                 extract):
        self.id_regex = id_regex
        self.content_column_name = content_column_name
        self.event_id_column_name = event_id_column_name
        self.obj_id_column_name = obj_id_column_name
        self.seq_column_name = seq_column_name
        self.extract = extract

    def sample(self, data: pd.DataFrame, ground_truth=None):
        features = {
            "sequential_features": [],
            "quantitative_features": [],
            "semantic_features": []
        }
        labels = []
        log_sequences = OrderedDict()
        for _, row in data.iterrows():
            obj_id_set = set(re.findall(
                self.id_regex, row[self.content_column_name]))
            for obj_id in obj_id_set:
                if obj_id not in log_sequences:
                    log_sequences[obj_id] = []
                log_sequences[obj_id].append(
                    row[self.event_id_column_name])
        grouped = pd.DataFrame(
            [(k, " ".join(v))
             for k, v in log_sequences.items()],
            columns=[self.obj_id_column_name, self.seq_column_name])

        if isinstance(ground_truth, str):
            ground_truth = pd.read_csv(ground_truth)
        if isinstance(ground_truth, pd.DataFrame):
            labeled = self._attach_tag(grouped, ground_truth)
        elif ground_truth is None:
            labeled = grouped
        else:
            raise Exception(
                f"argument error: 'ground_truth' of type '{type(ground_truth)}'")

        for _, row in labeled.iterrows():
            self.extract(row, features, labels)

        return (features, labels) if ground_truth is not None else features

    def _attach_tag(self, grouped, ground_truth: pd.DataFrame):
        # JOIN is faster than iterating over the DataFrame
        joined = grouped.merge(
            ground_truth, left_on=self.obj_id_column_name,
            right_on=self.obj_id_column_name, how="inner")
        joined["Label"] = np.where(joined["Label"] == "Anomaly", 1, 0)
        return joined

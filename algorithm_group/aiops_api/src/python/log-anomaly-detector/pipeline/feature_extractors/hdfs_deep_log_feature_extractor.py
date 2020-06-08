# -*- coding: utf8 -*-

from collections import OrderedDict
import logging
import os
import regex as re

import numpy as np
import pandas as pd

from pipeline import AbstractCommand
from storage.log_storage import LogStorage


class HdfsDeepLogFeatureExtractor(AbstractCommand):
    def __init__(self, command_id: str):
        super(HdfsDeepLogFeatureExtractor, self).__init__(command_id)

    def __call__(self, **kwargs):
        grouped = self._group(kwargs["structured_log_store"])
        if "label_filepath" in kwargs:
            grouped = self._attach_tag(grouped, kwargs["label_filepath"])
        return {**kwargs, "features": grouped}

    @staticmethod
    def _group(log_store: LogStorage):
        """Grouping logs according to `blk_id` (session window sampling)

        Refer to the paper *System Log Analysis for Anomaly Detection* by He et al.
        for more information about sampling methods
        """
        logging.info("Grouping ...")
        df = log_store.get()
        log_sequences = OrderedDict()
        for _, row in df.iterrows():
            block_id_set = set(re.findall(r"blk_-?\d+", row["Content"]))
            for block_id in block_id_set:
                if block_id not in log_sequences:
                    log_sequences[block_id] = []
                log_sequences[block_id].append(str(row["EventId"]))
        grouped = pd.DataFrame([(k, " ".join(v)) for k, v in log_sequences.items()],
                               columns=["BlockId", "Sequence"])
        return grouped

    @staticmethod
    def _attach_tag(grouped, label_filepath):
        ground_truth = pd.read_csv(label_filepath)
        # JOIN is faster than iterating over the DataFrame
        joined = grouped.merge(
            ground_truth, left_on="BlockId", right_on="BlockId", how="inner")
        joined["Label"] = np.where(joined["Label"] == "Anomaly", 1, 0)
        return joined

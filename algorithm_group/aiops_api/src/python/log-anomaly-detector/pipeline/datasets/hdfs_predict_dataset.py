# -*- coding: utf8 -*-

from collections import Counter
import logging

import numpy as np
import pandas as pd

from pipeline.abstract_command import AbstractCommand
from utils.dataset import LogDataset


class HdfsDeepLogPredictDataset(AbstractCommand):
    def __init__(self, command_id):
        super(HdfsDeepLogPredictDataset, self).__init__(command_id)

    def __call__(self, **kwargs):
        window_size = kwargs["window_size"]
        features_df = kwargs["features"]
        num_sessions = 0
        dataset = {
            "sequential_features": [],
            "quantitative_features": [],
            "semantic_features": []
        }
        labels = []
        data = features_df["Sequence"].values
        for seq in data:
            seq = tuple(map(lambda n: int(n) - 1, seq.split()))
            for i in range(len(seq) - window_size):
                sequential_feature = seq[i: i + window_size]
                quantitative_feature = [0] * 29
                cnt = Counter(sequential_feature)
                for k in cnt:
                    quantitative_feature[k] = cnt[k]
                sequential_feature = np.array(sequential_feature)[:, np.newaxis]
                quantitative_feature = np.array(quantitative_feature)[:, np.newaxis]
                dataset['sequential_features'].append(sequential_feature)
                dataset['quantitative_features'].append(quantitative_feature)
                labels.append(seq[i + window_size])
            num_sessions += 1

        # TODO: needed?
        # if sample_ratio != 1:
        #     result_logs, labels = down_sample(result_logs, labels, sample_ratio)

        logging.info(f"#sessions {num_sessions}, "
                     f"#seqs {len(dataset['sequential_features'])}")

        return {**kwargs,
                "dataset": LogDataset(
                    dataset, labels=labels,
                    use_sequential_features=True,
                    use_quantitative_features=True)}

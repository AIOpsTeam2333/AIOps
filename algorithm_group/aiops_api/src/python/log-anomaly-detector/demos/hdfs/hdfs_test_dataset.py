# -*- coding: utf8 -*-

from collections import Counter
import logging

import numpy as np
import pandas as pd
from torch.utils.data import random_split

from pipeline.abstract_command import AbstractCommand
from utils.dataset import LogDataset


class HdfsTestDataset(AbstractCommand):
    def __init__(self, command_id):
        super(HdfsTestDataset, self).__init__(command_id)

    def __call__(self, **kwargs):
        feature_filepath = kwargs["feature_filepath"]
        window_size = kwargs["window_size"]
        num_events = kwargs["num_events"]
        features_df = pd.read_csv(feature_filepath,
                                  engine="c", na_filter=False, memory_map=True)
        num_sessions = 0
        dataset = {
            "sequential_features": [],
            "quantitative_features": [],
        }
        labels = []
        data = features_df[features_df["Label"] == 0]["Sequence"].values
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

        train_ratio = kwargs["train_ratio"]
        train_size = int(len(labels) * train_ratio)
        valid_size = len(labels) - train_size
        train_dataset, valid_dataset = random_split(
            LogDataset(dataset, labels=labels, use_sequential_features=True),
            [train_size, valid_size])

        return {**kwargs,
                "train_dataset": train_dataset,
                "valid_dataset": valid_dataset}

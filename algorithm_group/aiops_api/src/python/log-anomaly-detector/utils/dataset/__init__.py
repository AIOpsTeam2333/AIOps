# -*- coding: utf-8 -*-

import torch
from torch.utils.data import Dataset


class LogDataset(Dataset):
    def __init__(self,
                 features,
                 labels=None,
                 use_sequential_features=False,
                 use_quantitative_features=False,
                 use_semantic_features=False):
        self.use_sequential_features = use_sequential_features
        self.use_quantitative_features = use_quantitative_features
        self.use_semantic_features = use_semantic_features
        if self.use_sequential_features:
            self.sequential_features = features['sequential_features']
        if self.use_quantitative_features:
            self.quantitative_features = features['quantitative_features']
        if self.use_semantic_features:
            self.semantic_features = features['semantic_features']
        self.labels = labels

    def __len__(self):
        return len(self.sequential_features)

    def __getitem__(self, idx):
        x = {}
        if self.use_sequential_features:
            x['sequential_feature'] = torch.tensor(
                self.sequential_features[idx], dtype=torch.float)
        if self.use_quantitative_features:
            x['quantitative_feature'] = torch.tensor(
                self.quantitative_features[idx], dtype=torch.float)
        if self.use_semantic_features:
            x['semantic_feature'] = torch.tensor(
                self.semantic_features[idx], dtype=torch.float)
        if self.labels is not None:
            # dataset for train / validation
            return x, self.labels[idx]
        else:
            # dataset for prediction
            return x

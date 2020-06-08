# -*- coding: utf8 -*-

from sampling import Sampler


class SlidingWindowSampler(Sampler):
    def __init__(self,
                 window_size,
                 stride,
                 extract,
                 with_label):
        self.window_size = window_size
        self.stride = stride
        self.extract = extract
        self.with_label = with_label

    def sample(self, data):
        features = {
            "sequential_features": [],
            "quantitative_features": [],
            "semantic_features": []
        }
        labels = []
        window_size = self.window_size + 1 if self.with_label else self.window_size
        for i in range(0, len(data), self.stride):
            self.extract(data[i: min(i + window_size, len(data))], features, labels)
        return (features, labels) if self.with_label else features

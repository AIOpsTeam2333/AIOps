# -*- coding: utf8 -*-

from sampling import Sampler
from sampling import SlidingWindowSampler


class FixedWindowSampler(Sampler):
    def __init__(self,
                 window_size,
                 extract,
                 with_label):
        """
        Args:
            window_size - size of <em>X</em> window
            extract - function to extract features (sequential/quantitative/semantic)
                      from the window.
        """
        self.sliding_window_sampler = SlidingWindowSampler(
            window_size, window_size, extract, with_label)

    def sample(self, data):
        return self.sliding_window_sampler.sample(data)

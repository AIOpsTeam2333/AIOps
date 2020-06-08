# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/6/4 20:42
@desc:
"""

from abc import ABCMeta, abstractmethod


class Sampler(metaclass=ABCMeta):
    @abstractmethod
    def sample(self, data):
        pass

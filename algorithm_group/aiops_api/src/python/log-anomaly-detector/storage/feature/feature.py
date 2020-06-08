# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/6/4 19:58
@desc:
"""

from abc import ABCMeta, abstractmethod
from typing import NoReturn

import pandas as pd


class FeatureStore(metaclass=ABCMeta):
    @abstractmethod
    def save(self, feature: pd.DataFrame) -> NoReturn:
        pass

    @abstractmethod
    def get(self) -> pd.DataFrame:
        pass

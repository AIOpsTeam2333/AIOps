# -*- coding: utf8 -*-

from abc import ABCMeta, abstractmethod

import pandas as pd


class LogStorage(metaclass=ABCMeta):
    @abstractmethod
    def get(self) -> pd.DataFrame:
        pass

    @abstractmethod
    def save(self, data):
        pass

    def __repr__(self):
        return f"{self.__class__.__name__}[" \
               f"{', '.join([f'{name}={value}' for name, value in vars(self).items()])}" \
               f"]"

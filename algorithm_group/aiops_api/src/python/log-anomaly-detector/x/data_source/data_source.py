# -*- coding: utf8 -*-

from abc import ABCMeta, abstractmethod


class DataSource(metaclass=ABCMeta):
    @abstractmethod
    def get(self):
        pass

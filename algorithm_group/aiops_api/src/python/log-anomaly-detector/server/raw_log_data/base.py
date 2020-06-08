# -*- coding: utf8 -*-

from abc import ABCMeta, abstractmethod


class AbstractRawLogDataServer(metaclass=ABCMeta):
    @abstractmethod
    def start(self):
        pass

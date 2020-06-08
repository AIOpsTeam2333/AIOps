# -*- coding: utf8 -*-

from abc import ABCMeta, abstractmethod


class AbstractFeedbackServer(metaclass=ABCMeta):
    @abstractmethod
    def start(self):
        pass

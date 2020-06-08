# -*- coding: utf8 -*-

from abc import ABCMeta, abstractmethod


class AbstractCommand(metaclass=ABCMeta):
    def __init__(self, command_id: str):
        self.command_id = command_id

    @abstractmethod
    def __call__(self, **kwargs):
        pass

    def __str__(self):
        return self.command_id

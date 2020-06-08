# -*- coding: utf8 -*-

import requests

from pipeline.abstract_command import AbstractCommand


class DefaultAlarm(AbstractCommand):
    def __init__(self, command_id: str):
        super(DefaultAlarm, self).__init__(command_id)

    def __call__(self, **kwargs):
        window_ids = [e.window_id for e in kwargs["dataset"].examples]
        predictions = kwargs["predictions"]
        form = [(window_id, pred) for window_id, pred in zip(window_ids, predictions)]
        requests.post(kwargs["url"], form)
        return kwargs

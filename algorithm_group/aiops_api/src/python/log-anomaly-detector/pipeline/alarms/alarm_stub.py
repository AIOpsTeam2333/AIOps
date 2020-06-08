# -*- coding: utf-8 -*-

import logging

import pipeline


class AlarmStub(pipeline.AbstractCommand):
    def __init__(self, command_id):
        super(AlarmStub, self).__init__(command_id)

    def __call__(self, **kwargs):
        logging.info("Alarm stub called")
        predictions = kwargs["predictions"]
        window_ids = [e.window_id for e in kwargs["dataset"].examples]
        ans = [(window_id, pred) for window_id, pred in zip(window_ids, predictions)]
        print(ans)
        return kwargs

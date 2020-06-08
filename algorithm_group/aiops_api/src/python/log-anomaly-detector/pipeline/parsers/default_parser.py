# -*- coding: utf8 -*-

import os

import pandas as pd

from parsing.drain import LogParser

from pipeline.abstract_command import AbstractCommand
from storage.log_storage import LogStorage


class DefaultParser(AbstractCommand):
    def __init__(self, command_id):
        super(DefaultParser, self).__init__(command_id)

    def __call__(self, **kwargs):
        log_format = kwargs["log_format"]
        depth, st, regex = kwargs["depth"], kwargs["sim_thresh"], kwargs["regex"]
        data = kwargs["data"]
        structured_log_store: LogStorage = kwargs["structured_log_store"]
        event_template_store: LogStorage = kwargs["event_template_store"]
        parser = LogParser(log_format, data=data,
                           structured_log_store=structured_log_store,
                           event_template_store=event_template_store,
                           depth=depth, st=st, rex=regex)
        parser.parse()
        events_df = event_template_store.get()
        num_events = events_df.count().values[0]
        return {**kwargs, "num_events": num_events}

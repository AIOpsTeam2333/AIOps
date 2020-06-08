# -*- coding: utf8 -*-

import logging

from pymongo import MongoClient

from .base import AbstractFeedbackServer
from common import (barrier_singleton, scheduler)


class MongoFeedbackServer(AbstractFeedbackServer):
    def __init__(self,
                 host,
                 port,
                 db_name,
                 coll_name,
                 seq_no_column,
                 scheduler_config):
        self.host = host
        self.port = port
        self.client = MongoClient(f"mongodb://{host}:{port}")
        self.db = self.client[db_name]
        self.coll = self.db[coll_name]

        self.seq_no_column = seq_no_column
        self.last_max_seq_no = None

        scheduler.add_job(self._do_periodically(), trigger="cron", max_instances=10, **scheduler_config)

    def start(self):
        pass

    def _do_periodically(self):
        data = self._read_data()
        if len(data) == 0:
            return
        barrier_singleton.prepare_for_training()

    def _read_data(self):
        criteria = {}
        if self.last_max_seq_no is not None:
            criteria = {self.seq_no_column: {"$gt": self.last_max_seq_no}}
        data = list(self.coll.find(criteria))
        if len(data) > 0:
            curr_max = -1
            for ent in data:
                curr_max = max(curr_max, ent["_id"])
            self.last_max_seq_no = curr_max
            data = [ent["log_entry"] for ent in data]
        logging.info(f"{len(data)} record(s) read")
        return data

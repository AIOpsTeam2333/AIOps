# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/5/26 15:44
@desc:
"""

import logging
from pymongo import MongoClient

from .base import AbstractRawLogDataServer
import common

EXPECTED_ARGUMENTS = [
    'year', 'month', 'day', 'week', 'day_of_week',
    'hour', 'minute', 'second', 'start_date',
    'end_date', 'timezone'
]


class MongoRawLogDataServer(AbstractRawLogDataServer):
    def __init__(self,
                 host,
                 port,
                 db_name,
                 coll_name,
                 seq_no_column,
                 scheduler_config):
        self.client = MongoClient(f"mongodb://{host}:{port}/")
        self.db = self.client[db_name]
        self.coll = self.db[coll_name]

        self.seq_no_column = seq_no_column
        self.last_max_seq_no = None

        common.scheduler.add_job(self._do_periodically, trigger="cron", max_instances=10, **scheduler_config)

    def start(self):
        pass

    def _do_periodically(self):
        data = self._read_data()
        if len(data) == 0:
            return
        common.barrier_singleton.add(data)

    def _read_data(self):
        criteria = {}
        if self.last_max_seq_no is not None:
            criteria = {self.seq_no_column: {"$gt": self.last_max_seq_no}}
        data = list(self.coll.find(criteria).limit(200))
        if len(data) > 0:
            curr_max = -1
            for ent in data:
                curr_max = max(curr_max, ent["_id"])
            self.last_max_seq_no = curr_max
            data = [ent["log_entry"] for ent in data]
        logging.info(f"{len(data)} record(s) read")
        return data

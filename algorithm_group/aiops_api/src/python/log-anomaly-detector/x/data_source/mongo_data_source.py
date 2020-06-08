# -*- coding: utf8 -*-

import logging

from pymongo import MongoClient

from .data_source import DataSource


class MongoDataSource(DataSource):
    def __init__(self, **config):
        host = config.get("host", "localhost")
        port = config.get("port", "27017")
        self.client = MongoClient(f"mongodb://{host}:{port}/")
        self.db = self.client[config["database"]]
        self.coll = self.db[config["collection_name"]]
        self.seq_no_column = config["seq_no_column"]
        self.last_max_seq_no = None
        logging.debug(f"seq_no_column: {self.seq_no_column}")

    def get(self):
        criteria = {}
        if self.last_max_seq_no is not None:
            criteria = {self.seq_no_column: {"$gt": self.last_max_seq_no}}

        data = list(self.coll.find(criteria))
        logging.debug(data[0])
        if len(data) > 0:
            curr_max = -1
            for doc in data:
                curr_max = max(curr_max, doc[self.seq_no_column])
            self.seq_no_column = curr_max
            data = [entry["log_entry"] for entry in data]

        return data

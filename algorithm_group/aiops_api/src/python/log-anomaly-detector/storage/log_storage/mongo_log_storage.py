# -*- coding: utf8 -*-

import pandas as pd
from pymongo import MongoClient

from storage.log_storage import LogStorage
from utils import pascal_to_snake, snake_to_pascal


class MongoLogStorage(LogStorage):
    def __init__(self, host, port, db_name, coll_name):
        self.client = MongoClient(f"mongodb://{host}:{port}/")
        self.default_db_name = db_name
        self.default_coll_name = coll_name

    def get(self, db_name=None, coll_name=None):
        if db_name is None:
            db_name = self.default_db_name
        if coll_name is None:
            coll_name = self.default_coll_name

        coll = self.client[db_name][coll_name]

        res = coll.find()

        data = {}
        for doc in res:
            for k, v in doc.items():
                k = snake_to_pascal(k)
                if k not in data:
                    data[k] = []
                data[k].append(v)
        return pd.DataFrame(data)

    def save(self, data: pd.DataFrame, db_name=None, coll_name=None):
        if db_name is None:
            db_name = self.default_db_name
        if coll_name is None:
            coll_name = self.default_coll_name

        coll = self.client[db_name][coll_name]

        column_names = [pascal_to_snake(col) for col in data.columns]

        coll.insert_many(
            [{col_name: col_value
              for col_name, col_value in zip(column_names, row[1])}
             for row in data.iterrows()])

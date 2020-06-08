# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/6/4 19:58
@desc:
"""

from pymongo import MongoClient
import pandas as pd

import storage.feature as ft


class MongoFeatureStore(ft.FeatureStore):
    def __init__(self, host, port, db_name, coll_name):
        self.host = host
        self.port = port
        self.client = MongoClient(f"mongodb://{host}:{port}/")
        self.db = self.client[db_name]
        self.coll = self.db[coll_name]

    def save(self, feature):
        data = []
        for _, row in feature.iterrows():
            data.append({
                "window_id": row["BlockId"],
                "sequence": row["Sequence"]
            })
        self.coll.insert_many(data)

    def get(self):
        docs = self.coll.find({})
        data = []
        for doc in docs:
            data.append((doc["window_id"], doc["sequence"]))
        return pd.DataFrame(data, columns=["window_id", "sequence"])

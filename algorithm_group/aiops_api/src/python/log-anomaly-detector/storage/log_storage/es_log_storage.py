# -*- coding: utf8 -*-

import logging
import regex as re

from elasticsearch.exceptions import NotFoundError as EsNotFoundError
from elasticsearch import Elasticsearch
import pandas as pd

from exceptions import NoSuchLogDataException
from storage.log_storage import LogStorage
from utils import pascal_to_snake, snake_to_pascal


class ElasticsearchLogStorage(LogStorage):
    def __init__(self, host, port, index_name):
        self.default_index_name = index_name
        self.client = Elasticsearch()

    def get(self, index_name=None) -> pd.DataFrame:
        if index_name is None:
            index_name = self.default_index_name
        try:
            data = {}
            res = self.client.search(index=index_name, size=2000)["hits"]["hits"]
            for doc in res:
                doc = doc["_source"]
                for k, v in doc.items():
                    k = snake_to_pascal(k)
                    if k not in data:
                        data[k] = []
                    data[k].append(v)
            return pd.DataFrame(data)
        except EsNotFoundError:
            raise NoSuchLogDataException()

    def save(self, data: pd.DataFrame, index_name=None):
        logging.debug(data.columns)
        """
        Args:
             data - parsed raw_log_data data
        """
        if index_name is None:
            index_name = self.default_index_name

        column_names = [pascal_to_snake(col) for col in data.columns]

        for row in data.iterrows():
            self.client.index(
                index_name,
                {col_name: col_value
                 for col_name, col_value in zip(column_names, row[1])})

        self.client.indices.refresh(index_name)

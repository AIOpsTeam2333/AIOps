# -*- coding: utf8 -*-

from elasticsearch import Elasticsearch

from .data_source import DataSource


class ElasticsearchDataSource(DataSource):
    def __init__(self, config):
        host = config["host"] if "host" in config else "localhost"
        port = config["port"] if "port" in config else "9300"
        self.client = Elasticsearch([{"host": host, "port": port}])
        self.index_name = config["index_name"]
        self.seq_no_column = config["seq_no_column"]
        self.last_max_seq_no = None

    def get(self):
        criteria = {"query": {"match_all": {}}}
        if self.last_max_seq_no is not None:
            criteria = {"query": {}}

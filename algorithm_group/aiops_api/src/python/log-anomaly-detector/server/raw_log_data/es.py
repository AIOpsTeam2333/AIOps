# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/5/26 15:44
@desc:
"""

from apscheduler.schedulers.background import BackgroundScheduler
import logging

from elasticsearch import Elasticsearch

from .base import AbstractRawLogDataServer


def read_periodically():
    logging.info("read_periodically called")


class ElasticsearchRawLogDataServer(AbstractRawLogDataServer):
    def __init__(self,
                 host,
                 port,
                 index_name,
                 seq_no_column,
                 scheduler_config):
        self.index_name = index_name
        self.seq_no_column = seq_no_column
        self.scheduler_config = scheduler_config

        self.es = Elasticsearch()

        self.scheduler = BackgroundScheduler()
        self.scheduler.add_job(read_periodically, trigger="cron", max_instances=10, **self.scheduler_config)

    def start(self):
        self.scheduler.start()

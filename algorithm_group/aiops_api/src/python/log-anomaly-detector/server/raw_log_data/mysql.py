# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/5/26 15:44
@desc:
"""

import logging

from apscheduler.schedulers.background import BackgroundScheduler
import mysql.connector as mysql

from .base import AbstractRawLogDataServer
from common import scheduler


def read_periodically():
    logging.info("read_periodically")


class MysqlRawLogDataServer(AbstractRawLogDataServer):
    def __init__(self,
                 host,
                 port,
                 username,
                 password,
                 db_name,
                 table_name,
                 seq_no_column,
                 scheduler_config):
        self.host = host
        self.port = port
        self.username = username
        self.password = password
        self.db_name = db_name
        self.table_name = table_name
        self.seq_no_column = seq_no_column
        self.scheduler_config = scheduler_config

        self.conn = mysql.connect(
            host=self.host, port=self.port,
            user=self.username, password=self.password,
            database=self.db_name
        )
        self.cursor = self.conn.cursor()

        scheduler.add_job(read_periodically, trigger="cron", max_instances=10, **self.scheduler_config)

    def start(self):
        pass

# -*- coding: utf8 -*-

import mysql.connector as mysql

from .base import AbstractFeedbackServer


class MysqlFeedbackServer(AbstractFeedbackServer):
    def __init__(self, **config):
        self.host = config.get("host", "localhost")
        self.port = config.get("port", "3306")
        self.username = config["username"]
        self.password = config["password"]
        self.conn = mysql.connect(
            host=self.host,
            port=self.port,
            user=self.username,
            passsword=self.password
        )

    def start(self):
        pass

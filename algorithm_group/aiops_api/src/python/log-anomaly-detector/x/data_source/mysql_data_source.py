# -*- coding: utf8 -*-

import mysql.connector as mysql

from .data_source import DataSource


class MysqlDataSource(DataSource):
    def __init__(self, config):
        self.client = mysql.connect(
            host=config["host"], user=config["user"],
            password=config["password"], database=config["database"])
        self.table_name = config["table_name"]
        self.seq_no_column = config["seq_no_column"]
        self.last_max_seq_no = None
        self.cursor = self.client.cursor()

    def get(self):
        sql = f"""SELECT *, MAX({self.seq_no_column}) OVER () 
                    FROM {self.table_name}
               """
        if self.last_max_seq_no is not None:
            sql = " ".join([sql, f"WHERE {self.seq_no_column} > %s"])

        self.cursor.execute(sql)

        res = self.cursor.fetchall()
        data = []
        if len(res) > 0:
            self.last_max_seq_no = res[0][-1]
            data = [t[:-1] for t in res]

        return data

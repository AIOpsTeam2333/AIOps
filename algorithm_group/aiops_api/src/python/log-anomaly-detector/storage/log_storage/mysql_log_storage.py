# -*- coding: utf8 -*-

import mysql.connector as mysql
import pandas as pd

from storage.log_storage import LogStorage
from utils import pascal_to_snake, snake_to_pascal


class MysqlLogStorage(LogStorage):
    def __init__(self,
                 host,
                 user,
                 password,
                 db_name,
                 table_name):
        self.conn = mysql.connect(host=host, user=user, password=password)
        self.cursor = self.conn.cursor()
        self.default_db_name = db_name
        self.default_table_name = table_name

    def get(self, db_name=None, table_name=None):
        if db_name is None:
            db_name = self.default_db_name
        if table_name is None:
            table_name = self.default_table_name
        self.cursor.execute(f"SELECT * FROM `{db_name}`.`{table_name}`")
        res = self.cursor.fetchall()
        column_names = [snake_to_pascal(t[0]) for t in self.cursor.description]
        data = {k: [] for k in column_names}
        for row in res:
            for k, v in zip(column_names, row):
                data[k].append(v)
        return pd.DataFrame(data)

    def save(self, data: pd.DataFrame, db_name=None, table_name=None):
        """
        用户需要负责创建表
        """
        if db_name is None:
            db_name = self.default_db_name
        if table_name is None:
            table_name = self.default_table_name

        column_names = [pascal_to_snake(col) for col in data.columns]

        sql = f"INSERT INTO `{db_name}`.`{table_name}`(`{column_names[0]}`"
        for col_name in column_names[1:]:
            sql += f", `{col_name}`"
        sql += ") VALUES (%s"
        for _ in range(len(column_names) - 1):
            sql += ", %s"
        sql += ")"

        # from pd.DataFrame to List[Tuple]
        to_be_inserted = [tuple(field for field in row[1])
                          for row in data.iterrows()]

        self.cursor.executemany(sql, to_be_inserted)

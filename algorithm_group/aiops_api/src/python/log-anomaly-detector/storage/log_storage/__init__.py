# -*- coding: utf8 -*-

"""
LogStorage 用于存储中间结果
"""

from .log_storage import LogStorage
from .csv_file_log_storage import CsvFileLogStorage
from .es_log_storage import ElasticsearchLogStorage
from .mongo_log_storage import MongoLogStorage
from .mysql_log_storage import MysqlLogStorage

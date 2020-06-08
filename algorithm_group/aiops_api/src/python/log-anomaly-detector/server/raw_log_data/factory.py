# -*- coding: utf8 -*-

import os

from exceptions import ConfigException
from tools import read_yaml
from server.raw_log_data.api import ApiRawLogDataServer
from server.raw_log_data.es import ElasticsearchRawLogDataServer
from server.raw_log_data.mongo import MongoRawLogDataServer
from server.raw_log_data.mysql import MysqlRawLogDataServer

CONFIG_DIR = "config/server/raw-log-data"


def _create_api():
    doc = read_yaml(os.path.join(CONFIG_DIR, "api.yml"))
    return ApiRawLogDataServer()


def _create_es():
    doc = read_yaml(os.path.join(CONFIG_DIR, "es.yml"))
    host = doc.get("host", "localhost")
    port = doc.get("port", "9200")
    required_config = {
        "index_name": None,
        "seq_no_column": None,
        "cron": None
    }
    for k in required_config.keys():
        try:
            required_config[k] = doc[k]
        except KeyError:
            raise ConfigException(f"`{k}` should be specified")
    return ElasticsearchRawLogDataServer(
        host, port, required_config["index_name"],
        required_config["seq_no_column"], required_config["cron"])


def _create_mongo():
    doc = read_yaml(os.path.join(CONFIG_DIR, "mongo.yml"))
    host = doc.get("host", "localhost")
    port = doc.get("port", "27017")
    required_config = {
        "db_name": None,
        "coll_name": None,
        "seq_no_column": None,
        "cron": None
    }
    for k in required_config.keys():
        try:
            required_config[k] = doc[k]
        except KeyError:
            raise ConfigException(f"`{k}` should be specified")
    return MongoRawLogDataServer(
        host, port, required_config["db_name"], required_config["coll_name"],
        required_config["seq_no_column"], required_config["cron"])


def _create_mysql():
    doc = read_yaml(os.path.join(CONFIG_DIR, "mysql.yml"))
    host = doc.get("host", "localhost")
    port = doc.get("port", "3306")
    required_config = {
        "username": None,
        "password": None,
        "db_name": None,
        "table_name": None,
        "seq_no_column": None,
        "cron": None
    }
    for k in required_config.keys():
        try:
            required_config[k] = doc[k]
        except KeyError:
            raise ConfigException(f"`{k}` should be specified")
    return MysqlRawLogDataServer(
        host, port, required_config["username"], required_config["password"],
        required_config["db_name"], required_config["table_name"],
        required_config["seq_no_column"], required_config["cron"])


_handlers = {
    "api": _create_api,
    "pull/es": _create_es,
    "pull/mongo": _create_mongo,
    "pull/mysql": _create_mysql
}


class RawLogDataServerFactory:
    @staticmethod
    def create(run_mode):
        try:
            return _handlers[run_mode]()
        except KeyError:
            raise ConfigException(f"unsupported run mode: {run_mode}")

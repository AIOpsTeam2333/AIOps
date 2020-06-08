# -*- coding: utf8 -*-

from elasticsearch import Elasticsearch

from main import main
from tools import read_yaml


if __name__ == "__main__":
    try:
        main()
    finally:
        overall_cfg = read_yaml("../config/server/server.yml")
        run_mode = overall_cfg["run_mode"]
        if run_mode == "api":
            server_cfg = read_yaml("../config/server/raw-log-data/api.yml")
        elif run_mode == "pull/mongo":
            server_cfg = read_yaml("../config/server/raw-log-data/mongo.yml")
        elif run_mode == "pull/es":
            server_cfg = read_yaml("../config/server/raw-log-data/es.yml")
        elif run_mode == "pull/mysql":
            server_cfg = read_yaml("../config/server/raw-log-data/mysql.yml")

        options = read_yaml("../config/options.yml")
        structured_log_store_cfg = options["structured_log_store"]
        if structured_log_store_cfg["class_name"] == "ElasticsearchLogStorage":
            es = Elasticsearch()
            es.indices.delete(
                index=structured_log_store_cfg["constructor_args"]["index_name"])

        event_template_store_cfg = options["event_template_store"]
        if event_template_store_cfg["class_name"] == "ElasticsearchLogStorage":
            es = Elasticsearch()
            es.indices.delete(
                index=event_template_store_cfg["constructor_args"]["index_name"])

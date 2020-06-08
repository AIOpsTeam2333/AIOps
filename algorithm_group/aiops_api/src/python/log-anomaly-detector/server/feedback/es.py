# -*- coding: utf8 -*-

from elasticsearch import Elasticsearch

from .base import AbstractFeedbackServer
from common import scheduler


class ElasticsearchFeedbackServer(AbstractFeedbackServer):
    def __init__(self, **config):
        host = config.get("host", "localhost")
        port = config.get("port", "9200")
        self.client = Elasticsearch([{"host": host, "port": port}])
        scheduler.add_job(self._do_periodically, trigger="cron", max_instances=10, )

    def start(self):
        pass

    def _do_periodically(self):
        pass

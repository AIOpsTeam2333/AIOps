# -*- coding: utf8 -*-

import logging
from datetime import datetime

import common
from tools import read_yaml
import server as srv

logging.basicConfig(
    format="%(asctime)s %(levelname)s %(funcName)s: %(message)s",
    level=logging.CRITICAL
)


def main():
    doc = read_yaml("config/server/server.yml")
    raw_log_data_run_mode = doc["raw_log_data"]["run_mode"]
    feedback_run_mode = doc["feedback"]["run_mode"]
    srv.RawLogDataServerFactory.create(raw_log_data_run_mode)
    srv.FeedbackServerFactory.create(feedback_run_mode)
    print(common.app.view_functions)
    common.scheduler.start()
    # common.app.run(host="0.0.0.0", port="9527")
    common.app.run()


if __name__ == "__main__":
    logging.info(f"started at {datetime.now()}")
    main()

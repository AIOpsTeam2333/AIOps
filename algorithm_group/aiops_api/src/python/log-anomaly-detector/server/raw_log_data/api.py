# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/5/26 15:44
@desc:
"""

from flask import request, jsonify

from common import app, barrier_singleton
# from barrier import barrier_singleton
from tools import get_extension, is_allowed_file, read_yaml
from .base import AbstractRawLogDataServer

cfg = read_yaml("config/server/raw-log-data/api.yml")
ALLOWED_EXTENSIONS = cfg.get("allowed_extensions", ["txt", "log", "dat"])


def on_receive_logs():
    if "log_file" not in request.files:
        # status code 400 stands for "Bad Request"
        # abort(400)
        return jsonify({
            "status_code": 400,
            "message": "No file part"
        })
    log_file = request.files["log_file"]
    if log_file.filename == "":
        return jsonify({
            "status_code": 400,
            "message": "No selected file"
        })
    if log_file and is_allowed_file(log_file.filename, ALLOWED_EXTENSIONS):
        lines = log_file.readlines()
        lines = [line.decode("utf-8").strip() for line in lines]
        barrier_singleton.add(lines)
        return jsonify({
            "status_code": 200,
            "message": "success"
        })
    return jsonify({
        "status_code": 400,
        "message": f"file extension \"{get_extension(log_file.filename)}\" is not allowed"
    })


class ApiRawLogDataServer(AbstractRawLogDataServer):
    def __init__(self):
        app.add_url_rule("/logs", view_func=on_receive_logs, methods=["POST"])

    def start(self):
        pass

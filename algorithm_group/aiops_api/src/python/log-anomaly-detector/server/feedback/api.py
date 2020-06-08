# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/6/3 11:40
@desc:
"""

import os
import tempfile
import pandas as pd
from flask import (request, jsonify)

import common
from server.feedback import AbstractFeedbackServer
from tools import get_extension, is_allowed_file
from external.result_message import (bad_request_result_message,
                                     success_result_message)

ALLOWED_EXTENSIONS = ["csv"]


def on_receive_feedback():
    if "feedback_file" not in request.files:
        # status code 400 stands for "Bad Request"
        # abort(400)
        return jsonify(bad_request_result_message("No file part"))
    feedback_file = request.files["feedback_file"]
    if feedback_file.filename == "":
        return jsonify(bad_request_result_message("No selected file"))
    if feedback_file and is_allowed_file(feedback_file.filename, ALLOWED_EXTENSIONS):
        lines = feedback_file.readlines()
        lines = [line.decode("utf-8").strip() + "\n" for line in lines]
        if os.name == "nt":
            tmp = tempfile.NamedTemporaryFile("w+t", delete=False)
            tmp.writelines(lines)
            tmp.close()
            df_feedback = pd.read_csv(tmp.name)
            os.remove(tmp.name)
        else:
            with tempfile.NamedTemporaryFile("w+t") as tmp:
                tmp.writelines(lines)
                tmp.flush()
                df_feedback = pd.read_csv(tmp.name)
        common.barrier_singleton.prepare_for_training(df_feedback)
        return jsonify(success_result_message())
    return jsonify(
        bad_request_result_message(
            f"ext '.{get_extension(feedback_file.filename)}' is not allowed"))


class ApiFeedbackServer(AbstractFeedbackServer):
    def __init__(self):
        common.app.add_url_rule("/feedback", view_func=on_receive_feedback, methods=["POST"])

    def start(self):
        pass

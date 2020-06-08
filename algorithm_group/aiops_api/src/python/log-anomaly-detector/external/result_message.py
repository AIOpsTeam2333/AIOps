# -*- coding: utf8 -*-


def _template(status_code: int, msg: str):
    return {
        "status_code": status_code,
        "message": msg
    }


def bad_request_result_message(msg: str):
    return _template(400, msg)


def success_result_message(msg: str = "success"):
    return _template(200, msg)

# -*- coding: utf8 -*-


class InvalidArgumentException(Exception):
    pass


class DuplicateCommandIdException(Exception):
    pass


class ConfigException(Exception):
    pass


class NoSuchLogDataException(Exception):
    pass

# -*- coding: utf8 -*-

import pandas as pd

from storage.log_storage import LogStorage


class CsvFileLogStorage(LogStorage):
    def __init__(self, filename: str):
        if not filename.endswith(".csv"):
            filename += ".csv"
        self.filename = filename

    def get(self):
        return pd.read_csv(self.filename)

    def save(self, data: pd.DataFrame):
        data.to_csv(self.filename, index=False)

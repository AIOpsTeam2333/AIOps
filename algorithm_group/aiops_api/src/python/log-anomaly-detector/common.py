# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/6/5 10:10
@desc:
"""

from apscheduler.schedulers.background import BackgroundScheduler
from flask import Flask

import barrier

app = Flask("log-anomaly-detector")

scheduler = BackgroundScheduler()

barrier_singleton = barrier.Barrier()

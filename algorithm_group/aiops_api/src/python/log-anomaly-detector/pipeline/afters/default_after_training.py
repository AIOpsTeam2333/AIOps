# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/6/5 22:33
@desc:
"""

import pipeline
import common


class DefaultAfterTraining(pipeline.AbstractCommand):
    def __init__(self, command_id):
        super(DefaultAfterTraining, self).__init__(command_id)

    def __call__(self, **kwargs):
        common.barrier_singleton.finish_training()

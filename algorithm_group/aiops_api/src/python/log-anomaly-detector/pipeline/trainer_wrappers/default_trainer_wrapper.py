# -*- coding: utf8 -*-

from pipeline.abstract_command import AbstractCommand
from models.tools.train import Trainer
# this import statement is for eval
from models import *


class DefaultTrainerWrapper(AbstractCommand):
    def __init__(self, command_id):
        super(DefaultTrainerWrapper, self).__init__(command_id)

    def __call__(self, **kwargs):
        model_class = eval(kwargs["model_class"])
        model = model_class(input_size=kwargs['input_size'],
                            hidden_size=kwargs['hidden_size'],
                            num_layers=kwargs['num_layers'],
                            num_keys=kwargs['num_classes'])
        Trainer(model, kwargs).start_train()

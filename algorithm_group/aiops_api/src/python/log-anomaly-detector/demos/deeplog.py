#!/usr/bin/env python
# -*- coding: utf-8 -*-

import argparse
import sys

from models.deep_log import DeepLog
from models.tools.predict import Predictor
from models.tools.train import Trainer
from utils.__init__ import seed_everything

# Config Parameters

options = dict()
options['data_dir'] = '../data/'
options["data_rel_path"] = {
    "semantic_vec": "hdfs/event2semantic_vec.json",
    "train": "hdfs/hdfs_train",
    "val": "hdfs/hdfs_test_normal",
    "test": ""
}
options['window_size'] = 10
options['device'] = "cpu"

# Sampling
options['sample'] = "sliding_window"
options['window_size'] = 10  # if fix_window

# Features
options['sequential_features'] = True
options['quantitative_features'] = False
options['semantic_features'] = False
options['num_features'] = sum([options['sequential_features'],
                               options['quantitative_features'],
                               options['semantic_features']])

# Model
options['input_size'] = 1
options['hidden_size'] = 64
options['num_layers'] = 2
options['num_classes'] = 28

# Train
options['batch_size'] = 128
options['accumulation_step'] = 1

options['optimizer'] = 'adam'
options['lr'] = 0.001
options['max_epoch'] = 370
options['lr_step'] = (300, 350)
options['lr_decay_ratio'] = 0.1

options['resume_path'] = None
options['model_name'] = "deeplog"
options['save_dir'] = "../result/deeplog/"

# Predict
options['model_path'] = "../result/deeplog/default.pth"
options['num_candidates'] = 9

seed_everything(seed=1234)


def train():
    model = DeepLog(input_size=options['input_size'],
                    hidden_size=options['hidden_size'],
                    num_layers=options['num_layers'],
                    num_keys=options['num_classes'])
    trainer = Trainer(model, options)
    trainer.start_train()


def predict():
    model = DeepLog(input_size=options['input_size'],
                    hidden_size=options['hidden_size'],
                    num_layers=options['num_layers'],
                    num_keys=options['num_classes'])
    predictor = Predictor(model, options)
    predictor.predict_unsupervised()

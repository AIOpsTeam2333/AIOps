#!/usr/bin/env python
# -*- coding: utf-8 -*-

import argparse
import sys

from models.log_robust import LogRobust
from models.tools.predict import Predictor
from models.tools.train import Trainer
from utils.__init__ import seed_everything

# Config Parameters

config = dict()
config['data_dir'] = '../data/'
config["data_rel_path"] = {
    "semantic_vec": "hdfs/event2semantic_vec.json",
    "train": "hdfs/robust_log_train.csv",
    "val": "hdfs/robust_log_valid.csv",
    "test": "hdfs/robust_log_test.csv"
}
config['window_size'] = 10
config['device'] = "cpu"

# Sampling
config['sample'] = "session_window"
config['window_size'] = -1

# Features
config['sequential_features'] = False
config['quantitative_features'] = False
config['semantic_features'] = True
config['num_features'] = sum(
    [config['sequential_features'], config['quantitative_features'], config['semantic_features']])

# Model
config['input_size'] = 300
config['hidden_size'] = 128
config['num_layers'] = 2
config['num_classes'] = 2

# Train
config['batch_size'] = 256
config['accumulation_step'] = 1

config['optimizer'] = 'adam'
config['lr'] = 0.001
config['max_epoch'] = 60
config['lr_step'] = (40, 50)
config['lr_decay_ratio'] = 0.1

config['resume_path'] = None
config['model_name'] = "robustlog"
config['save_dir'] = "../result/robustlog/"

# Predict
config['model_path'] = "../result/robustlog/robustlog_last.pth"
config['num_candidates'] = -1

seed_everything(seed=1234)


def train():
    model = LogRobust(input_size=config['input_size'],
                      hidden_size=config['hidden_size'],
                      num_layers=config['num_layers'],
                      num_keys=config['num_classes'])
    trainer = Trainer(model, config)
    trainer.start_train()


def predict():
    model = LogRobust(input_size=config['input_size'],
                      hidden_size=config['hidden_size'],
                      num_layers=config['num_layers'],
                      num_keys=config['num_classes'])
    predictor = Predictor(model, config)
    predictor.predict_supervised()


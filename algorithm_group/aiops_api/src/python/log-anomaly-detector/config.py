# -*- coding: utf8 -*-

from models.deep_log import DeepLog
from utils import seed_everything

"""
configurations for raw_log_data parsers
"""
# HDFS raw_log_data format
log_format = '<Date> <Time> <Pid> <Level> <Component>: <Content>'

# regular expression list for optional pre-processing (default: [])
regex = [
    r'blk_(|-)[0-9]+',                                        # block id
    r'(/|)([0-9]+\.){3}[0-9]+(:[0-9]+|)(:|)',                 # IP
    r'(?<=[^A-Za-z0-9])(\-?\+?\d+)(?=[^A-Za-z0-9])|[0-9]+$',  # numbers
]
# similarity threshold
st = 0.5
# depth of all leaf nodes
depth = 4

"""
configurations for training & predicting
"""
options = dict()

options["data_dir"] = "./data"

options['data_rel_path'] = {
    "semantic_vec": "hdfs/event2semantic_vec.json",
    "train": "hdfs/hdfs_train",
    "val": "hdfs/hdfs_test_normal",
    "test": ""
}

options['device'] = "cpu"

# Sampling
options['sample'] = "sliding_window"
options['window_size'] = 10  # if fix_window

# Features
options['sequential_features'] = True
options['quantitative_features'] = True
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
options['model_name'] = "loganomaly"
options['save_dir'] = "../result/loganomaly/"

# Predict
options['model_path'] = "./result/loganomaly/loganomaly_last.pth"
options['num_candidates'] = 9

seed_everything(seed=1234)

EMBED_DIM = 300

model = DeepLog(input_size=options['input_size'],
                hidden_size=options['hidden_size'],
                num_layers=options['num_layers'],
                num_keys=options['num_classes'])

"""
configurations for API
"""
SERVER_PORT = 8999
UPLOAD_FOLDER = "data/logs/raw"
ALLOWED_EXTENSIONS = {"txt", "raw_log_data"}

# -*- coding: utf8 -*-

import os

from pipeline import (DefaultDetector, DefaultParser,
                      DefaultTrainerWrapper, Pipeline)
from .hdfs_train_dataset import HdfsTrainDataset
from pipeline.datasets.hdfs_predict_dataset import HdfsDeepLogPredictDataset
from pipeline.feature_extractors.hdfs_deep_log_feature_extractor import HdfsDeepLogFeatureExtractor
from pipeline.alarms.alarm_stub import AlarmStub

from x.data_source import MongoDataSource
from storage.log_storage import ElasticsearchLogStorage

train_pipe = Pipeline("hdfs-train-pipeline")
train_pipe.add([DefaultParser("hdfs-train-pipeline-parser"),
                HdfsDeepLogFeatureExtractor("hdfs-train-pipeline-feature-extractor"),
                HdfsTrainDataset("hdfs-train-pipeline-dataset"),
                DefaultTrainerWrapper("hdfs-train-pipeline-trainer")])

test_pipe = Pipeline("hdfs-evaluation-pipeline")
test_pipe.add([DefaultParser("hdfs-evaluation-pipeline-parser"),
               HdfsDeepLogFeatureExtractor("hdfs-evaluation-pipeline-feature-extractor"),
               HdfsTrainDataset("hdfs-evaluation-pipeline-dataset")])

predict_pipe = Pipeline("hdfs-predict-pipeline")
predict_pipe.add([DefaultParser("hdfs-predict-pipeline-parser"),
                  HdfsDeepLogFeatureExtractor("hdfs-predict-pipeline-feature-extractor"),
                  HdfsDeepLogPredictDataset("hdfs-predict-pipeline-dataset"),
                  DefaultDetector("hdfs-predict-pipeline-detector"),
                  AlarmStub("hdfs-predict-pipeline-alarm")])

config = {
    "data_source": MongoDataSource(
        database="db_demo", collection_name="coll_demo", seq_no_column="_id"),
    "structured_log_store": ElasticsearchLogStorage("localhost", "9200", "idx_demo_structured"),
    "event_template_store": ElasticsearchLogStorage("localhost", "9200", "idx_demo_template"),
    "data_dir": os.path.join("data", "logs"),
    # "raw_log_filename" is set by main
    "log_format": "<Date> <Time> <Pid> <Level> <Component>: <Content>",
    "depth": 4,
    "st": 0.5,
    "regex": [
        r"blk_(|-)[0-9]+",                                        # block id
        r"(/|)([0-9]+\.){3}[0-9]+(:[0-9]+|)(:|)",                 # IP + optional port number
        r"(?<=[^A-Za-z0-9])(\-?\+?\d+)(?=[^A-Za-z0-9])|[0-9]+$",  # numbers
    ],
    "label_filepath": "data/anomaly_label.csv",
    "window_size": 10,
    "device": "cuda",
    "model_class": "DeepLog",
    "model_name": "deeplog",
    "save_dir": "data/models/deeplog",
    "model_path": "data/models/deeplog/default.pth",
    "input_size": 1,
    "train_ratio": 0.9,
    "hidden_size": 128,
    "num_candidates": 9,
    "num_classes": 29,
    "batch_size": 128,
    "num_workers": 8,
    "num_layers": 2,
    "sequential_features": True,
    "quantitative_features": True,
    "semantic_features": False,
    "lr": 0.01,
    "lr_step": (300, 350),
    "lr_decay_ratio": 0.1,
    "accumulation_step": 1,
    "max_epoch": 370,
    "optimizer": "sgd",
    "resume_path": None,
    "url": "http://localhost:9000"
}

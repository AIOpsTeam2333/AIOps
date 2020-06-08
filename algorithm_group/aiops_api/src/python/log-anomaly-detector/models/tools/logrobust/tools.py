# -*- coding: utf8 -*-

import io
import os
import sys

sys.path.append("..")

import pandas as pd
import torch
import torch.nn as nn
import torchtext

from tools import tokenize, load_model
from constants import (DATA_DIR_LITERAL,
                       NUM_EMBEDDINGS,
                       EMBED_DIM,
                       MODEL_PATH_LITERAL,
                       BATCH_SIZE_LITERAL)
from models.logrobust import LogRobust
from .predict import Predictor
from .train import Trainer


def pre():
    """Pre-process model."""

    print("Pre-processing...")

    # load data
    text = torchtext.data.Field(
        sequential=True, use_vocab=True, lower=True,
        tokenize=tokenize, batch_first=True,
        is_target=False, include_lengths=True)
    target = torchtext.data.Field(
        sequential=False, use_vocab=False,
        batch_first=True, is_target=True)
    dataset = torchtext.data.TabularDataset(
        "", format='csv',
        fields={"question_text": ('text', text),
                "target": ('target', target)})
    data_test = torchtext.data.TabularDataset(
        "", format='csv',
        fields={"question_text": ('text', text)})

    # build vocab
    text.build_vocab(dataset, data_test, min_freq=3)
    text.vocab.load_vectors(torchtext.vocab.Vectors("data/embeddings.vec"))
    vocab_size = len(text.vocab.itos)
    padding_idx = text.vocab.stoi[text.pad_token]

    # split data
    data_train, data_val = dataset.split(split_ratio=0.9)

    print("train set size:", len(data_train))
    print("val set size:", len(data_val))
    print("test set size:", len(data_test))
    print("vocab size:", len(text.vocab.itos))
    print("embed shape:", text.vocab.vectors.shape)
    print('')

    args_dict = {
        "data_train": data_train, "data_val": data_val,
        "data_test": data_test, "vocab_size": vocab_size,
        "padding_idx": padding_idx}

    return args_dict


def train(**config):
    """Train model.
    """
    print("Training...")

    data_dir = config[DATA_DIR_LITERAL]
    log_template_filepath = os.path.join(data_dir, config["log_template_filepath"])
    feature_filepath = os.path.join(data_dir, config["feature_filepath"])

    event_template_df = pd.read_csv(log_template_filepath, dtype={"EventId": object})
    event_templates = event_template_df["EventTemplate"].to_list()

    processed = [tokenize(et) for et in event_templates]
    word_tfidf = tfidf(processed)

    word2idx = {}
    embeddings = torch.zeros(NUM_EMBEDDINGS, EMBED_DIM)
    with io.open("../../../data/embeddings.vec",
                 "r",
                 encoding="utf-8",
                 newline="\n",
                 errors="ignore") as fin:
        _, _ = map(int, fin.readline().split())
        next_id = 0
        for ln in fin.readlines():
            tokens = ln.split()
            word2idx[tokens[0]] = next_id
            embeddings[next_id] = torch.tensor(list(map(float, tokens[1:])), dtype=torch.float32)
            next_id += 1
    embeddings = nn.Embedding.from_pretrained(embeddings)

    # semantic_vectors[0] is dummy
    semantic_vectors = torch.zeros(len(processed) + 1, EMBED_DIM)
    for i, (et, tfidf) in enumerate(zip(processed, word_tfidf), start=1):
        vec = torch.zeros(EMBED_DIM)
        for tk in et:
            if tk in word2idx:
                vec += embeddings(torch.LongTensor([word2idx[tk]])).view(-1) * tfidf[tk]
            # TODO: how to deal with UNK better?
        vec /= len(et)
        semantic_vectors[i] = vec

    # load data and embed
    data_train = config["data_train"]

    # define model
    model = LogRobust({
        # "padding_idx": config["padding_idx"],
        "pretrained_embed": semantic_vectors,
    })

    # define trainer
    trainer_args = {
        "epochs": 5,
        "batch_size": 128,
        "validate": True,
        "save_best_dev": True,
        "use_cuda": True,
        "print_every_step": 1000,
        "optimizer": torch.optim.Adam(model.parameters(), lr=1e-3),
        "model_path": MODEL_PATH_LITERAL,
        "eval_metrics": "f1",
    }
    trainer = Trainer(**trainer_args)

    # train
    data_val = config["data_val"]
    trainer.train(model, data_train, dev_data=data_val)


def predict(**config):
    """Inference using model.
    """
    print("Predicting...")

    data_dir = config[DATA_DIR_LITERAL]
    log_template_filepath = os.path.join(data_dir, config["log_template_filepath"])
    feature_filepath = os.path.join(data_dir, config["feature_filepath"])

    event_template_df = pd.read_csv(log_template_filepath, dtype={"EventId": object})
    event_templates = event_template_df["EventTemplate"].to_list()

    processed = [tokenize(et) for et in event_templates]
    word_tfidf = tfidf(processed)

    word2idx = {}
    embeddings = torch.zeros(NUM_EMBEDDINGS, EMBED_DIM)
    with io.open("../../../data/embeddings.vec",
                 "r",
                 encoding="utf-8",
                 newline="\n",
                 errors="ignore") as fin:
        _, _ = map(int, fin.readline().split())
        next_id = 0
        for ln in fin.readlines():
            tokens = ln.split()
            word2idx[tokens[0]] = next_id
            embeddings[next_id] = torch.tensor(list(map(float, tokens[1:])), dtype=torch.float32)
            next_id += 1
    embeddings = nn.Embedding.from_pretrained(embeddings)

    # semantic_vectors[0] is dummy
    semantic_vectors = torch.zeros(len(processed) + 1, EMBED_DIM)
    for i, (et, tfidf) in enumerate(zip(processed, word_tfidf), start=1):
        vec = torch.zeros(EMBED_DIM)
        for tk in et:
            if tk in word2idx:
                vec += embeddings(torch.LongTensor([word2idx[tk]])).view(-1) * tfidf[tk]
            # TODO: how to deal with UNK better?
        vec /= len(et)
        semantic_vectors[i] = vec

    # define model
    model = LogRobust({
        # "padding_idx": args["padding_idx"],
        "pretrained_embed": semantic_vectors,
    })
    load_model(model, config[MODEL_PATH_LITERAL])

    # define predictor
    predictor = Predictor(batch_size=config[BATCH_SIZE_LITERAL])

    dataset = config["dataset"]
    # predict
    y_pred = predictor.predict(model, dataset, threshold=config["threshold"])

    abnormal = [e.ID for e, label in zip(dataset, y_pred) if label == 1]
    print(abnormal)

    # save `y_pred` for training later

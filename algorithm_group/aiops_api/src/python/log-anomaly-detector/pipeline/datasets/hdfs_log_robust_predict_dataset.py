# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/6/5 11:22
@desc:
"""

import io
import logging
import tempfile

import pandas as pd
import torch
import torch.nn as nn
import torchtext

from pipeline import AbstractCommand
from storage.log_storage import LogStorage
from vec import calculate_tfidf
from tools import tokenize


class HdfsLogRobustPredictDataset(AbstractCommand):
    def __init__(self, command_id):
        super(HdfsLogRobustPredictDataset, self).__init__(command_id)

    def __call__(self, **kwargs):
        # data structure of `features`
        #   * BlockId
        #   * Sequence
        #   * Label
        df_features: pd.DataFrame = kwargs["features"]
        num_sessions = len(df_features)

        block_id_field = torchtext.data.Field(
            sequential=False,
            use_vocab=True
        )
        sequence_field = torchtext.data.Field(
            sequential=True,
            use_vocab=True,
            include_lengths=True,
            fix_length=50,
            batch_first=True
        )
        label_field = torchtext.data.Field(
            sequential=False,
            use_vocab=False,
            is_target=True
        )

        with tempfile.TemporaryFile("w+t", encoding="utf-8") as tmp:
            filename = tmp.name + ".csv"
            df_features.to_csv(filename, index=False)
            dataset = torchtext.data.TabularDataset(
                filename,
                format="csv",
                fields=[("window_id", block_id_field),
                        ("sequence", sequence_field),
                        ("label", label_field)],
                skip_header=True
            )
        block_id_field.build_vocab(dataset)

        semantic_vectors = self._generate_semantic_vectors(
            kwargs["event_template_store"], "data/embeddings.vec"
        )
        sequence_field.build_vocab(dataset)
        sequence_field.vocab.set_vectors(
            {str(i + 1): i for i in range(0, len(semantic_vectors) - 2)},
            semantic_vectors[2:], 300
        )

        # TODO: needed?
        # if sample_ratio != 1:
        #     result_logs, labels = down_sample(result_logs, labels, sample_ratio)

        logging.info(f"#sessions: {num_sessions}, #seqs: {len(dataset)}")

        return {
            **kwargs,
            "dataset": dataset,
            "pretrained_embed": sequence_field.vocab.vectors.to(
                "cuda" if torch.cuda.is_available() else "cpu"),
            "padding_idx": sequence_field.vocab.stoi[sequence_field.pad_token],
        }

    @staticmethod
    def _generate_semantic_vectors(log_store: LogStorage, embeddings_filepath):
        df_event_template = log_store.get()
        event_templates = df_event_template["EventTemplate"].to_list()

        processed = [tokenize(et) for et in event_templates]
        word_tfidf = calculate_tfidf(processed)

        word2idx = {}
        with io.open(
                embeddings_filepath, mode="r", encoding="utf-8",
                newline="\n", errors="ignore") as fin:
            num_embeddings, embed_dim = [int(e) for e in fin.readline().strip().split()]
            embeddings = torch.zeros(num_embeddings, embed_dim)
            next_id = 0
            for ln in fin.readlines():
                tokens = ln.strip().split()
                word2idx[tokens[0]] = next_id
                embeddings[next_id] = torch.tensor([float(e) for e in tokens[1:]], dtype=torch.float32)
                next_id += 1
        embeddings = nn.Embedding.from_pretrained(embeddings)

        # semantic_vectors[0, 1] are for <unk> and <pad> respectively
        semantic_vectors = torch.zeros(len(processed) + 2, embed_dim)
        for i, (et, tfidf) in enumerate(zip(processed, word_tfidf), start=2):
            vec = torch.zeros(embed_dim)
            for tk in et:
                if tk in word2idx:
                    vec += embeddings(torch.tensor([word2idx[tk]], dtype=torch.int64)).view(-1) * tfidf[tk]
                # TODO: how to deal with UNK better?
            vec /= len(et)
            semantic_vectors[i] = vec

        return semantic_vectors

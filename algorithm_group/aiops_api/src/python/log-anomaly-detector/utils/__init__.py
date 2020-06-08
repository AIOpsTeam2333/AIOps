from collections import OrderedDict
import io
import os
import random
import regex as re
import string

import nltk
import numpy as np
import torch
from tqdm import tqdm


def save_parameters(options, filename):
    with open(filename, "w+") as f:
        for key in options.keys():
            f.write("{}: {}\n".format(key, options[key]))


# https://gist.github.com/KirillVladimirov/005ec7f762293d2321385580d3dbe335
def seed_everything(seed=1234):
    random.seed(seed)
    os.environ['PYTHONHASHSEED'] = str(seed)
    np.random.seed(seed)
    torch.manual_seed(seed)
    # torch.cuda.manual_seed(seed)
    # torch.backends.cudnn.deterministic = True


# https://blog.csdn.net/folk_/article/details/80208557
def train_val_split(logs_meta, labels, val_ratio=0.1):
    total_num = len(labels)
    train_index = list(range(total_num))
    train_logs = {}
    val_logs = {}
    for key in logs_meta.keys():
        train_logs[key] = []
        val_logs[key] = []
    train_labels = []
    val_labels = []
    val_num = int(total_num * val_ratio)

    for i in range(val_num):
        random_index = int(np.random.uniform(0, len(train_index)))
        for key in logs_meta.keys():
            val_logs[key].append(logs_meta[key][random_index])
        val_labels.append(labels[random_index])
        del train_index[random_index]

    for i in range(total_num - val_num):
        for key in logs_meta.keys():
            train_logs[key].append(logs_meta[key][train_index[i]])
        train_labels.append(labels[train_index[i]])

    return train_logs, train_labels, val_logs, val_labels


def load_vectors(filepath: str) -> dict:
    fin = io.open(filepath, 'r', encoding='utf-8', newline='\n', errors='ignore')
    num_tokens, embed_size = map(int, fin.readline().split())
    data = {}
    for line in tqdm(fin):
        tokens = line.strip().split()
        data[tokens[0]] = list(map(float, tokens[1:]))
    return data


def pascal_to_snake(s: str):
    return "_".join([t.lower() for t in re.findall("[A-Z][a-z]*", s)])


def snake_to_pascal(s: str):
    return "".join([t[0].upper() + t[1:] for t in s.split("_")])


def should_discard(word):
    def _is_punctuation():
        return word in string.punctuation

    def _is_stop_word():
        return word in nltk.corpus.stopwords.words("english")

    def _is_number():
        return re.match(
            r"^[+-]?[0-9]+(\.[0-9]+)?([eE][+-]?[0-9]+)?$", word) is not None
    return _is_punctuation() or _is_stop_word() or _is_number()


def extract_embeddings(pretrained_embed_filepath: str,
                       max_num_words: int = 50000,
                       output_filepath: str = "./embeddings.vec"):
    with io.open(
            pretrained_embed_filepath, mode="r", encoding="utf-8",
            newline="\n", errors="ignore") as fin:
        num_words, embed_dim = [int(t) for t in fin.readline().split()]
        counter, limit = 0, min(max_num_words, num_words)
        embeddings = OrderedDict()
        with tqdm(total=limit) as pbar:
            for ln in fin.readlines():
                ln = ln.split()
                word = ln[0]
                if should_discard(word):
                    continue
                embeddings[word] = [float(t) for t in ln[1:]]
                counter += 1
                pbar.update(1)
                if counter >= limit:
                    break

    with io.open(
            output_filepath, mode="w", encoding="utf-8",
            newline="\n", errors="ignore") as fout:
        fout.write(f"{counter} {embed_dim}\n")
        for word, embed in tqdm(embeddings.items()):
            ln = word
            for t in embed:
                ln += f" {t}"
            fout.write(ln + "\n")

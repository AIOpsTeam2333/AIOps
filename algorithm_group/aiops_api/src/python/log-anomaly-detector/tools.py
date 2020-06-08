# -*- coding: utf8 -*-

import io
import logging
import yaml

import torch
import torch.nn.functional as F

from exceptions import InvalidArgumentException

NEG_INF = -10000
TINY_FLOAT = 1e-6


def seq_mask(seq_len, max_len):
    """Create sequence mask.

    Parameters
    ----------
    seq_len: torch.long, shape [batch_size],
        Lengths of sequences in a batch.
    max_len: int
        The maximum sequence length in a batch.

    Returns
    -------
    mask: torch.long, shape (batch_size, max_seq_len)
        Mask tensor for sequence.
    """
    # (batch_size, max_seq_len)
    idx = torch.arange(max_len).to(seq_len).repeat(seq_len.size(0), 1)
    # seq_len: (batch_size) -> (batch_size, 1) -> broadcast
    mask = torch.gt(seq_len.unsqueeze(1), idx).to(seq_len)

    return mask


def mask_softmax(matrix, mask=None):
    """Perform softmax on length dimension with masking.

    Parameters
    ----------
    matrix: torch.float, shape [batch_size, .., max_len]
    mask: torch.long, shape [batch_size, max_len]
        Mask tensor for sequence.

    Returns
    -------
    output: torch.float, shape [batch_size, .., max_len]
        Normalized output in length dimension.
    """

    if mask is None:
        result = F.softmax(matrix, dim=-1)
    else:
        mask_norm = ((1 - mask) * NEG_INF).to(matrix)
        for _ in range(matrix.dim() - mask_norm.dim()):
            mask_norm = mask_norm.unsqueeze(1)
        result = F.softmax(matrix + mask_norm, dim=-1)

    return result


def mask_mean(seq, mask=None):
    """Compute mask average on length dimension.

    Parameters
    ----------
    seq : torch.float, size [batch, max_seq_len, n_channels],
        Sequence vector.
    mask : torch.long, size [batch, max_seq_len],
        Mask vector, with 0 for mask.

    Returns
    -------
    mask_mean : torch.float, size [batch, n_channels]
        Mask mean of sequence.
    """

    if mask is None:
        return torch.mean(seq, dim=1)

    mask_sum = torch.sum(  # [b,msl,nc]->[b,nc]
        seq * mask.unsqueeze(-1).float(), dim=1)
    seq_len = torch.sum(mask, dim=-1)  # [b]
    return mask_sum / (seq_len.unsqueeze(-1).float() + TINY_FLOAT)


def mask_max(seq, mask=None):
    """Compute mask max on length dimension.

    Parameters
    ----------
    seq : torch.float, size [batch, max_seq_len, n_channels],
        Sequence vector.
    mask : torch.long, size [batch, max_seq_len],
        Mask vector, with 0 for mask.

    Returns
    -------
    mask_max : torch.float, size [batch, n_channels]
        Mask max of sequence.
    """

    if mask is None:
        return torch.mean(seq, dim=1)

    # [b,msl,nc]->[b,nc]
    mask_max_ret, _ = torch.max(
        seq + (1 - mask.unsqueeze(-1).float()) * NEG_INF,
        dim=1
    )

    return mask_max_ret


from typing import List
import nltk
import regex as re
import string


def tokenize(event_template: str) -> List[str]:
    # remove operators
    event_template = re.sub(r"=|\+|-|\*|\/|%|&|\||!|\^|<|>", " ", event_template)
    # remove punctuations
    event_template = event_template.translate(str.maketrans(string.punctuation, " " * len(string.punctuation)))
    # remove numbers
    event_template = re.sub(r"\d+", " ", event_template)
    # remove delimiter
    event_template = re.sub(r"\s+", " ", event_template)
    # remove leading and trailing spaces
    event_template = event_template.strip()
    # extract all english words
    tokens = re.findall(r"[A-Z]{2,}|[a-zA-Z][a-z]*", event_template)
    # remove stop words and to lower case
    stop_words = set(nltk.corpus.stopwords.words("english"))
    tokens = [t.lower() for t in tokens
              if t not in stop_words]
    return tokens


def save_model(model, model_path):
    """Save model."""
    torch.save(model.state_dict(), model_path)


def load_model(model, model_path, use_cuda=True):
    """Load model."""
    map_location = "cpu"
    if use_cuda and torch.cuda.is_available():
        map_location = 'cuda:0'
    model.load_state_dict(torch.load(model_path, map_location))
    return model


def read_yaml(filepath):
    try:
        return yaml.load(
            io.open(
                filepath, encoding="utf-8", newline="\r\n", errors="ignore"),
            Loader=yaml.FullLoader)
    except Exception as err:
        raise err


def get_extension(filename) -> str:
    return filename.rsplit(".", 1)[1].lower() if "." in filename else ""


def is_allowed_file(filename, allowed_extensions):
    return get_extension(filename).lower() in allowed_extensions


def load_class(qualified_name):
    if isinstance(qualified_name, str):
        comps = qualified_name.split(".")
    elif isinstance(qualified_name, list):
        comps = qualified_name
    else:
        raise InvalidArgumentException()
    mod = __import__(comps[0])
    for comp in comps[1:]:
        mod = getattr(mod, comp)
    return mod


def pick_embeddings(input_filepath, output_filepath, num_words=50000):
    stop_words = set(nltk.corpus.stopwords.words("english"))

    logging.info("Picking embeddings ...")

    with io.open(input_filepath, mode="r", encoding="utf-8", newline="\n", errors="ignore") as fin:
        _, embed_dim = [int(e) for e in fin.readline().split()]
        embeddings = {}
        for ln in fin.readlines():
            ln = ln.strip().split()
            word = ln[0].lower()
            if re.match(r"^[a-zA-Z]+$", word) is not None \
                    and word not in stop_words:
                e = [float(token) for token in ln[1:]]
                embeddings[word] = e
                if len(embeddings) == num_words:
                    break

    logging.info("Picking embeddings done")

    logging.info("Writing result ...")

    with io.open(output_filepath, mode="w", encoding="utf-8") as fout:
        fout.write(f"{len(embeddings)} {embed_dim}\n")
        for word, e in embeddings.items():
            fout.write(f"{word} {' '.join([str(token) for token in e])}\n")

    logging.info("Writing result done")

# -*- coding: utf8 -*-

from collections import Counter
from functools import reduce
from math import log
from typing import List

import numpy as np


def calculate_tfidf(processed_event_templates: List[List[str]]) -> List[dict]:
    """Calculate TF-IDF weight

    :param processed_event_templates: list of lists of english words
    :return: a list of dict, the i-th element corresponds to the i-th event-template,
             and a dict contains (word, TF-IDF weight) pairs, i.e., the keys of a dict
             are english words and the values of a dict are corresponding TF-IDf weights.
    """
    def _softmax(weights: dict):
        max_w = max(weights.values())
        d = sum([np.exp(w - max_w) for w in weights.values()])
        normalized = {word: np.exp(weight - max_w) / d
                      for word, weight in weights.items()}
        return normalized

    result = []
    num_templates = len(processed_event_templates)
    for et in processed_event_templates:
        num_words = len(et)
        # (word, count) pairs
        word_count = Counter(et)
        # (word, TF) pairs
        word_tf = {word: (count / num_words)
                   for word, count in word_count.items()}
        # (word, IDF) pairs
        #   `map(lambda et2: word in et2, processed_event_templates)` yields
        # a list whose elements are bool values indicating whether or not
        # corresponding template contains the word.
        #   Then, `sum(map(...))` gives the number of templates that contains
        # the word. Apparently, `log2(num_templates / sum(map(...)))` calculates
        # IDF of the word.
        #   For the sake of avoiding duplication judgments, I use `for word
        # in word_count` rather than `for word in et`.
        #
        # Example:
        #     event_templates = [["a", "a", "a", "b"],
        #                        ["b", "c", "c"],
        #                        ["b", "b", "b", "c"]]
        # event_templates[0]'s `word_count` = {"a": 3, "b": 1}
        # 1. map(...)      -> [True, False, False]  # only event_templates[0] contains "a"
        # 2. sum(map(...)) -> 1                     # `True` and `False` will be converted
        #                                           # into 1 and 0 respectively
        # 3. log2(num_templates / sum(map(...))) -> 1.58...
        # 4. word_idf = {"a": 1.58, "b": 0}
        word_idf = {word: (log(num_templates /
                               reduce(
                                   lambda accum, et2: accum + (word in et2),
                                   processed_event_templates, 0)))
                    for word in word_count}
        assert len(word_tf) == len(word_idf)
        word_tf_idf = {word: (word_tf[word] * word_idf[word])
                       for word in word_count}
        result.append(_softmax(word_tf_idf))
    return result


def cos_sim(a, b):
    return np.dot(a, b) / (np.linalg.norm(a) * np.linalg.norm(b))

import os
import json
from collections import Counter

import numpy as np
import pandas as pd
from tqdm import tqdm


def read_json(filename):
    with open(filename, 'r') as load_f:
        file_dict = json.load(load_f)
    return file_dict


def trp(l, n):
    """ Truncate or pad a list """
    r = l[:n]
    if len(r) < n:
        r.extend(list([0]) * (n - len(r)))
    return r


def down_sample(logs, labels, sample_ratio):
    print('sampling...')
    total_num = len(labels)
    all_index = list(range(total_num))
    sample_logs = {}
    for key in logs.keys():
        sample_logs[key] = []
    sample_labels = []
    sample_num = int(total_num * sample_ratio)

    for i in tqdm(range(sample_num)):
        random_index = int(np.random.uniform(0, len(all_index)))
        for key in logs.keys():
            sample_logs[key].append(logs[key][random_index])
        sample_labels.append(labels[random_index])
        del all_index[random_index]
    return sample_logs, sample_labels


def sliding_window(data_dir: str,
                   data_rel_path: dict,
                   datatype,
                   window_size,
                   sample_ratio=1):
    """
    dataset structure
        result_logs : dict:
            result_logs['feature0'] : list
            result_logs['feature1'] : list
            ...
        labels : list
    """
    event2semantic_vec = read_json(os.path.join(data_dir,
                                                data_rel_path["semantic_vec"]))
    num_sessions = 0
    result_logs = {
        "sequential_features": [],
        "quantitative_features": [],
        "semantic_features": []
    }
    labels = []

    filepath = os.path.join(data_dir, data_rel_path[datatype])
    with open(filepath, 'r') as f:
        for line in f.readlines():
            num_sessions += 1
            line = tuple(map(lambda n: n - 1, map(int, line.strip().split())))

            for i in range(len(line) - window_size):
                sequential_patterns = list(line[i:i + window_size])
                quantitative_patterns = [0] * 29
                log_counter = Counter(sequential_patterns)

                for key in log_counter:
                    quantitative_patterns[key] = log_counter[key]
                semantic_pattern = []
                for event in sequential_patterns:
                    if event == 0:
                        semantic_pattern.append([-1] * 300)
                    else:
                        semantic_pattern.append(event2semantic_vec[str(event - 1)])
                sequential_patterns = np.array(sequential_patterns)[:, np.newaxis]
                quantitative_patterns = np.array(quantitative_patterns)[:, np.newaxis]
                result_logs['sequential_features'].append(sequential_patterns)
                result_logs['quantitative_features'].append(quantitative_patterns)
                result_logs['semantic_features'].append(semantic_pattern)
                labels.append(line[i + window_size])

    if sample_ratio != 1:
        result_logs, labels = down_sample(result_logs, labels, sample_ratio)

    print(f"File {filepath}, "
          f"#sessions {num_sessions}, "
          f"#seqs {len(result_logs['sequential_features'])}")

    return result_logs, labels


def session_window(data_dir: str, data_rel_path: dict, datatype: str, sample_ratio=1):
    event2semantic_vec = read_json(os.path.join(data_dir,
                                                data_rel_path["semantic_vec"]))
    result_logs = {
        "sequential_features": [],
        "quantitative_features": [],
        "semantic_features": []
    }
    labels = []

    filepath = os.path.join(data_dir, data_rel_path[datatype])
    train_df = pd.read_csv(filepath)
    for i in tqdm(range(len(train_df))):
        ori_seq = [
            int(event_id) for event_id in train_df["Sequence"][i].split(' ')
        ]
        sequential_features = trp(ori_seq, 50)
        semantic_features = []
        for event in sequential_features:
            if event == 0:
                semantic_features.append([-1] * 300)
            else:
                semantic_features.append(event2semantic_vec[str(event - 1)])
        quantitative_features = [0] * 29
        log_counter = Counter(sequential_features)

        for key in log_counter:
            quantitative_features[key] = log_counter[key]

        sequential_features = np.array(sequential_features)[:, np.newaxis]
        quantitative_features = np.array(quantitative_features)[:, np.newaxis]
        result_logs['sequential_features'].append(sequential_features)
        result_logs['quantitative_features'].append(quantitative_features)
        result_logs['semantic_features'].append(semantic_features)
        labels.append(int(train_df["label"][i]))

    if sample_ratio != 1:
        result_logs, labels = down_sample(result_logs, labels, sample_ratio)

    # result_logs, labels = up_sample(result_logs, labels)

    print(f"File {filepath}, "
          f"#sessions {len(result_logs['semantic_features'])}")
    return result_logs, labels

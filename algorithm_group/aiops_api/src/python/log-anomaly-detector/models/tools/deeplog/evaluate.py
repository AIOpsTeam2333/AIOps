# -*- coding: utf8 -*-

import logging
import time

import numpy as np
import torch
from torch.utils.data import DataLoader
import torch.nn.functional as F
from tqdm import tqdm

from utils.dataset import LogDataset
from utils.dataset.sampling import sliding_window, session_window


class SupervisedEvaluator:
    def __init__(self, model, options):
        self.model = model
        self.data_dir = options['data_dir']
        self.data_rel_path = options["data_rel_path"]
        self.device = options['device']
        self.model_path = options['model_path']
        self.window_size = options['window_size']
        self.num_candidates = options['num_candidates']
        self.num_classes = options['num_classes']
        self.input_size = options['input_size']
        self.sequential_patterns = options['sequential_features']
        self.quantitative_patterns = options['quantitative_features']
        self.semantic_features = options['semantic_features']
        self.batch_size = options['batch_size']

    def evaluate(self):
        model = self.model.to(self.device)
        model.load_state_dict(torch.load(self.model_path)['state_dict'])
        model.eval()
        logging.info(f'Model path: {self.model_path}')
        X, y = session_window(self.data_dir,
                              self.data_rel_path,
                              datatype='test')
        eval_loader = DataLoader(LogDataset(features=X,
                                            labels=y,
                                            use_sequential_features=self.sequential_patterns,
                                            use_quantitative_features=self.quantitative_patterns,
                                            use_semantic_features=self.semantic_features),
                                 batch_size=self.batch_size,
                                 shuffle=False,
                                 pin_memory=True)
        progress_bar = tqdm(eval_loader, desc="\r")
        TP, FP, FN, TN = 0, 0, 0, 0
        for i, (log, label) in enumerate(progress_bar):
            features = []
            for value in log.values():
                features.append(value.clone().to(self.device))
            output = self.model(features=features, device=self.device)
            output = F.sigmoid(output)[:, 0].cpu().detach().numpy()
            # predicted = torch.argmax(output, dim=1).cpu().numpy()
            predicted = (output < 0.2).astype(int)
            label = np.array([y.cpu() for y in label])
            TP += ((predicted == 1) * (label == 1)).sum()
            FP += ((predicted == 1) * (label == 0)).sum()
            FN += ((predicted == 0) * (label == 1)).sum()
            TN += ((predicted == 0) * (label == 0)).sum()
        P, R = 100 * TP / (TP + FP), 100 * TP / (TP + FN)
        F1 = 2 * P * R / (P + R)
        print(f'false positive (FP): {FP}, '
              f'false negative (FN): {FN}, '
              f'Precision: {P:.3f}%, '
              f'Recall: {R:.3f}%, '
              f'F1-measure: {F1:.3f}%')


class UnsupervisedEvaluator:
    def __init(self, model, options):
        self.model = model
        self.data_dir = options['data_dir']
        self.data_rel_path = options["data_rel_path"]
        self.device = options['device']
        self.model_path = options['model_path']
        self.window_size = options['window_size']
        self.num_candidates = options['num_candidates']
        self.num_classes = options['num_classes']
        self.input_size = options['input_size']
        self.sequential_patterns = options['sequential_features']
        self.quantitative_patterns = options['quantitative_features']
        self.semantic_features = options['semantic_features']
        self.batch_size = options['batch_size']

    def evaluate(self, x, y):
        model = self.model.to(self.device)
        model.load_state_dict(torch.load(self.model_path)['state_dict'])
        model.eval()
        print(f"Model path: {self.model_path}")
        # test_normal_loader, test_normal_length = None, None  # generate('hdfs_test_normal')
        # test_abnormal_loader, test_abnormal_length = None, None  # generate('hdfs_test_abnormal')
        data_loader = DataLoader(x, batch_size=self.batch_size, pin_memory=True)
        TP, FP, FN, TN = 0, 0, 0, 0
        start_time = time.time()
        with torch.no_grad():
            for batch in data_loader:
                features, labels, ground_truth = batch
                seq0 = torch.tensor(seq0, dtype=torch.float).view(
                        -1, self.window_size, self.input_size).to(self.device)
                seq1 = torch.tensor(seq1, dtype=torch.float).view(
                    -1, self.num_classes, self.input_size).to(self.device)
                label = torch.tensor(label).view(-1).to(self.device)
                output = model(features=[seq0, seq1], device=self.device)
                predicted = torch.argsort(output,
                                          1)[0][-self.num_candidates:]
                if label not in predicted:
                    pass

        # Compute precision, recall and F1-measure
        FN = 0 - TP
        P = 100 * TP / (TP + FP)
        R = 100 * TP / (TP + FN)
        F1 = 2 * P * R / (P + R)
        print(
            'false positive (FP): {}, false negative (FN): {}, Precision: {:.3f}%, Recall: {:.3f}%, F1-measure: {:.3f}%'
                .format(FP, FN, P, R, F1))
        print('Finished Predicting')
        elapsed_time = time.time() - start_time
        print(f'Elapsed time: {elapsed_time}')

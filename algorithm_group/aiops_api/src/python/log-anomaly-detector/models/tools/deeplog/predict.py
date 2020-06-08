# -*- coding: utf-8 -*-

import logging

import torch
import torch.nn.functional as F
from torch.utils.data import DataLoader
from tqdm import tqdm

from utils.dataset import LogDataset


class Predictor:
    def __init__(self, model, config):
        self.model = model
        self.device = torch.device(config['device'])
        self.model_path = config['model_path']
        self.window_size = config['window_size']
        self.num_candidates = config['num_candidates']
        self.num_classes = config['num_classes']
        self.input_size = config['input_size']
        self.sequential_patterns = config['sequential_features']
        self.quantitative_patterns = config['quantitative_features']
        self.semantic_features = config['semantic_features']
        self.batch_size = config['batch_size']
        self.num_workers = config["num_workers"]

    def predict(self, x: LogDataset):
        model = self.model.to(self.device)
        # model.load_state_dict(torch.load(self.model_path)['state_dict'])
        model.load_state_dict(torch.load(self.model_path))
        model.eval()
        logging.info(f"Model path: {self.model_path}")
        data_loader = DataLoader(x, batch_size=self.batch_size,
                                 num_workers=self.num_workers, pin_memory=True)
        logs_with_error = []
        with torch.no_grad():
            for features, labels in tqdm(data_loader, desc="\r"):
                sequential_features = features["sequential_feature"]
                quantitative_features = features["quantitative_feature"]
                seq0 = torch.tensor(sequential_features, dtype=torch.float)\
                    .view(-1, self.window_size, self.input_size).to(self.device)
                seq1 = torch.tensor(quantitative_features, dtype=torch.float)\
                    .view(-1, self.num_classes, self.input_size).to(self.device)
                output = model(features=[seq0, seq1], device=self.device)
                prediction = torch.argsort(output, dim=1)[0][-self.num_candidates:]
                # TODO
                print(f"prediction: {prediction}")
        return set(logs_with_error)

    def predict_supervised(self, x: LogDataset):
        model = self.model.to(self.device)
        model.load_state_dict(torch.load(self.model_path)['state_dict'])
        model.eval()
        logging.info(f'Model path: {self.model_path}')
        data_loader = DataLoader(x, batch_size=self.batch_size,
                                 num_workers=self.num_workers, pin_memory=True)
        # TODO
        for log in tqdm(data_loader, desc="\r"):
            features = []
            for value in log.values():
                features.append(value.clone().to(self.device))
            output = model(features=features, device=self.device)
            output = F.sigmoid(output)[:, 0].cpu().detach().numpy()
            # predicted = torch.argmax(output, dim=1).cpu().numpy()
            prediction = (output < 0.2).astype(int)
            # predicted == 1 ?

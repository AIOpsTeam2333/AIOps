# -*- coding: utf8 -*-

"""
@author: HAN Qi
@time: 2020/5/28 15:19
@desc:
"""

import logging
from threading import Lock, Thread

import pandas as pd

from exceptions import ConfigException
from tools import read_yaml, load_class


class Barrier:
    def __init__(self):
        cfg = read_yaml("config/barrier.yml")

        self.limit = cfg["limit"]
        self.num_running_tasks = 0
        self.waiting_tasks = []

        self.has_train_signal = False
        self.training = False
        self.train_data = pd.DataFrame()

        self.mutex = Lock()

    def add(self, data):
        self.mutex.acquire()
        logging.critical(f"raw log data read, #waiting tasks: {len(self.waiting_tasks)}")
        self.waiting_tasks.append(data)
        if self.num_running_tasks < self.limit and \
                not self.has_train_signal and \
                not self.training:
            logging.critical(f"mutex state: {self.mutex.locked()}")
            Thread(target=self._create_new_instance).start()
            # self.mutex.release() in self._create_new_instance
        else:
            self.mutex.release()

    def finish_detecting(self):
        self.mutex.acquire()
        self.num_running_tasks = self.num_running_tasks - 1
        logging.critical(f"detecting done, #running tasks: {self.num_running_tasks}, "
                         f"#waiting tasks: {len(self.waiting_tasks)}")
        if self._can_train():
            self._start_training()
        elif len(self.waiting_tasks) > 0 and \
                not self.has_train_signal and \
                not self.training:
            Thread(target=self._create_new_instance).start()
        else:
            self.mutex.release()

    def prepare_for_training(self, data):
        self.mutex.acquire()
        logging.critical("training request received")
        self.has_train_signal = True
        self.train_data = pd.concat([self.train_data, data])
        if self._can_train():
            self._start_training()
        else:
            self.mutex.release()

    def finish_training(self):
        self.mutex.acquire()
        logging.critical(f"training done, #waiting tasks: {len(self.waiting_tasks)}")
        self.training = False
        if self._can_train():
            self._start_training()
        elif self.num_running_tasks < self.limit \
                and len(self.waiting_tasks) > 0 \
                and not self.has_train_signal \
                and not self.training:
            Thread(target=self._create_new_instance).start()
        else:
            self.mutex.release()

    def _create_new_instance(self):
        if self.num_running_tasks < self.limit \
                and len(self.waiting_tasks) > 0 \
                and not self.has_train_signal \
                and not self.training:
            self.num_running_tasks = self.num_running_tasks + 1
            data = self.waiting_tasks.pop(0)
            logging.critical("new detecting pipeline created")
            logging.critical(f"#running tasks: {self.num_running_tasks}, "
                             f"#waiting tasks: {len(self.waiting_tasks)}")
            try:
                self.mutex.release()
            except RuntimeError:
                pass
            doc = read_yaml("config/pipeline.yml")
            try:
                pipeline_config = doc["predict"]
            except KeyError:
                raise ConfigException("pipeline config for predict should be specified")
            try:
                pipeline_id = pipeline_config.pop("id")
            except KeyError:
                raise ConfigException("`id` of pipeline should be specified")
            commands = []
            for stage_name, cfg in pipeline_config.items():
                class_obj = load_class(["pipeline", stage_name + "s", cfg["class_name"]])
                stage = class_obj(cfg["id"])
                commands.append(stage)
            class_obj = load_class("pipeline.Pipeline")
            pipeline = class_obj(pipeline_id, commands)
            options = read_yaml("config/options.yml")
            structured_log_store_class_obj = load_class(
                ["storage",
                 "log_storage",
                 options["structured_log_store"]["class_name"]]
            )
            options["structured_log_store"] = structured_log_store_class_obj(
                **options["structured_log_store"]["constructor_args"]
            )
            event_template_store_class_obj = load_class(
                ["storage",
                 "log_storage",
                 options["event_template_store"]["class_name"]]
            )
            options["event_template_store"] = event_template_store_class_obj(
                **options["event_template_store"]["constructor_args"]
            )
            options["data"] = data
            pipeline.run(**options)
        else:
            self.mutex.release()

    def _can_train(self):
        return self.has_train_signal \
               and not self.training \
               and self.num_running_tasks == 0

    def _start_training(self):
        logging.critical("start training ...")
        self.training = True
        self.has_train_signal = False
        data = self.train_data
        self.train_data = pd.DataFrame()
        self.mutex.release()
        doc = read_yaml("config/pipeline.yml")
        try:
            pipeline_cfg = doc["train"]
        except KeyError:
            raise ConfigException("pipeline config for train should be specified")
        try:
            pipeline_id = pipeline_cfg.pop("id")
        except KeyError:
            raise ConfigException("`id` of pipeline should be specified")
        commands = []
        for stage_name, cfg in pipeline_cfg.items():
            class_obj = load_class(["pipeline", stage_name + "s", cfg["class_name"]])
            stage = class_obj(cfg["id"])
            commands.append(stage)
        class_obj = load_class("pipeline.Pipeline")
        pipeline = class_obj(pipeline_id, commands)
        options = read_yaml("config/options.yml")
        structured_log_store_class_obj = load_class(
            ["storage",
             "log_storage",
             options["structured_log_store"]["class_name"]]
        )
        options["structured_log_store"] = structured_log_store_class_obj(
            **options["structured_log_store"]["constructor_args"]
        )
        event_template_store_class_obj = load_class(
            ["storage",
             "log_storage",
             options["event_template_store"]["class_name"]]
        )
        options["event_template_store"] = event_template_store_class_obj(
            **options["event_template_store"]["constructor_args"]
        )
        options["data"] = data
        pipeline.run(**options)

    def _construct_pipeline(self, pipeline_cfg):
        pass

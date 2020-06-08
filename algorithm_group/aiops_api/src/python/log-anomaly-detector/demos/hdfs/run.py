#!/usr/bin/env python
# -*- coding: utf8 -*-

import os

from elasticsearch import Elasticsearch

from demos.hdfs.hdfs_pipeline_config import (config,
                                             train_pipe,
                                             test_pipe,
                                             predict_pipe)

if __name__ == "__main__":
    es = Elasticsearch()

    os.chdir("../..")

    try:
        # train_pipe.run(**config)
        # test_pipe.run(**config)
        predict_pipe.run(**config)
    except:
        pass
    finally:
        es.indices.delete(index="idx_demo_template")
        es.indices.delete(index="idx_demo_structured")

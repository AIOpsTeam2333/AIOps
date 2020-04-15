# 按照函数y_train=x_train*np.sin(x_train)生成函数
import numpy as np

import tensorflow as tf
import matplotlib.pyplot as plt
from keras.layers import Dense, Dropout
import  pandas as pd
from pandas import read_csv


import pandas as pd
from matplotlib import pyplot

import os
import time
# import timedelta
from datetime import datetime



# print(fileList)



def kpi_predict(kpiid, inputDataList,modelConfig):

    saveDirs = modelConfig[ "saveDirs" ]
    uu = modelConfig[ "uu" ]  # 起始位置
    hRate = modelConfig[ "hRate" ]   # 历史数据集大小
    nRate = modelConfig[ "nRate" ]   # 实时数据集大小（用于检验

    traintestRate = modelConfig[ "traintestRate" ]  # 划分train test比例
    max_epochs =modelConfig[ "max_epochs" ]   # 训练次数
    b_size = modelConfig[ "b_size" ]   # batch_size每批数量

    STA_windowSize_left = modelConfig[ "STA_windowSize_left" ]   # 特征提取窗口大小
    STA_windowSize_right = modelConfig[ "STA_windowSize_right" ]

    STA_fws = modelConfig[ "STA_fws" ] ;  # 特征提取分位数

    chipSize = 3000

    from keras.models import load_model

    thisTrainSaveDir=saveDirs+"/"+str(kpiid)+"/"
    model_save_path = thisTrainSaveDir + "model.h5"

    model = load_model(model_save_path)

    tpList = inputDataList
    stdd = np.std(tpList) * np.std(tpList)
    mean = np.mean(tpList)
    fws = np.percentile(tpList, STA_fws)


    input_x = [[stdd,mean,fws]]
    unknown = np.array(
        input_x
        , dtype=np.float32)
    predicted = model.predict(unknown)
    return predicted


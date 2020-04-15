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


def kpi_train_model(kpiid,rowDataFrame,modelConfig):

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
    if not os.path.exists(saveDirs):
        os.mkdir(saveDirs)

    thisTrainSaveDir=saveDirs+"/"+str(kpiid)+"/"
    if not os.path.exists(thisTrainSaveDir):
        os.mkdir(thisTrainSaveDir)


    logFile = open(thisTrainSaveDir+"log.txt", "a")  # 设置文件对象
    logFile.writelines("开始处理 \n  "+kpiid+"\n")
    logFile.writelines(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())))
    logFile.writelines("\n"+"==================================================="+"\n")
    logFile.writelines("hRate=" + str(hRate)+"\n")
    logFile.writelines("nRate=" + str(nRate)+"\n")
    logFile.writelines("traintestRate=" + str(traintestRate)+"\n")
    logFile.writelines("max_epochs=" + str(max_epochs)+"\n")
    logFile.writelines("b_size=" + str(b_size)+"\n")
    logFile.writelines("STA_windowSize_Left=" + str(STA_windowSize_left)+"\n")
    logFile.writelines("STA_windowSize_Right=" + str(STA_windowSize_right) + "\n")
    logFile.writelines("STA_fws=" + str(STA_fws)+"\n")
    # logFile.writelines(" TC =" + str( TC )+"\n")
    logFile.writelines("==================================================="+"\n")


    dataset = rowDataFrame
    # dataset.sort_index(by = ['timestamp'],axis = 0,ascending = True)
    # dataset.set_index(["timestamp"], inplace=True)

    if not os.path.exists(thisTrainSaveDir+"rowDat/"):
        os.mkdir(thisTrainSaveDir+"rowDat/")
    for i  in range(1,int(len(dataset)/chipSize)):
        pyplot.subplot("211")
        pyplot.plot(dataset.iloc[int((i-1) *(chipSize)):int(i  *chipSize), 1:2].values, label='value')
        pyplot.legend()
        pyplot.subplot("212")
        pyplot.plot(dataset.iloc[int((i-1) *(chipSize)):int(i  *chipSize), 2:3].values, label='label')
        pyplot.legend()
        pyplot.savefig(thisTrainSaveDir+"rowDat/"+str(i)+"原始数据.png", dpi=200)
        pyplot.clf()

    historyData_length = int(len(dataset) * hRate) - 100
    newData_length = int(len(dataset) * nRate) - 100

    dataset = dataset.iloc[uu:uu + historyData_length + newData_length]

    handledData = pd.DataFrame({})
    handledData['timestamp'] = []
    handledData['std'] = []
    handledData['mean'] = []
    handledData['fws'] = []
    # handledData['diffStd'] = []
    # handledData['diffMean'] = []
    # handledData['CRstd'] = []
    # handledData['CRmean'] = []
    handledData['label'] = []
    allLen = len(dataset)


    lastStd=0
    lastMean=0

    print( dataset.head(5))
    if os.path.exists(thisTrainSaveDir+"HandledData.csv"):
        names=['timestamp','std', 'mean', 'fws','label']
        handledData=read_csv(thisTrainSaveDir+"HandledData.csv", names=names, low_memory=False, skiprows=1)
    else:
        alllent=len(dataset)
        for index, row in dataset.iterrows():
            if index %1000==0:
                print("提取特征" + str(index - uu) + "/" + str(alllent))
            tpList = []
            # print(index,sizee
            for ii in range(index - STA_windowSize_left, index+STA_windowSize_right):
                try:
                    tpList.append(dataset.loc[ii]["value"])
                except:
                    continue
            if (len(tpList) < STA_windowSize_left+STA_windowSize_right):
                continue
            stdd = np.std(tpList)*np.std(tpList)
            mean = np.mean(tpList)
            fws = np.percentile(tpList, STA_fws)

            changeRate=0

            # diffStd=stdd-lastStd
            # diffMean= mean - lastMean
            # CRstd =0
            # if stdd != 0:
            #     CRstd= (stdd-lastStd)/stdd
            # CRMean =0
            # if mean != 0:
            #     CRMean= (mean-lastMean)/mean
            # lastStd=stdd
            # lastMean = mean
            handledData.loc[len(handledData)] = [row["timestamp"],
                                                 stdd,
                                                 mean,
                                                 fws,
                                                 # diffStd,
                                                 # diffMean,
                                                 # CRstd,
                                                 # CRMean,
                                                 int(row["label"])
                                                 ]
        handledData.to_csv(thisTrainSaveDir+"HandledData.csv")




    TZ_df = handledData.iloc[0:historyData_length, :]
    TZ_test = handledData.iloc[historyData_length:historyData_length + newData_length, :]

    print(TZ_df.head(5))
    print(len(TZ_df))
    # design network
    tmstpEnd=TZ_df.iloc[len(TZ_df)-1]["timestamp"]
    TZ_df.set_index(["timestamp"], inplace=True)
    TZ_test.set_index(["timestamp"], inplace=True)
    def generateErrorData(trainDF,rate):
        errorDataSample = trainDF[trainDF['label'].isin([1.0])]
        print("异常数据的长度是："+str(len(errorDataSample)))
        addRate=int( (len(errorDataSample)+len(trainDF))*rate/  len(errorDataSample)     )
        prgrs=0
        allntl=len(errorDataSample)
        for i in range(0, addRate):
            print("生成异常数据 " + str(prgrs) + "/" + str(allntl))
            prgrs=prgrs+1
            trainDF= pd.concat([trainDF,errorDataSample])
        print("现在训练数据集的长度是"+str(len(trainDF)))
        print("其中异常占比" + str(   len(trainDF[trainDF['label'].isin([1.0])]) / len(trainDF)  )       )
        return trainDF

    # TZ_df =generateErrorData(TZ_df,0.01)

    # TZ_df = TZ_df.sample(frac=1)

    train_x = TZ_df.iloc[0:int(traintestRate * len(TZ_df)), 0:3].values.astype('float32')
    train_y = TZ_df.iloc[0:int(traintestRate * len(TZ_df)), 3:4].values.astype('float32')

    test_x = TZ_df.iloc[int(traintestRate * len(TZ_df)):len(TZ_df), 0:3].values.astype('float32')
    test_y = TZ_df.iloc[int(traintestRate * len(TZ_df)):len(TZ_df), 3:4].values.astype('float32')



    import keras as K
    # 2. 定义模型
    init = K.initializers.glorot_uniform(seed=1)
    simple_adam = K.optimizers.Adam()
    model = K.models.Sequential()
    model.add(K.layers.Dense(units=128, input_dim=3, kernel_initializer=init, activation='relu'))
    model.add(Dropout(0.01))
    model.add(K.layers.Dense(units=128, kernel_initializer=init, activation='relu'))
    model.add(Dropout(0.01))
    model.add(K.layers.Dense(units=1, kernel_initializer=init, activation='sigmoid'))
    model.compile(
                loss='binary_crossentropy',
                  optimizer=simple_adam,
                  metrics=['accuracy'
                            # metric_precision,
                            # metric_recall,
                            # metric_F1score
                           ]
                  )

    print("Starting training ")
    history = model.fit(
                        train_x,
                        train_y,

                        batch_size=b_size,
                        epochs=max_epochs,
                        validation_data=(test_x, test_y),
                        # class_weight='auto',
                        shuffle=True,
                        verbose=2)
    print("Training finished \n")

    model_save_path = thisTrainSaveDir+"model.h5"
    # 保存模型
    model.save(model_save_path)
    logFile.writelines(" 完成训练，模型已保存\n")
    # from keras.models import load_model
    # model = load_model(model_save_path)

    plt.subplot("211")
    pyplot.plot(history.history['loss'], label='train')
    pyplot.plot(history.history['val_loss'], label='test')
    pyplot.legend()
    pyplot.savefig(thisTrainSaveDir+"trainLoss.png", dpi=100)
    pyplot.clf()

    logFile.writelines("===================================================" + "\n")
    logFile.writelines("开始调整阈值:" + "\n")

    from sklearn import  metrics

    unknown = np.array(
        train_x
        , dtype=np.float32)
    predicted = model.predict(unknown)
    def testTC(tc,time):
        y_true = train_y;
        y_pred = []

        sum=0;

        true1num = 0
        false1num = 0
        true0num = 0
        false0num =0
        ytrue1num=0
        for i in range(len(predicted)):
            if predicted[i][0] > tc:
                y_pred.append(1)
                if y_true[i]==1:
                    true1num=true1num+1
                    ytrue1num=ytrue1num+1
                else :
                    false1num=false1num+1
            else:
                y_pred.append(0)
                if y_true[i]==1:
                    false0num=false0num+1
                    ytrue1num = ytrue1num + 1
                else :
                    true0num=true0num+1
        myscore=1
        if ytrue1num != 0:
            rate=   ytrue1num/(len(y_true)-ytrue1num)
            myscore= (true1num/(ytrue1num))-    (false1num/(ytrue1num))

        # ac_score=metrics.accuracy_score(y_true, y_pred)
        # prec_score = metrics.precision_score(y_true, y_pred,average='macro')#样本数量不均衡，注重数量少的，使用宏平均
        # rc_score=  metrics. recall_score(y_true, y_pred, average='macro')
        # f_score=metrics.f1_score(y_true, y_pred, average='macro')



        print("第:" + str(time) + "次,得分：" + str(myscore) + " ")
        # logFile.writelines("第:"+str(time)+"次,得分："+str((false1num/(ytrue1num))) + "  \n")
        # logFile.writelines("1:" + str((true1num/(ytrue1num))) + ",2：" + str((false1num/(ytrue1num))) + "\n")
        # logFile.writelines("(true1num/(true1num+false0num))= "+str((true1num/(true1num+false0num)))+"\n")
        # logFile.writelines("rate *(false1num/(true1num+false0num))= "+str(rate *(false1num/(true1num+false0num)))+"\n")


        # logFile.writelines("ac_score:     " + str(ac_score)  +  "    ")
        # logFile.writelines("prec_score:   " + str(prec_score) + "    ")
        # logFile.writelines("rc_score:     " + str(rc_score) +   "    \n")
        return myscore

    startTC=0
    maxScore=0;
    genTc=startTC
    step=0.02
    for i in range( 1,int((1-startTC)/step)   ):
        tpScore= testTC(startTC+i*step, i)
        if tpScore>maxScore:
            maxScore=tpScore
            genTc=startTC+i*step

    logFile.writelines("调整完成，最终阈值:" + str(genTc) + ",得分：" + str(maxScore) + "\n")

    TC=genTc
    # 检验阶段
    def runTestData(name, testDataFrame, clfNum):
        print("开始测试" + name)
        # print("head5")
        # print(testDataFrame.head(5))
        input_x=testDataFrame.iloc[:, 0:3].values.astype('float32')
        unknown = np.array(
            input_x
            , dtype=np.float32)
        predicted = model.predict(unknown)
        y_true =  testDataFrame.iloc[:,3 :4].values.astype('float32')
        y_pred = []


        true1num = 0
        false1num = 0
        true0num = 0
        false0num = 0
        ytrue1num = 0
        for i in range(len(predicted)):
            y_pred.append(predicted[i][0])
            if predicted[i][0] > clfNum:
                # y_pred.append(1)
                if y_true[i] == 1:
                    true1num = true1num + 1
                    ytrue1num = ytrue1num + 1
                else:
                    false1num = false1num + 1
            else:
                # y_pred.append(0)
                if y_true[i] == 1:
                    false0num = false0num + 1
                    ytrue1num = ytrue1num + 1
                else:
                    true0num = true0num + 1

        logFile.writelines("异常数量" + str(ytrue1num) + " \n")
        if ytrue1num!=0:
            logFile.writelines("异常检出率" + str( true1num/ytrue1num ) + " \n")
        if (false1num+true1num)!=0:
            logFile.writelines("正常误报率" + str(false1num/(false1num+true1num) ) + " \n")
        #
        # for values in predicted:

            # if values[0] > clfNum:
            #     y_pred.append(1)
            # else:
            #     y_pred.append(0)

        plt.subplot("211")
        pyplot.plot(y_true , label='label')
        pyplot.legend()
        # pyplot.show()
        plt.subplot("212")
        pyplot.plot(y_pred , label='predict')
        pyplot.legend()
        pyplot.savefig(thisTrainSaveDir + name+"预测和实际.png", dpi=100)
        pyplot.clf()

        pyplot.plot( testDataFrame.iloc[:,0 :1].values.astype('float32'), label='std')
        pyplot.plot(testDataFrame.iloc[:, 1:2].values.astype('float32'), label='mean')
        pyplot.plot(testDataFrame.iloc[:, 2:3].values.astype('float32'), label='fws')
        pyplot.legend()
        pyplot.savefig(thisTrainSaveDir + name + "数据.png", dpi=100)
        pyplot.clf()



        return 0

    runTestData("历史数据", TZ_df, TC)
    runTestData("新的数据", TZ_test, TC)

    logFile.close()
    return 0


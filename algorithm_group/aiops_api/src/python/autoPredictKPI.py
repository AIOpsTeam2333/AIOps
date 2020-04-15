import logging
import pytz
import time



from apscheduler.schedulers.background import BackgroundScheduler
from apscheduler.schedulers.blocking import BlockingScheduler
import mysql.connector

import KPI_modelTrain
import KPI_predict

import configparser
cf = configparser.ConfigParser()
cf.read("KPIautoPredictConfig.ini")
secs = cf.sections()
# print(secs)
print(cf.get("modelconfig", "minTrainNum"))



HOST =cf.get("database", "HOST")
USERNAME = cf.get("database", "USERNAME")
PASSWORD = str(cf.get("database", "PASSWORD"))


DB_NAME=cf.get("database", "DB_NAME")
PredictKPIList=cf.get("predictconfig", "PredictKPIList")
modelDir=cf.get("modelconfig", "modelDir")

tableIgnoreList=cf.get("predictconfig", "tableIgnoreList").split(",")

modelConfig = {
    "minTrainNum":int(cf.get("modelconfig", "minTrainNum")),
    "minPredictNum":int(cf.get("modelconfig", "minPredictNum")),
    "saveDirs": cf.get("modelconfig", "saveDirs"),
    "uu": int(cf.get("modelconfig", "uu")),  # 起始位置
    "hRate":float(cf.get("modelconfig", "hRate") )  ,  # 历史数据集大小
    "nRate": float(cf.get("modelconfig", "nRate") ),  # 实时数据集大小（用于检验

    "traintestRate": float(cf.get("modelconfig", "traintestRate") ),  # 划分train test比例
    "max_epochs": int(cf.get("modelconfig", "max_epochs")),  # 训练次数
    "b_size": int(cf.get("modelconfig", "b_size")),  # batch_size每批数量

    "STA_windowSize_left": int(cf.get("modelconfig", "STA_windowSize_left")),  # 特征提取窗口大小
    "STA_windowSize_right": int(cf.get("modelconfig", "STA_windowSize_right")),
    "STA_fws": float(cf.get("modelconfig", "STA_fws") ),  # 特征提取分位数

}


def read_database(db_name: str, table_name: str):
    conn = mysql.connector.connect(host=HOST,
                                   user=USERNAME,
                                   password=PASSWORD,
                                   database=db_name)
    # 数据库 handle
    cursor = conn.cursor()

    cursor.execute('SELECT * FROM {0};'.format(table_name))
    values = cursor.fetchall()

    logging.info('read from `%s`.`%s`, size of result set: %d' % (db_name, table_name, len(values)))
    # print(values)

    cursor.close()
    conn.close()

    return values

def write_database(db_name: str, table_name: str, records):
    conn = mysql.connector.connect(host=HOST,
                                   user=USERNAME,
                                   password=PASSWORD,
                                   database=db_name)
    cursor = conn.cursor()

    # 将 records 中的元组全部插入表中
    cursor.executemany('INSERT INTO {0} (first_name, last_name, last_update) VALUES (%s, %s, now());'.format(table_name),
                       records)
    if cursor.rowcount != len(records):
        logging.error("FAILED to insert all records")
    conn.commit()

    logging.info('write to `%s`.`%s`, number of records: %d' % (db_name, table_name, len(records)))

    cursor.close()
    conn.close()

def getAllKpiName():
    conn = mysql.connector.connect(host=HOST,
                                   user=USERNAME,
                                   password=PASSWORD,
                                   database=DB_NAME)
    # 数据库 handle
    cursor = conn.cursor()

    cursor.execute('SHOW TABLES' )
    values = cursor.fetchall()
    kpinameList=[]
    for tableName in values:
        tableName=tableName[0]
        # print(tableName)
        if tableName.startswith("kpi_"):
            kpinameList.append(tableName)


    # print(len(values))
    # print(len(kpinameList))
    # print( kpinameList )

    cursor.close()
    conn.close()
    return kpinameList
def generateDataFrame(datalist):

    # print(datalist)
    import pandas as pd
    df = pd.DataFrame(datalist)
    print(df)
    if len(df)==0:
        return None

    df.columns = ['id', 'value', 'timestamp', 'label'] #获得历史数据时把predict改成label
    # print (type(datalist[0][2]))
    return df

def getLatestOnePieceData(kpiName,db_name):
    # defaultPredict=str(defaultPredict)
    SQLstr="select id,value,time,predict FROM "+kpiName+" WHERE  is  null ORDER BY time   limit 1 "
    # print (SQLstr)
    conn = mysql.connector.connect(host=HOST,
                                   user=USERNAME,
                                   password=PASSWORD,
                                   database=db_name)
    # 数据库 handle
    cursor = conn.cursor()

    cursor.execute(SQLstr)
    values = cursor.fetchall()
    # //logging.info('read from `%s`.`%s`, size of result set: %d' % (db_name, kpiName, len(values)))
    # print(values)
    if len(values) == 0:
        return None

    targetID=values[0][0]
    return  targetID

def getReleatedData(kpiName,db_name,dataid):

    # print (SQLstr)
    conn = mysql.connector.connect(host=HOST,
                                   user=USERNAME,
                                   password=PASSWORD,
                                   database=db_name)
    # 数据库 handle
    cursor = conn.cursor()

    SQLstr = "select id,value,time,predict FROM " + kpiName + " WHERE id <= " + str(
        dataid) + " ORDER BY id  DESC limit " + str(modelConfig["STA_windowSize_left"])
    cursor.execute(SQLstr)
    values = cursor.fetchall()
    if len(values) <= modelConfig["minPredictNum"]:
        return None
    cursor.close()
    conn.close()
    return generateDataFrame(values)["value"].tolist()
def setPredict(kpiName,db_name,dataid,predict):

    # print (SQLstr)
    conn = mysql.connector.connect(host=HOST,
                                   user=USERNAME,
                                   password=PASSWORD,
                                   database=db_name)
    # 数据库 handle
    cursor = conn.cursor()

    SQLstr = "UPDATE  " + kpiName + "SET  predict = "+  str(predict)+  " WHERE id  = " + str( dataid)
    cursor.execute(SQLstr)
    # values = cursor.fetchall()
    print("成功更新")
    cursor.close()
    conn.close()
    return


def getAllHistoryData(kpiName,db_name):
    defaultPredict=-1
    defaultPredict=str(defaultPredict)
    SQLstr="select id,value,time,predict  FROM "+kpiName+" WHERE predict is not null ORDER BY time   DESC "
    # print (SQLstr)
    conn = mysql.connector.connect(host=HOST,
                                   user=USERNAME,
                                   password=PASSWORD,
                                   database=db_name)
    # 数据库 handle
    cursor = conn.cursor()
    cursor.execute(SQLstr)
    values = cursor.fetchall()
    print(values)
    if len(values)<=modelConfig["minTrainNum"]:
        return None
    cursor.close()
    conn.close()

    return values


def string2timestamp(strValue):
    import  datetime
    try:
        d = datetime.datetime.strptime(strValue, "%Y-%m-%d %H:%M:%S.%f")
        t = d.timetuple()
        timeStamp = int(time.mktime(t))
        timeStamp = float(str(timeStamp) + str("%06d" % d.microsecond)) / 1000000
        print
        timeStamp
        return timeStamp
    except ValueError as e:

        d = datetime.datetime.strptime("2020-12-22 22:22:22", "%Y-%m-%d %H:%M:%S")
        t = d.timetuple()
        timeStamp = int(time.mktime(t))
        timeStamp = float(str(timeStamp) + str("%06d" % d.microsecond)) / 1000000
        print
        timeStamp
    return timeStamp


def every_ten_seconds():

    time.sleep(2)
    print("当前时间： ",str ( time.strftime('%Y.%m.%d %H:%M:%S ', time.localtime(time.time())) ) )
    kpiNameList = getAllKpiName()
    for kpiName in kpiNameList:
        if kpiName  in tableIgnoreList:
            continue
        targetID=getLatestOnePieceData(kpiName, "aiops")
        if targetID==None:
            print("数据过少，跳过 " + str(kpiName))
            continue
        TfDataFrame =getReleatedData(kpiName, "aiops", targetID)
        if TfDataFrame!=None:
            predict= KPI_predict.kpi_predict(kpiName, TfDataFrame, modelConfig)
            setPredict(kpiName, "aiops", targetID, predict);
            print(str(kpiName)+"预测为："+str(predict))

    print("=======================================" )
def ever_week():
    time.sleep(2)
    print("生成每周模型...")
    kpiNameList = getAllKpiName()
    for kpiName in kpiNameList:
        if kpiName in tableIgnoreList:
            continue
        print("正在处理." + str(kpiName))
        historyData   =getAllHistoryData(kpiName, "aiops")
        print(historyData)
        if historyData  !=None:
            KPI_modelTrain.kpi_train_model(kpiName,    generateDataFrame(historyData)  ,modelConfig)


if __name__ == '__main__':
    # KPI_modelTrain.kpi_train_model("kpi_all_p95_hour", generateDataFrame(getAllHistoryData("kpi_all_p95_hour", "aiops")), modelConfig)
    #print("predict：" + str(KPI_predict.kpi_predict("kpi_all_p95_hour", getLatestOnePieceData("kpi_all_p95_hour", "aiops"), modelConfig)))
    # print ( getAllKpiName())
    ever_week()

    # 如果只有定时任务，使用 BlockingScheduler
    # scheduler = BlockingScheduler()
    # 如果除了定时任务之外还有其他工作，使用 BackgroundScheduler
    scheduler = BackgroundScheduler()

    scheduler.configure(timezone=pytz.timezone('Asia/Shanghai'))

    scheduler.add_job(every_ten_seconds, 'cron', second='*/10', id='every_ten_seconds')
    scheduler.add_job(ever_week, 'cron', day='*/7', id='every_week')

    scheduler.start()

    try:
        # 模拟程序运行
        while True:
            time.sleep(2)
    except (KeyboardInterrupt, SystemExit):
        scheduler.shutdown()

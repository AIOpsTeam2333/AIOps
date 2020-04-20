# 预处理组：

编译环境需安装lombok插件



## 1.结构介绍

### **resource**包

resoureces/datainfo.properties存储配置信息

resources/test包下存储相关测试信息

resources/sechema包下存储相应sql文件



### com.aiops.processdata包

config 存Spring配置信息，数据源/扫描包

dao 存数据持久化接口及实现

service 存业务层接口及实现

entity 存 json文件对应数据结构bean

po 存 数据库转化后bean对象

utils 存工具类

run 下 Runner为启动类

## 2.程序启动

运行run包下Runner类启动 存储数据



## 3.测试

test包

现有接口均已测试
package com.aiops.processdata.service;

import com.aiops.processdata.entity.service.Service_Data;
import com.aiops.processdata.po.service.ServicePO;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/7 22:03
 */
public interface Service {
    /**
     * 将文件数据全部插入
     *
     * @param service_data
     * @return
     */
    List<ServicePO> save(Service_Data service_data);

    /**
     * 根据service_info中id返回实际数据库序号
     * 不存在返回-1
     *
     * @param pre_id
     * @return 生成的node_id
     */
    Integer findById(String pre_id);

    /**
     * 根据服务名返回数据库中实际服务id
     * 不存在返回-1
     *
     * @param name
     * @return node_id
     */
    Integer findByName(String name);
}

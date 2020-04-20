package com.aiops.processdata.dao;

import com.aiops.processdata.entity.service.Service_Info;
import com.aiops.processdata.po.service.ServicePO;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/7 18:16
 */
public interface ServiceRepository {
    /**
     * 插入service数据
     *
     * @param service_info
     * @return 插入后的数据
     */
    ServicePO insertService(Service_Info service_info);

    /**
     * 根据service_info中id返回Service对象
     *
     * @param pre_id
     * @return 生成的node_id的对象
     */
    ServicePO findById(String pre_id);

    /**
     * 根据 span中name返回Service对象
     *
     * @param name
     * @return service对象
     */
    ServicePO findByName(String name);
}

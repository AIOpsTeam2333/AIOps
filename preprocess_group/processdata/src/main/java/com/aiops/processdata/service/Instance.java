package com.aiops.processdata.service;

import com.aiops.processdata.entity.Instance.ServiceInstance_Data;
import com.aiops.processdata.po.instance.InstancePO;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/10 20:37
 */
public interface Instance {
    /**
     * 存储所有的instance服务
     *
     * @param serviceInstance_data
     * @return 返回的instance服务
     */
    List<InstancePO> save(ServiceInstance_Data serviceInstance_data);
}

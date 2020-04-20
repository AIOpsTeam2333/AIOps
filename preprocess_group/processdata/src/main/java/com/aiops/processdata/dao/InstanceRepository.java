package com.aiops.processdata.dao;

import com.aiops.processdata.entity.Instance.ServiceInstance_Info;
import com.aiops.processdata.po.instance.InstancePO;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/10 17:45
 */
public interface InstanceRepository {
    /**
     * 插入Instance数据
     *
     * @param serviceInstance_info
     * @return
     */
    InstancePO insertInstance(ServiceInstance_Info serviceInstance_info);

}

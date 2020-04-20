package com.aiops.processdata.dao;

import com.aiops.processdata.entity.endpoint.Endpoint_Info;
import com.aiops.processdata.po.endpoint.EndpointPO;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/9 20:57
 */
public interface EndpointRepository {
    /**
     * 插入endpoint数据
     *
     * @param endpoint_info
     * @return 插入后的数据对象
     */
    EndpointPO insertEndpoint(Endpoint_Info endpoint_info);

    /**
     * 根据endpoint_info中的id返回Endpoint对象
     *
     * @param pre_id
     * @return 生成的endpointId数据库对象
     */
    EndpointPO findById(String pre_id);

    /**
     * 根据span中endpointname返回Endpoint对象
     *
     * @param name
     * @return 生成的endpoint对象
     */
    EndpointPO findByName(String name);
}

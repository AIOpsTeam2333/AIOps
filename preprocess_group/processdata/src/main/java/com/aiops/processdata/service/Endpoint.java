package com.aiops.processdata.service;

import com.aiops.processdata.entity.endpoint.Endpoint_Data;
import com.aiops.processdata.po.endpoint.EndpointPO;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/9 23:30
 */
public interface Endpoint {
    /**
     * 将文件数据全部插入
     *
     * @param endpoint_data
     * @return PO列表
     */
    List<EndpointPO> save(Endpoint_Data endpoint_data);

    /**
     * 根据endpoint_info中id返回实际数据库序号
     *
     * @param pre_id
     * @return 生成的service_endpoint_id;
     */
    Integer findById(String pre_id);

    /**
     * 根据span 中endpointName信息返回实际数据库对应的id
     *
     * @param name
     * @return 生成的service_endpoint_id
     */
    Integer findByName(String name);
}

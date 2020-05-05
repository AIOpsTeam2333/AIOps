package com.aiops.api.dao;

import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.EndpointTopologyNode;
import com.aiops.api.service.topology.endpoint.dto.ContextIdInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 拓扑信息查询, 包括endpoint拓扑查询和Service拓扑查询
 * @author Shuaiyu Yao
 * @create 2020-05-05 15:38
 */
@Repository
public class TopologyDao {


    /**
     * 获取endpoint端点上下文信息
     *
     * @param duration
     * @param endpointId
     * @return
     */
    public List<ContextIdInfo> getContextIdInfo(
            Duration duration,
            Integer endpointId
    ) {
        return null;
    }

    public List<EndpointTopologyNode> selectNodesBySpanId(List<Integer> ids, Duration duration) {
        return null;
    }

    public List<EndpointTopologyNode> selectNodesByParentSpanId(List<Integer> ids, Duration duration) {
        return null;
    }

}

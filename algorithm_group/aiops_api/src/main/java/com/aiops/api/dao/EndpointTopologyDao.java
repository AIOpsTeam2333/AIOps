package com.aiops.api.dao;

import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.EndpointTopologyNode;
import com.aiops.api.mapper.EndpointTopologyMapper;
import com.aiops.api.service.topology.endpoint.dto.ContextIdInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-24 15:12
 */
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EndpointTopologyDao {

    public final EndpointTopologyMapper endpointTopologyMapper;

    /**
     * 获取上下文
     *
     * @param duration
     * @param endpointId
     * @return
     */
    public List<ContextIdInfo> getContextIdInfo(
            Duration duration,
            Integer endpointId
    ) {
        Date from = duration.getStart();
        Date to = duration.getEnd();
        return endpointTopologyMapper.getContextIdInfo(endpointId, from, to);
    }

    public List<EndpointTopologyNode> selectNodesBySpanId(List<Integer> ids, Duration duration) {
        return endpointTopologyMapper.getTopologyNodeBySpanId(ids, true, duration.getStart(), duration.getEnd());
    }

    public List<EndpointTopologyNode> selectNodesByParentSpanId(List<Integer> ids, Duration duration) {
        return endpointTopologyMapper.getTopologyNodeBySpanId(ids, false, duration.getStart(), duration.getEnd());
    }
}

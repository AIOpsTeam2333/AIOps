package com.aiops.api.dao;

import com.aiops.api.common.enums.ScopeType;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.TopologyCall;
import com.aiops.api.mapper.TopologyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 拓扑信息查询, 包括endpoint拓扑查询和Service拓扑查询
 *
 * @author Shuaiyu Yao
 * @create 2020-05-05 15:38
 */
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TopologyDao {

    private final TopologyMapper topologyMapper;

    /**
     * 查询拓扑关系
     *
     * @param duration  duration
     * @param id        需要查询的关键id
     * @param scopeType endpoint和service
     * @return
     */
    public List<TopologyCall> queryTopologyCalls(
            Duration duration,
            Integer id,
            ScopeType scopeType
    ) {
        return topologyMapper.queryTopologyCall(duration.getStart(), duration.getEnd(), id, scopeType.databaseName());
    }
}

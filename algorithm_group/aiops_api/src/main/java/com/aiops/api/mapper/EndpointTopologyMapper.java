package com.aiops.api.mapper;

import com.aiops.api.config.MyMapper;
import com.aiops.api.entity.vo.response.EndpointTopologyNode;
import com.aiops.api.service.topology.endpoint.dto.ContextIdInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-24 15:13
 **/
@Repository
@Mapper
public interface EndpointTopologyMapper extends MyMapper<EndpointTopologyNode> {

    List<ContextIdInfo> getContextIdInfo(
            @Param("endpointId") Integer endpointId,
            @Param("from") Date from,
            @Param("to") Date to
    );

    /**
     * 根据id查找Endpoint拓扑节点
     *
     * @param ids         id
     * @param isCurrentId true表示用span_id查询, false表示用parent_span_id
     * @return
     */
    List<EndpointTopologyNode> getTopologyNodeBySpanId(
            @Param("ids") List<Integer> ids,
            @Param("isCurrentId") boolean isCurrentId,
            @Param("from") Date from,
            @Param("to") Date to
    );
}

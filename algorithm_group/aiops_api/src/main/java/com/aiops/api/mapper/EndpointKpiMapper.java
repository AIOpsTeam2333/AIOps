package com.aiops.api.mapper;

import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import com.aiops.api.entity.vo.response.SimpleOrderNode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-11 15:58
 */
@Repository
public interface EndpointKpiMapper {

    List<CrossAxisGraphPoint> selectCrossAxisKpi(
            @Param("endpointId") Integer endpointId,
            @Param("kpiName") String kpiName,
            @Param("step") String step,
            @Param("from") Date from,
            @Param("to") Date to
    );

    List<SimpleOrderNode> selectServiceSlowEndpoint(
            @Param("serviceId") Integer serviceId,
            @Param("step") String step,
            @Param("from") Date from,
            @Param("to") Date to
    );

    List<SimpleOrderNode> selectGlobalSlowEndpoint(
            @Param("step") String step,
            @Param("from") Date from,
            @Param("to") Date to
    );
}

package com.aiops.api.mapper;

import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import com.aiops.api.entity.vo.response.MemoryPoint;
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
public interface InstanceKpiMapper {

    List<CrossAxisGraphPoint> queryCrossAxisKpi(
            @Param("instanceId") Integer instanceId,
            @Param("kpiName") String kpiName,
            @Param("step") String step,
            @Param("from") Date from,
            @Param("to") Date to
    );

    List<SimpleOrderNode> queryServiceInstanceThroughputDesc(
            @Param("serviceId") Integer serviceId,
            @Param("step") String step,
            @Param("from") Date from,
            @Param("to") Date to
    );

    List<MemoryPoint> queryMemoryPoints(
            @Param("instanceId") Integer instanceId,
            @Param("occupiedHeap") String occupiedHeap,
            @Param("maxHeap") String maxHeap,
            @Param("step") String step,
            @Param("from") Date from,
            @Param("to") Date to
    );
}

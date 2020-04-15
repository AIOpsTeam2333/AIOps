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
public interface ServiceKpiMapper {

    List<CrossAxisGraphPoint> queryCrossAxisKpi(
            @Param("serviceId") Integer serviceId,
            @Param("kpiName") String kpiName,
            @Param("step") String step,
            @Param("from") Date from,
            @Param("to") Date to
    );

    List<SimpleOrderNode> queryKpiByOrder(
            @Param("kpiName") String kpiName,
            @Param("isDesc") Boolean isDesc,
            @Param("step") String step,
            @Param("from") Date from,
            @Param("to") Date to
    );
}

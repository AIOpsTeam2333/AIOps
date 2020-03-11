package com.aiops.api.mapper;

import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
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

    List<CrossAxisGraphPoint> selectCrossAxisKpi(
            @Param("instanceId") Integer instanceId,
            @Param("kpiName") String kpiName,
            @Param("step") String step,
            @Param("from") Date from,
            @Param("to") Date to
    );
}

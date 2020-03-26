package com.aiops.api.mapper;

import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import com.aiops.api.entity.vo.response.HeatmapPoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-10 13:28
 */
@Repository
public interface GlobalKpiMapper {

    List<CrossAxisGraphPoint> selectCrossAxisKpi(
            @Param("kpiName") String kpiName,
            @Param("step") String step,
            @Param("from") Date from,
            @Param("to") Date to
    );


    Integer selectTheMostHeatmapStep(
            @Param("step") String step,
            @Param("from") Date from,
            @Param("to") Date to
    );


    List<HeatmapPoint> selectHeatmap(
            @Param("step") String step,
            @Param("heatmapStep") Integer heatmapStep,
            @Param("from") Date from,
            @Param("to") Date to
    );

}

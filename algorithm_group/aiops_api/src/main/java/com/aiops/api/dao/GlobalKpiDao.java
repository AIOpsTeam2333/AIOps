package com.aiops.api.dao;

import com.aiops.api.common.type.KpiType;
import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import com.aiops.api.entity.vo.response.HeatmapPoint;
import com.aiops.api.mapper.GlobalKpiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-11 13:49
 */
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalKpiDao {

    private final GlobalKpiMapper globalKpiMapper;


    public List<HeatmapPoint> getHeatmap(String step,
                                         Integer heatmapStep,
                                         Date from,
                                         Date to
    ) {
        return globalKpiMapper.selectHeatmap(step, heatmapStep, from, to);
    }

    public Integer getTheMostHeatmapStep(
            String step,
            Date from,
            Date to
    ) {
        return globalKpiMapper.selectTheMostHeatmapStep(step, from, to);
    }

    public List<CrossAxisGraphPoint> getP50(
            String step,
            Date from,
            Date to
    ) {
        return globalKpiMapper.selectCrossAxisKpi(KpiType.P50.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP75(
            String step,
            Date from,
            Date to
    ) {
        return globalKpiMapper.selectCrossAxisKpi(KpiType.P75.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP90(
            String step,
            Date from,
            Date to
    ) {
        return globalKpiMapper.selectCrossAxisKpi(KpiType.P90.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP95(
            String step,
            Date from,
            Date to
    ) {
        return globalKpiMapper.selectCrossAxisKpi(KpiType.P95.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP99(
            String step,
            Date from,
            Date to
    ) {
        return globalKpiMapper.selectCrossAxisKpi(KpiType.P99.databaseName(), step, from, to);
    }
}

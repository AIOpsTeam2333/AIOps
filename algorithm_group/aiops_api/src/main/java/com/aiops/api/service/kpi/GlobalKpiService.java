package com.aiops.api.service.kpi;

import com.aiops.api.dao.GlobalKpiDao;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.HeatmapGraph;
import com.aiops.api.entity.vo.response.HeatmapPoint;
import com.aiops.api.entity.vo.response.PercentileGraph;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-11 13:59
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalKpiService {

    private final GlobalKpiDao globalKpiDao;

    public PercentileGraph getGlobalPercentileGraph(Duration duration) {
        PercentileGraph result = new PercentileGraph();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        String step = duration.getStep().getName();
        result.setP50(globalKpiDao.getP50(step, from, to));
        result.setP75(globalKpiDao.getP75(step, from, to));
        result.setP90(globalKpiDao.getP90(step, from, to));
        result.setP95(globalKpiDao.getP95(step, from, to));
        result.setP99(globalKpiDao.getP99(step, from, to));
        return result;
    }

    public HeatmapGraph getGlobalHeatmapGraph(Duration duration) {
        HeatmapGraph result = new HeatmapGraph();
        Date from = duration.getStart();
        Date to = duration.getEnd();
        String step = duration.getStep().getName();
        Integer heatmapStep = globalKpiDao.getTheMostHeatmapStep(step, from, to);
        if (heatmapStep == null) {
            heatmapStep = 0;
        }
        List<HeatmapPoint> heatmapPoints = globalKpiDao.getHeatmap(step, heatmapStep, from, to);
        result.setNodes(heatmapPoints);
        result.setResponseTimeStep(heatmapStep);
        return result;
    }
}

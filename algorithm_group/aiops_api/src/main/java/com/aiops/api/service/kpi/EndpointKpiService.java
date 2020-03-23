package com.aiops.api.service.kpi;

import com.aiops.api.dao.EndpointKpiDao;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import com.aiops.api.entity.vo.response.PercentileGraph;
import com.aiops.api.entity.vo.response.SimpleOrderNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-11 16:08
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EndpointKpiService {

    private final EndpointKpiDao endpointKpiDao;


    public List<CrossAxisGraphPoint> getResponseTime(Duration duration, Integer endpointId) {
        Date from = duration.getStart();
        Date to = duration.getEnd();
        String step = duration.getStep().getName();
        return endpointKpiDao.getResponseTime(endpointId, step, from, to);
    }

    public List<CrossAxisGraphPoint> getThroughput(Duration duration, Integer endpointId) {
        Date from = duration.getStart();
        Date to = duration.getEnd();
        String step = duration.getStep().getName();
        return endpointKpiDao.getThroughput(endpointId, step, from, to);
    }

    public List<CrossAxisGraphPoint> getSla(Duration duration, Integer endpointId) {
        Date from = duration.getStart();
        Date to = duration.getEnd();
        String step = duration.getStep().getName();
        return endpointKpiDao.getSla(endpointId, step, from, to);
    }

    public PercentileGraph getPercentileGraph(Duration duration, Integer endpointId) {
        Date from = duration.getStart();
        Date to = duration.getEnd();
        String step = duration.getStep().getName();
        PercentileGraph result = new PercentileGraph();
        result.setP50(endpointKpiDao.getP50(endpointId, step, from, to));
        result.setP75(endpointKpiDao.getP75(endpointId, step, from, to));
        result.setP90(endpointKpiDao.getP90(endpointId, step, from, to));
        result.setP95(endpointKpiDao.getP95(endpointId, step, from, to));
        result.setP99(endpointKpiDao.getP99(endpointId, step, from, to));
        return result;
    }

    public List<SimpleOrderNode> getServiceSlowEndpoint(Duration duration, Integer serviceId) {
        Date from = duration.getStart();
        Date to = duration.getEnd();
        String step = duration.getStep().getName();
        return endpointKpiDao.selectServiceSlowEndpoint(serviceId, step, from, to);
    }

    public List<SimpleOrderNode> getGlobalSlowEndpoint(Duration duration) {
        Date from = duration.getStart();
        Date to = duration.getEnd();
        String step = duration.getStep().getName();
        return endpointKpiDao.selectGlobalSlowEndpoint(step, from, to);
    }
}

package com.aiops.api.service.kpi;

import com.aiops.api.dao.ServiceKpiDao;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.request.Paging;
import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import com.aiops.api.entity.vo.response.PercentileGraph;
import com.aiops.api.entity.vo.response.SimpleOrderNode;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-11 16:08
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceKpiService {

    private final ServiceKpiDao serviceKpiDao;
    @Qualifier("defaultPaging")
    private final Paging defaultPaging;

    public List<CrossAxisGraphPoint> getApdexScore(Duration duration, Integer serviceId) {
        Date from = duration.getStart();
        Date to = duration.getEnd();
        String step = duration.getStep().getName();
        return serviceKpiDao.getApdexScore(serviceId, step, from, to);
    }

    public List<CrossAxisGraphPoint> getResponseTime(Duration duration, Integer serviceId) {
        Date from = duration.getStart();
        Date to = duration.getEnd();
        String step = duration.getStep().getName();
        return serviceKpiDao.getResponseTime(serviceId, step, from, to);
    }

    public List<CrossAxisGraphPoint> getThroughput(Duration duration, Integer serviceId) {
        Date from = duration.getStart();
        Date to = duration.getEnd();
        String step = duration.getStep().getName();
        return serviceKpiDao.getThroughput(serviceId, step, from, to);
    }

    public List<CrossAxisGraphPoint> getSla(Duration duration, Integer serviceId) {
        Date from = duration.getStart();
        Date to = duration.getEnd();
        String step = duration.getStep().getName();
        return serviceKpiDao.getSla(serviceId, step, from, to);
    }

    public PercentileGraph getPercentileGraph(Duration duration, Integer serviceId) {
        Date from = duration.getStart();
        Date to = duration.getEnd();
        String step = duration.getStep().getName();
        PercentileGraph result = new PercentileGraph();
        result.setP50(serviceKpiDao.getP50(serviceId, step, from, to));
        result.setP75(serviceKpiDao.getP75(serviceId, step, from, to));
        result.setP90(serviceKpiDao.getP90(serviceId, step, from, to));
        result.setP95(serviceKpiDao.getP95(serviceId, step, from, to));
        result.setP99(serviceKpiDao.getP99(serviceId, step, from, to));
        return result;
    }

    public List<SimpleOrderNode> getThroughputByDescTop(Duration duration) {
        Date from = duration.getStart();
        Date to = duration.getEnd();
        String step = duration.getStepName();
        PageHelper.startPage(defaultPaging);
        return serviceKpiDao.queryThroughputByOrder(true, step, from, to);
    }
}

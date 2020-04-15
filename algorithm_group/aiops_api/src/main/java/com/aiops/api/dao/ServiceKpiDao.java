package com.aiops.api.dao;

import com.aiops.api.common.enums.KpiType;
import com.aiops.api.entity.vo.request.Paging;
import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import com.aiops.api.entity.vo.response.SimpleOrderNode;
import com.aiops.api.mapper.ServiceKpiMapper;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-11 16:00
 */
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceKpiDao {

    private final ServiceKpiMapper serviceKpiMapper;

    public List<CrossAxisGraphPoint> getP50(
            Integer serviceId,
            String step,
            Date from,
            Date to
    ) {
        return serviceKpiMapper.queryCrossAxisKpi(serviceId, KpiType.P50.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP75(
            Integer serviceId,
            String step,
            Date from,
            Date to
    ) {
        return serviceKpiMapper.queryCrossAxisKpi(serviceId, KpiType.P75.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP90(
            Integer serviceId,
            String step,
            Date from,
            Date to
    ) {
        return serviceKpiMapper.queryCrossAxisKpi(serviceId, KpiType.P90.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP95(
            Integer serviceId,
            String step,
            Date from,
            Date to
    ) {
        return serviceKpiMapper.queryCrossAxisKpi(serviceId, KpiType.P95.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP99(
            Integer serviceId,
            String step,
            Date from,
            Date to
    ) {
        return serviceKpiMapper.queryCrossAxisKpi(serviceId, KpiType.P99.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getApdexScore(
            Integer serviceId,
            String step,
            Date from,
            Date to
    ) {
        return serviceKpiMapper.queryCrossAxisKpi(serviceId, KpiType.APDEX_SCORE.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getResponseTime(
            Integer serviceId,
            String step,
            Date from,
            Date to
    ) {
        return serviceKpiMapper.queryCrossAxisKpi(serviceId, KpiType.RESPONSE_TIME.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getThroughput(
            Integer serviceId,
            String step,
            Date from,
            Date to
    ) {
        return serviceKpiMapper.queryCrossAxisKpi(serviceId, KpiType.THROUGHPUT.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getSla(
            Integer serviceId,
            String step,
            Date from,
            Date to
    ) {
        return serviceKpiMapper.queryCrossAxisKpi(serviceId, KpiType.SLA.databaseName(), step, from, to);
    }

    public List<SimpleOrderNode> queryThroughputByOrder(
            Boolean isDesc,
            String step,
            Date from,
            Date to
    ) {
        return serviceKpiMapper.queryKpiByOrder(KpiType.THROUGHPUT.databaseName(), isDesc, step, from, to);
    }
}

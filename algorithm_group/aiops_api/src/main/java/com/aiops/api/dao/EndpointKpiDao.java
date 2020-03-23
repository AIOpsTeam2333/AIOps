package com.aiops.api.dao;

import com.aiops.api.common.type.KpiType;
import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import com.aiops.api.entity.vo.response.SimpleOrderNode;
import com.aiops.api.mapper.EndpointKpiMapper;
import com.aiops.api.mapper.ServiceKpiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-11 16:00
 */
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EndpointKpiDao {

    private final EndpointKpiMapper endpointKpiMapper;

    public List<CrossAxisGraphPoint> getP50(
            Integer endpointId,
            String step,
            Date from,
            Date to
    ) {
        return endpointKpiMapper.selectCrossAxisKpi(endpointId, KpiType.P50.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP75(
            Integer endpointId,
            String step,
            Date from,
            Date to
    ) {
        return endpointKpiMapper.selectCrossAxisKpi(endpointId, KpiType.P75.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP90(
            Integer endpointId,
            String step,
            Date from,
            Date to
    ) {
        return endpointKpiMapper.selectCrossAxisKpi(endpointId, KpiType.P90.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP95(
            Integer endpointId,
            String step,
            Date from,
            Date to
    ) {
        return endpointKpiMapper.selectCrossAxisKpi(endpointId, KpiType.P95.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP99(
            Integer endpointId,
            String step,
            Date from,
            Date to
    ) {
        return endpointKpiMapper.selectCrossAxisKpi(endpointId, KpiType.P99.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getResponseTime(
            Integer endpointId,
            String step,
            Date from,
            Date to
    ) {
        return endpointKpiMapper.selectCrossAxisKpi(endpointId, KpiType.RESPONSE_TIME.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getThroughput(
            Integer endpointId,
            String step,
            Date from,
            Date to
    ) {
        return endpointKpiMapper.selectCrossAxisKpi(endpointId, KpiType.THROUGHPUT.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getSla(
            Integer endpointId,
            String step,
            Date from,
            Date to
    ) {
        return endpointKpiMapper.selectCrossAxisKpi(endpointId, KpiType.SLA.databaseName(), step, from, to);
    }

    public List<SimpleOrderNode> selectServiceSlowEndpoint(
            Integer serviceId,
            String step,
            Date from,
            Date to
    ) {
        return endpointKpiMapper.selectServiceSlowEndpoint(serviceId, step, from, to);
    }

    public List<SimpleOrderNode> selectGlobalSlowEndpoint(
            String step,
            Date from,
            Date to
    ) {
        return endpointKpiMapper.selectGlobalSlowEndpoint(step, from, to);
    }
}

package com.aiops.api.dao;

import com.aiops.api.common.enums.KpiType;
import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import com.aiops.api.entity.vo.response.SimpleOrderNode;
import com.aiops.api.mapper.InstanceKpiMapper;
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
public class InstanceKpiDao {

    private final InstanceKpiMapper instanceKpiMapper;

    public List<CrossAxisGraphPoint> getP50(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.selectCrossAxisKpi(instanceId, KpiType.P50.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP75(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.selectCrossAxisKpi(instanceId, KpiType.P75.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP90(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.selectCrossAxisKpi(instanceId, KpiType.P90.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP95(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.selectCrossAxisKpi(instanceId, KpiType.P95.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP99(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.selectCrossAxisKpi(instanceId, KpiType.P99.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getResponseTime(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.selectCrossAxisKpi(instanceId, KpiType.RESPONSE_TIME.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getThroughput(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.selectCrossAxisKpi(instanceId, KpiType.THROUGHPUT.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getSla(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.selectCrossAxisKpi(instanceId, KpiType.SLA.databaseName(), step, from, to);
    }

    public List<SimpleOrderNode> selectServiceInstanceThroughput(
            Integer serviceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.serviceInstanceThroughputDesc(serviceId, step, from, to);
    }
}

package com.aiops.api.dao;

import com.aiops.api.common.enums.KpiType;
import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import com.aiops.api.entity.vo.response.MemoryPoint;
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
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.P50.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP75(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.P75.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP90(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.P90.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP95(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.P95.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> getP99(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.P99.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> queryCpu(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.CPU.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> queryResponseTime(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.RESPONSE_TIME.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> queryThroughput(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.THROUGHPUT.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> querySla(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.SLA.databaseName(), step, from, to);
    }

    public List<SimpleOrderNode> queryServiceInstanceThroughput(
            Integer serviceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryServiceInstanceThroughputDesc(serviceId, step, from, to);
    }

    public List<MemoryPoint> queryJvmHeap(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryMemoryPoints(instanceId, KpiType.HEAP.databaseName(), KpiType.MAX_HEAP.databaseName(), step, from, to);
    }

    public List<MemoryPoint> queryNonJvmHeap(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryMemoryPoints(instanceId, KpiType.NON_HEAP.databaseName(), KpiType.MAX_NON_HEAP.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> queryYoungGcTime(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.YOUNG_GC_TIME.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> queryOldGcTime(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.OLD_GC_TIME.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> queryYoungGcCount(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.YOUNG_GC_COUNT.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> queryOldGcCount(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.OLD_GC_COUNT.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> queryClrGcGen0(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.CLR_GC_GEN0.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> queryClrGcGen1(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.CLR_GC_GEN1.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> queryClrGcGen2(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.CLR_GC_GEN2.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> queryClrCpu(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.CLR_CPU.databaseName(), step, from, to);
    }

    public List<CrossAxisGraphPoint> queryClrHeap(
            Integer instanceId,
            String step,
            Date from,
            Date to
    ) {
        return instanceKpiMapper.queryCrossAxisKpi(instanceId, KpiType.CLR_HEAP.databaseName(), step, from, to);
    }
}

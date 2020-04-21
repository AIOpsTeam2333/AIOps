package com.aiops.api.controller;

import com.aiops.api.common.enums.KpiType;
import com.aiops.api.common.validation.NeedIdGroup;
import com.aiops.api.entity.vo.request.RequestBodyKpi;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.*;
import com.aiops.api.service.kpi.InstanceKpiService;
import com.aiops.api.common.kpi.KpiIndicator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 11:21
 **/
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = {"实例指标数据查询"})
@RestController
@RequestMapping("/instance")
public class InstanceKpiController {

    private final InstanceKpiService instanceKpiService;

    @ApiOperation(value = "实例指标视图")
    @PostMapping("/")
    public InstanceKpiAll instanceKpiAllData(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        KpiIndicator kpiIndicator = requestBodyKpi.getBusiness();
        Duration duration = requestBodyKpi.getDuration();
        Integer instanceId = requestBodyKpi.getId();
        InstanceKpiAll result = new InstanceKpiAll();

        if (kpiIndicator.needKpiType(KpiType.RESPONSE_TIME)) {
            result.setInstanceResponseTime(instanceKpiService.getInstanceResponseTime(duration, instanceId));
        }

        if (kpiIndicator.needKpiType(KpiType.THROUGHPUT)) {
            result.setInstanceThroughput(instanceKpiService.getInstanceThroughput(duration, instanceId));
        }

        if (kpiIndicator.needKpiType(KpiType.SLA)) {
            result.setInstanceSLA(instanceKpiService.getInstanceSla(duration, instanceId));
        }

        if (kpiIndicator.needKpiType(KpiType.CPU)) {
            result.setInstanceCPU(instanceKpiService.getInstanceCpu(duration, instanceId));
        }

        if (kpiIndicator.needHeap()) {
            result.setHeap(instanceKpiService.getJvmHeap(duration, instanceId));
        }

        if (kpiIndicator.needNonHeap()) {
            result.setNonHeap(instanceKpiService.getNonJvmHeap(duration, instanceId));
        }

        if (kpiIndicator.needGcTime()) {
            result.setGcTime(instanceKpiService.getGcTime(duration, instanceId));
        }

        if (kpiIndicator.needGcCount()) {
            result.setGcCount(instanceKpiService.getGcCount(duration, instanceId));
        }

        if (kpiIndicator.needClrGc()) {
            result.setClrGC(instanceKpiService.getClrGc(duration, instanceId));
        }

        if (kpiIndicator.needKpiType(KpiType.CLR_HEAP)) {
            result.setClrHeap(instanceKpiService.getClrHeap(duration, instanceId));
        }

        if (kpiIndicator.needKpiType(KpiType.CLR_CPU)) {
            result.setClrCPU(instanceKpiService.getClrCpu(duration, instanceId));
        }
        return result;
    }

    @ApiOperation(value = "实例指标数据ResponseTime")
    @PostMapping("/responseTime")
    public List<CrossAxisGraphPoint> instanceResponseTime(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        return instanceKpiService.getInstanceResponseTime(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }

    @ApiOperation(value = "实例指标数据Throughput")
    @PostMapping("/throughput")
    public List<CrossAxisGraphPoint> instanceThroughput(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        return instanceKpiService.getInstanceThroughput(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }

    @ApiOperation(value = "实例指标数据SLA")
    @PostMapping("/sla")
    public List<CrossAxisGraphPoint> instanceSLA(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        return instanceKpiService.getInstanceSla(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }

    @ApiOperation(value = "实例指标数据jvm cpu")
    @PostMapping("/cpu")
    public List<CrossAxisGraphPoint> cpu(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        return instanceKpiService.getInstanceCpu(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }

    @ApiOperation(value = "实例指标数据heap")
    @PostMapping("/heap")
    public List<MemoryPoint> heap(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        return instanceKpiService.getJvmHeap(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }

    @ApiOperation(value = "实例指标数据nonHeap")
    @PostMapping("/nonHeap")
    public List<MemoryPoint> nonHeap(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        return instanceKpiService.getNonJvmHeap(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }

    @ApiOperation(value = "实例指标数据gcTime")
    @PostMapping("/gcTime")
    public GcTime gcTime(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        return instanceKpiService.getGcTime(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }

    @ApiOperation(value = "实例指标数据gcCount")
    @PostMapping("/gcCount")
    public GcCount gcCount(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        return instanceKpiService.getGcCount(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }

    @ApiOperation(value = "实例指标数据clrGc")
    @PostMapping("/clrGc")
    public ClrGC clrGc(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        return instanceKpiService.getClrGc(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }

    @ApiOperation(value = "实例指标数据clrCpu")
    @PostMapping("/clrCpu")
    public List<CrossAxisGraphPoint> clrCpu(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        return instanceKpiService.getClrCpu(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }

    @ApiOperation(value = "实例指标数据clrHeap")
    @PostMapping("/clrHeap")
    public List<CrossAxisGraphPoint> clrHeap(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        return instanceKpiService.getClrHeap(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }
}

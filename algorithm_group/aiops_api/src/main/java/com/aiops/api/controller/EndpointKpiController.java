package com.aiops.api.controller;

import com.aiops.api.common.enums.KpiType;
import com.aiops.api.common.validation.NeedIdGroup;
import com.aiops.api.entity.vo.request.CommonRequestBodyKpi;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.*;
import com.aiops.api.service.kpi.EndpointKpiService;
import com.aiops.api.service.kpi.GlobalKpiService;
import com.aiops.api.service.kpi.KpiHelper;
import com.aiops.api.service.kpi.KpiIndicator;
import com.aiops.api.service.metadata.MetadataService;
import com.aiops.api.service.topology.endpoint.EndpointTopologyService;
import com.aiops.api.service.trace.TraceService;
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
 * @create 2020-03-06 11:13
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = {"端点指标数据查询"})
@RestController
@RequestMapping("/endpoint")
public class EndpointKpiController {

    private final GlobalKpiService globalKpiService;
    private final EndpointKpiService endpointKpiService;
    private final EndpointTopologyService endpointTopologyService;
    private final KpiHelper kpiHelper;
    private final MetadataService metadataService;
    private final TraceService traceService;

    @ApiOperation(value = "端点指标数据")
    @PostMapping("/")
    public EndpointKpiAll endpointKpiAllData(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBodyKpi commonRequestBodyKpi
    ) {
        KpiIndicator kpiIndicator = kpiHelper.splitKpi(commonRequestBodyKpi.getBusiness());
        Duration duration = commonRequestBodyKpi.getDuration();
        Integer endpointId = commonRequestBodyKpi.getId();
        EndpointKpiAll result = new EndpointKpiAll();

        if (kpiIndicator.needKpiType(KpiType.RESPONSE_TIME)) {
            result.setEndpointResponseTime(endpointKpiService.getResponseTime(duration, endpointId));
        }

        if (kpiIndicator.needKpiType(KpiType.THROUGHPUT)) {
            result.setEndpointThroughput(endpointKpiService.getThroughput(duration, endpointId));
        }

        if (kpiIndicator.needKpiType(KpiType.SLA)) {
            result.setEndpointSLA(endpointKpiService.getSla(duration, endpointId));
        }

        if (kpiIndicator.needPercentile()) {
            result.setEndpointPercentile(endpointKpiService.getPercentileGraph(duration, endpointId));
            result.setGlobalPercentile(globalKpiService.getGlobalPercentileGraph(duration));
        }

        result.setGlobalBrief(metadataService.getGlobalBrief());
        result.setGlobalSlow(endpointKpiService.getGlobalSlowEndpoint(duration));
        result.setEndpointTopology(endpointTopologyService.selectEndpointTopology(duration, endpointId));
        result.setEndpointTraces(traceService.queryTracesInfoByEndpointId(duration, endpointId));
        return result;
    }

    @ApiOperation(value = "端点指标数据endpointResponseTime")
    @PostMapping("/responseTime")
    public List<CrossAxisGraphPoint> endpointResponseTime(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBodyKpi commonRequestBodyKpi
    ) {
        return endpointKpiService.getResponseTime(commonRequestBodyKpi.getDuration(), commonRequestBodyKpi.getId());
    }

    @ApiOperation(value = "端点指标数据endpointThroughput")
    @PostMapping("/throughput")
    public List<CrossAxisGraphPoint> endpointThroughput(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBodyKpi commonRequestBodyKpi
    ) {
        return endpointKpiService.getThroughput(commonRequestBodyKpi.getDuration(), commonRequestBodyKpi.getId());
    }

    @ApiOperation(value = "端点指标数据endpointSLA")
    @PostMapping("/sla")
    public List<CrossAxisGraphPoint> endpointSLA(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBodyKpi commonRequestBodyKpi
    ) {
        return endpointKpiService.getSla(commonRequestBodyKpi.getDuration(), commonRequestBodyKpi.getId());
    }

    @ApiOperation(value = "端点指标数据endpointPercentile")
    @PostMapping("/percentile")
    public PercentileGraph endpointPercentile(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBodyKpi commonRequestBodyKpi
    ) {
        return endpointKpiService.getPercentileGraph(commonRequestBodyKpi.getDuration(), commonRequestBodyKpi.getId());
    }

    @ApiOperation(value = "端点指标数据globalSlow")
    @PostMapping("/globalSlow")
    public List<SimpleOrderNode> endpointGlobalSlow(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBodyKpi commonRequestBodyKpi
    ) {
        return endpointKpiService.getGlobalSlowEndpoint(commonRequestBodyKpi.getDuration());
    }

    @ApiOperation(value = "端点指标数据endpointTopology")
    @PostMapping("/topology")
    public EndpointTopology endpointTopology(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBodyKpi commonRequestBodyKpi
    ) {
        return endpointTopologyService.selectEndpointTopology(commonRequestBodyKpi.getDuration(), commonRequestBodyKpi.getId());
    }

    @ApiOperation(value = "端点指标数据endpointTraces")
    @PostMapping("/traces")
    public TraceGraph endpointTraces(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBodyKpi commonRequestBodyKpi
    ) {
        return traceService.queryTracesInfoByEndpointId(commonRequestBodyKpi.getDuration(), commonRequestBodyKpi.getId());
    }
}

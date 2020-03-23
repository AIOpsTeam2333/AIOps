package com.aiops.api.controller;

import com.aiops.api.common.type.KpiType;
import com.aiops.api.common.validation.NeedIdGroup;
import com.aiops.api.entity.vo.request.CommonRequestBody;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import com.aiops.api.entity.vo.response.EndpointKpiAll;
import com.aiops.api.entity.vo.response.PercentileGraph;
import com.aiops.api.entity.vo.response.SimpleOrderNode;
import com.aiops.api.service.kpi.EndpointKpiService;
import com.aiops.api.service.kpi.GlobalKpiService;
import com.aiops.api.service.kpi.KpiHelper;
import com.aiops.api.service.metadata.MetadataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    private final KpiHelper kpiHelper;
    private final MetadataService metadataService;

    @ApiOperation(value = "端点指标数据")
    @PostMapping("/")
    public EndpointKpiAll endpointKpiAllData(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBody commonRequestBody
    ) {
        Set<KpiType> kpiTypes = kpiHelper.splitKpi(commonRequestBody.getBusiness());
        final boolean kpiEmpty = kpiTypes.isEmpty();
        Duration duration = commonRequestBody.getDuration();
        Integer endpointId = commonRequestBody.getId();
        EndpointKpiAll result = new EndpointKpiAll();

        if (kpiEmpty || kpiTypes.contains(KpiType.RESPONSE_TIME)) {
            result.setEndpointResponseTime(endpointKpiService.getResponseTime(duration, endpointId));
        }

        if (kpiEmpty || kpiTypes.contains(KpiType.THROUGHPUT)) {
            result.setEndpointThroughput(endpointKpiService.getThroughput(duration, endpointId));
        }

        if (kpiEmpty || kpiTypes.contains(KpiType.SLA)) {
            result.setEndpointSLA(endpointKpiService.getSla(duration, endpointId));
        }

        if (kpiEmpty || kpiTypes.contains(KpiType.PERCENTILE) || kpiTypes.contains(KpiType.P50) || kpiTypes.contains(KpiType.P75) || kpiTypes.contains(KpiType.P90) || kpiTypes.contains(KpiType.P95) || kpiTypes.contains(KpiType.P99)) {
            result.setEndpointPercentile(endpointKpiService.getPercentileGraph(duration, endpointId));
            result.setGlobalPercentile(globalKpiService.getGlobalPercentileGraph(duration));
        }

        //TODO
        result.setGlobalBrief(metadataService.globalBrief());
        result.setGlobalSlow(endpointKpiService.getGlobalSlowEndpoint(duration));
        result.setEndpointTopology(null);
        result.setEndpointTraces(null);
        return result;
    }

    @ApiOperation(value = "端点指标数据endpointResponseTime")
    @PostMapping("/endpointResponseTime")
    public List<CrossAxisGraphPoint> endpointResponseTime(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBody commonRequestBody
    ) {
        return endpointKpiService.getResponseTime(commonRequestBody.getDuration(), commonRequestBody.getId());
    }

    @ApiOperation(value = "端点指标数据endpointThroughput")
    @PostMapping("/endpointThroughput")
    public List<CrossAxisGraphPoint> endpointThroughput(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBody commonRequestBody
    ) {
        return endpointKpiService.getThroughput(commonRequestBody.getDuration(), commonRequestBody.getId());
    }

    @ApiOperation(value = "端点指标数据endpointSLA")
    @PostMapping("/endpointSLA")
    public List<CrossAxisGraphPoint> endpointSLA(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBody commonRequestBody
    ) {
        return endpointKpiService.getSla(commonRequestBody.getDuration(), commonRequestBody.getId());
    }

    @ApiOperation(value = "端点指标数据endpointPercentile")
    @PostMapping("/endpointPercentile")
    public PercentileGraph endpointPercentile(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBody commonRequestBody
    ) {
        return endpointKpiService.getPercentileGraph(commonRequestBody.getDuration(), commonRequestBody.getId());
    }

    @ApiOperation(value = "端点指标数据globalSlow")
    @PostMapping("/globalSlow")
    public List<SimpleOrderNode> endpointGlobalSlow(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBody commonRequestBody
    ) {
        return endpointKpiService.getGlobalSlowEndpoint(commonRequestBody.getDuration());
    }
}

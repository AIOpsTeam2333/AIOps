package com.aiops.api.controller;

import com.aiops.api.common.enums.KpiType;
import com.aiops.api.common.validation.NeedIdGroup;
import com.aiops.api.entity.vo.request.RequestBodyKpi;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import com.aiops.api.entity.vo.response.PercentileGraph;
import com.aiops.api.entity.vo.response.ServiceKpiAll;
import com.aiops.api.service.kpi.*;
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
 * @create 2020-03-06 11:11
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = {"服务指标数据查询"})
@RestController
@RequestMapping("/service")
public class ServiceKpiController {

    private final ServiceKpiService serviceKpiService;
    private final GlobalKpiService globalKpiService;
    private final EndpointKpiService endpointKpiService;
    private final InstanceKpiService instanceKpiService;

    @ApiOperation(value = "服务指标数据")
    @PostMapping("/")
    public ServiceKpiAll serviceKpiAllData(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        KpiIndicator kpiIndicator = requestBodyKpi.getBusiness();
        ServiceKpiAll serviceKpiAll = new ServiceKpiAll();
        Duration duration = requestBodyKpi.getDuration();
        Integer id = requestBodyKpi.getId();
        if (kpiIndicator.needKpiType(KpiType.APDEX_SCORE)) {
            serviceKpiAll.setServiceApdexScore(serviceKpiService.getApdexScore(duration, id));
        }

        if (kpiIndicator.needKpiType(KpiType.RESPONSE_TIME)) {
            serviceKpiAll.setServiceResponseTime(serviceKpiService.getResponseTime(duration, id));
        }

        if (kpiIndicator.needKpiType(KpiType.THROUGHPUT)) {
            serviceKpiAll.setServiceThroughput(serviceKpiService.getThroughput(duration, id));
        }

        if (kpiIndicator.needKpiType(KpiType.SLA)) {
            serviceKpiAll.setServiceSLA(serviceKpiService.getSla(duration, id));
        }

        if (kpiIndicator.needPercentile()) {
            serviceKpiAll.setGlobalPercentile(globalKpiService.getGlobalPercentileGraph(duration));
            serviceKpiAll.setServicePercentile(serviceKpiService.getPercentileGraph(duration, id));
        }

        serviceKpiAll.setServiceSlowEndpoint(endpointKpiService.getServiceSlowEndpoint(duration, id));
        serviceKpiAll.setServiceInstanceThroughput(instanceKpiService.getServiceInstanceThroughput(duration, id));
        return serviceKpiAll;
    }


    @ApiOperation(value = "服务指标数据serviceApdexScore")
    @PostMapping("/apdexScore")
    public List<CrossAxisGraphPoint> serviceApdexScore(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        return serviceKpiService.getApdexScore(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }

    @ApiOperation(value = "服务指标数据serviceResponseTime")
    @PostMapping("/responseTime")
    public List<CrossAxisGraphPoint> serviceResponseTime(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {
        return serviceKpiService.getResponseTime(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }

    @ApiOperation(value = "服务指标数据serviceThroughput")
    @PostMapping("/throughput")
    public List<CrossAxisGraphPoint> serviceThroughput(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {

        return serviceKpiService.getThroughput(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }

    @ApiOperation(value = "服务指标数据serviceSLA")
    @PostMapping("/sla")
    public List<CrossAxisGraphPoint> serviceSLA(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {

        return serviceKpiService.getSla(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }

    @ApiOperation(value = "服务指标数据servicePercentile")
    @PostMapping("/percentile")
    public PercentileGraph servicePercentile(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyKpi requestBodyKpi
    ) {

        return serviceKpiService.getPercentileGraph(requestBodyKpi.getDuration(), requestBodyKpi.getId());
    }
}

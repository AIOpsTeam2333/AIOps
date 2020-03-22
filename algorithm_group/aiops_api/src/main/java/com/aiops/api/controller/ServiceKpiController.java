package com.aiops.api.controller;

import com.aiops.api.common.validation.NeedIdGroup;
import com.aiops.api.entity.vo.request.CommonRequestBody;
import com.aiops.api.entity.vo.response.CrossAxisGraphPoint;
import com.aiops.api.entity.vo.response.PercentileGraph;
import com.aiops.api.entity.vo.response.ServiceKpiAll;
import com.aiops.api.service.kpi.GlobalKpiService;
import com.aiops.api.service.kpi.ServiceKpiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @ApiOperation(value = "服务指标数据")
    @PostMapping("/")
    public ServiceKpiAll serviceKpiAllData(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBody commonRequestBody
    ) {
        ServiceKpiAll serviceKpiAll = new ServiceKpiAll();
        return serviceKpiAll;
    }


    @ApiOperation(value = "服务指标数据serviceApdexScore")
    @PostMapping("/serviceApdexScore")
    public List<CrossAxisGraphPoint> serviceApdexScore(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBody commonRequestBody
    ) {
        return serviceKpiService.getApdexScore(commonRequestBody.getDuration(), commonRequestBody.getId());
    }

    @ApiOperation(value = "服务指标数据serviceResponseTime")
    @PostMapping("/serviceResponseTime")
    public List<CrossAxisGraphPoint> serviceResponseTime(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBody commonRequestBody
    ) {
        return serviceKpiService.getResponseTime(commonRequestBody.getDuration(), commonRequestBody.getId());
    }

    @ApiOperation(value = "服务指标数据serviceThroughput")
    @PostMapping("/serviceThroughput")
    public List<CrossAxisGraphPoint> serviceThroughput(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBody commonRequestBody
    ) {

        return serviceKpiService.getThroughput(commonRequestBody.getDuration(), commonRequestBody.getId());
    }

    @ApiOperation(value = "服务指标数据servicePercentile")
    @PostMapping("/servicePercentile")
    public PercentileGraph servicePercentile(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBody commonRequestBody
    ) {

        return serviceKpiService.getPercentileGraph(commonRequestBody.getDuration(), commonRequestBody.getId());
    }
}

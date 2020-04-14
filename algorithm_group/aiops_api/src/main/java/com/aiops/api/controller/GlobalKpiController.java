package com.aiops.api.controller;

import com.aiops.api.common.enums.KpiType;
import com.aiops.api.entity.vo.request.CommonRequestBodyKpi;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.*;
import com.aiops.api.service.kpi.EndpointKpiService;
import com.aiops.api.service.kpi.GlobalKpiService;
import com.aiops.api.service.kpi.KpiHelper;
import com.aiops.api.service.kpi.KpiIndicator;
import com.aiops.api.service.metadata.MetadataService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 9:56
 **/
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = {"全局指标数据查询"})
@RequestMapping("/global")
@RestController
public class GlobalKpiController {

    private final KpiHelper kpiHelper;
    private final GlobalKpiService globalKpiService;
    private final MetadataService metadataService;
    private final EndpointKpiService endpointKpiService;

    @ApiOperation(value = "全局指标数据")
    @ApiImplicitParam
    @PostMapping("/")
    public GlobalKpiAll globalAllData(
            @RequestBody @Valid CommonRequestBodyKpi commonRequestBodyKpi
    ) {
        KpiIndicator kpiIndicator = kpiHelper.splitKpi(commonRequestBodyKpi.getBusiness());

        GlobalKpiAll result = new GlobalKpiAll();
        if (kpiIndicator.needPercentile()) {
            result.setPercentileGraph(globalPercentile(commonRequestBodyKpi.getDuration()));
        }
        if (kpiIndicator.needKpiType(KpiType.HEATMAP)) {
            result.setHeatmapGraph(globalHeatmap(commonRequestBodyKpi.getDuration()));
        }
        result.setGlobalBrief(metadataService.getGlobalBrief());
        result.setGlobalSlow(endpointKpiService.getGlobalSlowEndpoint(commonRequestBodyKpi.getDuration()));
        result.setGlobalThroughput(globalKpiService.getGlobalThroughout(commonRequestBodyKpi.getDuration()));
        return result;
    }

    @ApiOperation(value = "全局视图")
    @GetMapping("/brief")
    public GlobalBrief globalBrief() {
        return metadataService.getGlobalBrief();
    }

    @ApiOperation(value = "全局百分位数, 包括p50-p99")
    @PostMapping("/percentile")
    public PercentileGraph globalPercentile(
            @RequestBody @Valid Duration duration
    ) {
        return globalKpiService.getGlobalPercentileGraph(duration);
    }

    @ApiOperation(value = "全局热量图")
    @PostMapping("/heatmap")
    public HeatmapGraph globalHeatmap(
            @RequestBody @Valid Duration duration
    ) {
        return globalKpiService.getGlobalHeatmapGraph(duration);
    }
}

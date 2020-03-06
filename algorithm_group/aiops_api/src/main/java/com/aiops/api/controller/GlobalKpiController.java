package com.aiops.api.controller;

import com.aiops.api.common.type.KpiType;
import com.aiops.api.dao.ServiceNodeDao;
import com.aiops.api.dao.ServiceEndpointDao;
import com.aiops.api.entity.vo.request.CommonRequestBody;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.*;
import com.aiops.api.service.kpi.KpiHelper;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

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

    private final ServiceEndpointDao serviceEndpointDao;
    private final ServiceNodeDao serviceNodeDao;
    private final KpiHelper kpiHelper;

    @ApiOperation(value = "全局指标数据")
    @ApiImplicitParam
    @PostMapping("/")
    public GlobalKpiAll globalAllData(
            @RequestBody @Valid CommonRequestBody commonRequestBody,
            BindingResult bindingResult
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        Set<KpiType> kpis = kpiHelper.splitKpi(commonRequestBody.getBusiness());

        GlobalKpiAll result = new GlobalKpiAll();
        if (kpis.contains(KpiType.PERCENTILE) || kpis.contains(KpiType.P50) || kpis.contains(KpiType.P75) || kpis.contains(KpiType.P90) || kpis.contains(KpiType.P95) || kpis.contains(KpiType.P99)) {
            result.setPercentileGraph(globalPercentile(commonRequestBody.getDuration(), bindingResult));
        }
        if (kpis.contains(KpiType.HEATMAP)) {
            result.setHeatmapGraph(globalHeatmap(commonRequestBody.getDuration(), bindingResult));
        }
        return result;
    }

    @ApiOperation(value = "全局视图")
    @GetMapping("/globalBrief")
    public GlobalBrief globalBrief() {
        GlobalBrief globalBrief = new GlobalBrief();
        globalBrief.setNumOfEndpoint(serviceEndpointDao.countAll());
        globalBrief.setNumOfService(serviceNodeDao.countAllService());
        globalBrief.setNumOfDatabase(serviceNodeDao.countAllDatabase());
        return globalBrief;
    }

    @ApiOperation(value = "全局百分位数, 包括p50-p99")
    @PostMapping("/globalPercentile")
    public PercentileGraph globalPercentile(
            @RequestBody @Valid Duration duration,
            BindingResult bindingResult
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return null;
    }

    @ApiOperation(value = "全局热量图")
    @PostMapping("/globalHeatmap")
    public HeatmapGraph globalHeatmap(
            @RequestBody @Valid Duration duration,
            BindingResult bindingResult
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return null;
    }
}

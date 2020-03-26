package com.aiops.api.controller;

import com.aiops.api.common.enums.KpiType;
import com.aiops.api.common.validation.NeedIdGroup;
import com.aiops.api.entity.vo.request.CommonRequestBodyKpi;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.DatabaseKpiAll;
import com.aiops.api.service.kpi.GlobalKpiService;
import com.aiops.api.service.kpi.KpiHelper;
import com.aiops.api.service.kpi.KpiIndicator;
import com.aiops.api.service.kpi.ServiceKpiService;
import com.aiops.api.service.metadata.MetadataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 11:13
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = {"数据库指标数据查询"})
@RestController
@RequestMapping("/database")
public class DatabaseKpiController {

    private final KpiHelper kpiHelper;
    private final GlobalKpiService globalKpiService;
    private final MetadataService metadataService;
    private final ServiceKpiService serviceKpiService;

    @ApiOperation(value = "数据库指标数据")
    @PostMapping("/")
    public DatabaseKpiAll endpointKpiAllData(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBodyKpi commonRequestBodyKpi
    ) {
        KpiIndicator kpiIndicator = kpiHelper.splitKpi(commonRequestBodyKpi.getBusiness());
        Duration duration = commonRequestBodyKpi.getDuration();
        Integer databaseId = commonRequestBodyKpi.getId();
        DatabaseKpiAll result = new DatabaseKpiAll();

        if (kpiIndicator.needKpiType(KpiType.RESPONSE_TIME)) {
            result.setDatabaseResponseTime(serviceKpiService.getResponseTime(duration, databaseId));
        }
        if (kpiIndicator.needKpiType(KpiType.THROUGHPUT)) {
            result.setDatabaseResponseTime(serviceKpiService.getThroughput(duration, databaseId));
        }
        if (kpiIndicator.needKpiType(KpiType.SLA)) {
            result.setDatabaseResponseTime(serviceKpiService.getSla(duration, databaseId));
        }
        if (kpiIndicator.needPercentile()) {
            result.setGlobalPercentile(globalKpiService.getGlobalPercentileGraph(duration));
            result.setDatabasePercentile(serviceKpiService.getPercentileGraph(duration, databaseId));
        }

        //todo
        result.setDatabaseTopNRecords(null);
        result.setGlobalBrief(metadataService.getGlobalBrief());

        return result;
    }

}

package com.aiops.api.entity.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 18:55
 */
@Data
public class DatabaseKpiAll {

    private GlobalBrief globalBrief;
    private List<CrossAxisGraphPoint> databaseResponseTime;
    private List<CrossAxisGraphPoint> databaseThroughput;
    private List<CrossAxisGraphPoint> databaseSLA;
    private PercentileGraph globalPercentile;
    private PercentileGraph databasePercentile;
    @ApiModelProperty(hidden = true)
    private List<DatabaseRecord> databaseTopNRecords;
}

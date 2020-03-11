package com.aiops.api.entity.vo.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 11:11
 **/
@Data
@ApiModel("Service接口返回值")
public class ServiceKpiAll {

    private PercentileGraph globalPercentile;
    private List<CrossAxisGraphPoint> serviceApdexScore;
    private List<CrossAxisGraphPoint> serviceResponseTime;
    private List<CrossAxisGraphPoint> serviceThroughput;
    private List<CrossAxisGraphPoint> serviceSLA;
    private PercentileGraph servicePercentile;
    private List<SimpleOrderNode> serviceSlowEndpoint;
    private List<SimpleOrderNode> serviceInstanceThroughput;
}

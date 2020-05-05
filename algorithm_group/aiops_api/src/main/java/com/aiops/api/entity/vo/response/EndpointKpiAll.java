package com.aiops.api.entity.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 11:12
 */
@Data
public class EndpointKpiAll {

    private GlobalBrief globalBrief;
    private List<CrossAxisGraphPoint> endpointResponseTime;
    private List<CrossAxisGraphPoint> endpointThroughput;
    private List<CrossAxisGraphPoint> endpointSLA;
    private PercentileGraph globalPercentile;
    private PercentileGraph endpointPercentile;
    private EndpointTopology endpointTopology;
    private List<SimpleOrderNode> globalSlow;
    private TraceGraph endpointTraces;

}

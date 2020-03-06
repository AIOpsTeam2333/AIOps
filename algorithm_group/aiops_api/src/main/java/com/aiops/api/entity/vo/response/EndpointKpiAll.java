package com.aiops.api.entity.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 11:12
 */
@Data
public class EndpointKpiAll {

    private List<CrossAxisGraphPoint> endpointResponseTime;
    private List<CrossAxisGraphPoint> endpointThroughput;
    private List<CrossAxisGraphPoint> endpointSLA;
    private PercentileGraph endpointPercentile;
    private Topology endpointTopology;
    private List<SimpleOrderNode> globalSlow;
    private TracesGraph endpointTraces;

}

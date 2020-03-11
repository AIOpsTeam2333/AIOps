package com.aiops.api.entity.vo.response;

import lombok.Data;

import java.util.List;


/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 11:09
 **/
@Data
public class GlobalKpiAll {

    //百分位数
    private PercentileGraph percentileGraph;

    //热量图
    private HeatmapGraph heatmapGraph;

    //全局吞吐量
    private List<SimpleOrderNode> globalThroughput;

    //全局最慢Endpoint
    private List<SimpleOrderNode> globalSlow;

    //全局概览
    private GlobalBrief globalBrief;
}

package com.aiops.api.entity.vo.response;

import lombok.Data;

import java.util.List;

/**
 * 百分位数数据
 * @author Shuaiyu Yao
 * @create 2020-03-06 13:35
 */
@Data
public class PercentileGraph {

    private List<CrossAxisGraphPoint> p50;
    private List<CrossAxisGraphPoint> p75;
    private List<CrossAxisGraphPoint> p90;
    private List<CrossAxisGraphPoint> p95;
    private List<CrossAxisGraphPoint> p99;

}

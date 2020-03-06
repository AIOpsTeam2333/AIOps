package com.aiops.api.entity.vo.response;

import lombok.Data;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 13:45
 */
@Data
public class HeatmapPoint {

    private Integer x;

    private Integer y;

    private Integer value;
}

package com.aiops.api.entity.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 14:16
 */
@Data
public class HeatmapGraph {

    private List<HeatmapPoint> nodes;

    /**
     * 步长
     */
    @ApiModelProperty()
    private Integer responseTimeStep;

}

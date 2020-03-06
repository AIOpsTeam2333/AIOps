package com.aiops.api.entity.vo.request;


import com.aiops.api.common.type.StatisticsStep;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 10:54
 **/
@Data
@ApiModel
public class Duration {

    @JsonProperty(value = "start", required = true)
    @NotBlank(message = "起始时间不能为空")
    @ApiModelProperty(name = "start", example = "1970-01")
    private String start;

    @JsonProperty(value = "end", required = true)
    @NotBlank(message = "结束时间不能为空")
    @ApiModelProperty(name = "stop", example = "2020-12")
    private String end;

    @JsonProperty(value = "step", required = true)
    @NotNull(message = "时间步长不能为空")
    @ApiModelProperty(name = "step", example = "MONTH")
    private StatisticsStep step;
}

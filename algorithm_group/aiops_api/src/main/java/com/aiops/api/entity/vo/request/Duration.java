package com.aiops.api.entity.vo.request;


import com.aiops.api.common.enums.StatisticsStep;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 10:54
 **/
@Data
@ApiModel
public class Duration {

    @JsonProperty(value = "start", required = true)
    @NotNull(message = "起始时间不能为空")
    @ApiModelProperty(name = "start", example = "2020-03-01")
    private Date start;

    @JsonProperty(value = "end", required = true)
    @NotNull(message = "结束时间不能为空")
    @ApiModelProperty(name = "stop", example = "2020-05-02")
    private Date end;

    @JsonProperty(value = "step", required = true)
    @NotNull(message = "时间步长不能为空")
    @ApiModelProperty(name = "step", example = "DAY")
    private StatisticsStep step;

    @ApiModelProperty(hidden = true)
    public String getStepName() {
        return step.getName();
    }
}

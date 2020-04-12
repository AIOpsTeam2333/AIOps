package com.aiops.api.entity.vo.request;

import com.aiops.api.common.enums.StatisticsStep;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.Date;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-25 13:55
 */
public interface WithDuration {

    Duration getDuration();

    @ApiModelProperty(hidden = true)
    @ApiParam(hidden = true)
    default Date getDurationFrom() {
        if (getDuration() == null) return null;
        return getDuration().getStart();
    }

    @ApiModelProperty(hidden = true)
    @ApiParam(hidden = true)
    default Date getDurationTo() {
        if (getDuration() == null) return null;
        return getDuration().getEnd();
    }

    @ApiModelProperty(hidden = true)
    @ApiParam(hidden = true)
    default StatisticsStep getDurationStep() {
        if (getDuration() == null) return null;
        return getDuration().getStep();
    }
}

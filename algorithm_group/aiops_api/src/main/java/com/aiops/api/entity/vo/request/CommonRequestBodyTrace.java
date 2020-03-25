package com.aiops.api.entity.vo.request;

import com.aiops.api.common.enums.QueryOrder;
import com.aiops.api.common.enums.TraceState;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 12:47
 **/
@Data
@ApiModel(description = "Trace接口参数")
public class CommonRequestBodyTrace implements WithDuration, WithPaging {

    private Integer serviceId;

    private Integer serviceInstanceId;

    private Integer endpointId;

    private TraceState traceState;

    private QueryOrder queryOrder;

    @Valid
    @NotNull(message = "duration不能为空")
    private Duration duration;

    private Paging paging;

    private Integer minTraceDuration;

    private Integer maxTraceDuration;
}

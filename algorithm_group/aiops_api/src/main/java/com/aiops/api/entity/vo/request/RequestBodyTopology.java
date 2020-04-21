package com.aiops.api.entity.vo.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-20 19:45
 */
@Data
public class RequestBodyTopology {

    @Valid
    @NotNull(message = "duration不能为空")
    private Duration duration;

    private Integer serviceId;

}

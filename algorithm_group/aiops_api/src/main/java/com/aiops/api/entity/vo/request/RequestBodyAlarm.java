package com.aiops.api.entity.vo.request;

import com.aiops.api.common.enums.ScopeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 12:47
 **/
@Data
@ApiModel(description = "Trace接口参数")
public class RequestBodyAlarm implements WithDuration, WithPaging {

    @Valid
    @NotNull(message = "duration不能为空")
    private Duration duration;

    @Valid
    private Paging paging;

    @ApiModelProperty(example = "test")
    private String keyword;

    @ApiModelProperty(example = "Instance")
    private ScopeType scope;

}

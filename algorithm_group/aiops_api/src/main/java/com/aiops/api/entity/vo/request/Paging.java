package com.aiops.api.entity.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-24 19:12
 **/
@Data
public class Paging {

    @NotNull(message = "pageNum不能为null")
    @ApiModelProperty(example = "1")
    private Integer pageNum;

    @ApiModelProperty(example = "20")
    @NotNull(message = "pageSize不能为null")
    private Integer pageSize;

}

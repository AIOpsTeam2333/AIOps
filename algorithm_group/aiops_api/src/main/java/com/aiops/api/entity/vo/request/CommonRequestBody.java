package com.aiops.api.entity.vo.request;

import com.aiops.api.common.validation.NeedIdGroup;
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
@ApiModel(description = "接口参数")
public class CommonRequestBody {

    @Valid
    @NotNull(message = "duration不能为空")
    private Duration duration;

    @ApiModelProperty(value = "查询业务名称, 按逗号隔开, 无则为全部查询", name = "business")
    private String business;

    @NotNull(groups = NeedIdGroup.class, message = "id不能为空")
    @ApiModelProperty(value = "查询的数据的id", example = "1")
    private Integer id;
}

package com.aiops.api.entity.vo.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-25 14:01
 **/
public interface WithPaging {

    Paging getPaging();

    @ApiModelProperty(hidden = true)
    @ApiParam(hidden = true)
    default Integer getPagingNum() {
        if (getPaging() == null) return null;
        return getPaging().getPageNum();
    }

    @ApiModelProperty(hidden = true)
    @ApiParam(hidden = true)
    default Integer getPagingSize() {
        if (getPaging() == null) return null;
        return getPaging().getPageSize();
    }
}

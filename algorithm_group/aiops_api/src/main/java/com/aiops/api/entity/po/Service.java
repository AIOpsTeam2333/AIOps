package com.aiops.api.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 16:30
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class Service extends ServiceNode{
//
//    private Integer serviceId;
//
//    private String name;
//
//    private String description;
//
//    @ApiModelProperty(example = "2000-01-01 00:00:00")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date addTimestamp;
//
//    private String nodeType;

    private Integer serviceId;
}

package com.aiops.api.entity.dto;

import lombok.Data;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 0:20
 **/
@Data
public class ServiceInstanceSearchDto {

    private Integer serviceInstanceId;

    private Integer serviceId;

    private String name;

    private String language;

    private String uuid;
}

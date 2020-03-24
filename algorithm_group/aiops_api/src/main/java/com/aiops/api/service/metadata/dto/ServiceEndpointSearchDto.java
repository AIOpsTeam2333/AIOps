package com.aiops.api.service.metadata.dto;

import lombok.Data;
/**
 * @author Shuaiyu Yao
 * @create 2020-03-03 23:55
 **/
@Data
public class ServiceEndpointSearchDto {
    private Integer serviceEndpointId;

    private Integer serviceId;

    private String name;
}

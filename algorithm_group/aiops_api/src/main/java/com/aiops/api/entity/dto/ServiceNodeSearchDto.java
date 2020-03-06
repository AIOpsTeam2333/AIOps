package com.aiops.api.entity.dto;

import lombok.Data;
/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 19:17
 **/
@Data
public class ServiceNodeSearchDto {
    private Integer nodeId;

    private String name;

    private String nodeType;
}

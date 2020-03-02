package com.aiops.api.entity.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 19:17
 **/
@Data
public class ServiceInfoSearchDto {
    private Integer serviceId;

    private String name;

    private String serviceContainerId;

    private Date addTimeFrom;

    private Date addTimeTo;

}

package com.aiops.api.entity.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 19:35
 **/
@Data
public class ServiceContainerInfoSearchDto {

    private String serviceContainerId;

    private String os;

    private String ip;

    private Date addTimeFrom;

    private Date addTimeTo;

    private Boolean isInUse;

    private Date deprecatedTimeFrom;

    private Date deprecatedTimeTo;

}

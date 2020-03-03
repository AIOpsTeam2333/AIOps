package com.aiops.api.entity.dto;

import lombok.Data;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-03 16:22
 */
@Data
public class ServiceBasicInfoDto {

    private Integer currentRunningServiceContainerCount;

    private Integer totalServiceCount;
}

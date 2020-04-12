package com.aiops.api.service.trace.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-25 13:34
 */
@Data
public class TraceSearchDto {

    private Integer serviceId;

    private Integer serviceInstanceId;

    private Integer endpointId;

    private String traceState;

    private String queryOrder;

    private Date from;

    private Date to;

    private Integer minTraceDuration;

    private Integer maxTraceDuration;
}

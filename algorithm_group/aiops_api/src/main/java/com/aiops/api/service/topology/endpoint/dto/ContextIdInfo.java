package com.aiops.api.service.topology.endpoint.dto;

import lombok.Data;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-24 15:11
 **/
@Data
public class ContextIdInfo {

    private Integer spanId;

    private Integer parentSpanId;

    private String name;

}

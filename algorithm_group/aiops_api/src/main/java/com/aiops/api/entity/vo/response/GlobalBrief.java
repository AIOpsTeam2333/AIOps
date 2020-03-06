package com.aiops.api.entity.vo.response;

import lombok.Data;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 9:46
 **/
@Data
public class GlobalBrief {

    private Integer numOfService;
    private Integer numOfEndpoint;
    private Integer numOfDatabase;
//    private Integer numOfCache;
//    private Integer numOfMQ;
}

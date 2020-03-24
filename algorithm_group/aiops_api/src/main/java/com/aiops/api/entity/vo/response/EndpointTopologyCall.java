package com.aiops.api.entity.vo.response;

import lombok.Data;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 17:45
 */
@Data
public class EndpointTopologyCall {

    private String id;

    private Integer source;

    private Integer target;

}

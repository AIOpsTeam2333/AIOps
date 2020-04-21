package com.aiops.api.entity.vo.response;

import lombok.Data;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-20 18:42
 */
@Data
public class TopologyNode {

    private Integer id;

    private String name;

    private String type;

    private Boolean isReal;
}

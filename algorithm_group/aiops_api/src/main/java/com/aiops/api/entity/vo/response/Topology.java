package com.aiops.api.entity.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-20 18:42
 */
@Data
public class Topology {

    List<TopologyCall> calls;

    List<TopologyNode> nodes;

}

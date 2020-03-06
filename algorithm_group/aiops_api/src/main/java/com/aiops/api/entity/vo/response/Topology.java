package com.aiops.api.entity.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 17:45
 **/
@Data
public class Topology {

    List<TopologyNode> nodes;

    List<TopologyCall> calls;

}

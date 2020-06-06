package com.aiops.api.service.topology.service;

import com.aiops.api.common.enums.ScopeType;
import com.aiops.api.dao.ServiceNodeMetadataDao;
import com.aiops.api.dao.TopologyDao;
import com.aiops.api.entity.po.Service;
import com.aiops.api.entity.po.ServiceNode;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.Topology;
import com.aiops.api.entity.vo.response.TopologyCall;
import com.aiops.api.entity.vo.response.TopologyNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-24 14:51
 **/
@org.springframework.stereotype.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceTopologyService {

    private final TopologyDao topologyDao;
    private final ServiceNodeMetadataDao serviceNodeMetadataDao;

    /**
     * 查询service的拓扑关系
     *
     * @param duration  duration
     * @param serviceId
     * @return
     */
    public Topology getServiceTopology(Duration duration, Integer serviceId) {
        List<TopologyCall> calls = queryCalls(duration, serviceId);
        //获取其中的id
        Set<Integer> serviceIds = new HashSet<>();
        for (TopologyCall call : calls) {
            serviceIds.add(call.getTarget());
            serviceIds.add(call.getSource());
        }
        List<Integer> serviceIdList = new ArrayList<>(serviceIds);

        //根据Id获取节点信息
        List<TopologyNode> nodes = queryNodes(serviceIdList);

        //生成Topology实体
        Topology result = new Topology();
        result.setCalls(calls);
        result.setNodes(nodes);
        return result;
    }

    /**
     * 查询调用依赖
     *
     * @param duration
     * @param serviceId
     * @return
     */
    private List<TopologyCall> queryCalls(Duration duration, Integer serviceId) {
        return topologyDao.queryTopologyCalls(duration, serviceId, ScopeType.SERVICE);
    }

    /**
     * 查询节点
     *
     * @param serviceIds
     * @return
     */
    private List<TopologyNode> queryNodes(List<Integer> serviceIds) {
        List<ServiceNode> serviceNodes = serviceNodeMetadataDao.queryServicesByIds(serviceIds);
        List<TopologyNode> nodes = serviceNodes.stream().map(serviceNode -> {
            TopologyNode node = new TopologyNode();
            node.setId(serviceNode.getNodeId());
            node.setName(serviceNode.getName());
            node.setIsReal(true);
            node.setType(serviceNode.getNodeType());
            return node;
        }).collect(Collectors.toList());
        return nodes;
    }
}

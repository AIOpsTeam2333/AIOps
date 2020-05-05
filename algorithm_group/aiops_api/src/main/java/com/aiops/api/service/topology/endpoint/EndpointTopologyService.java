package com.aiops.api.service.topology.endpoint;

import com.aiops.api.common.enums.ScopeType;
import com.aiops.api.dao.EndpointMetadataDao;
import com.aiops.api.dao.TopologyDao;
import com.aiops.api.entity.po.Endpoint;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.Topology;

import com.aiops.api.entity.vo.response.TopologyCall;
import com.aiops.api.entity.vo.response.TopologyNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author Shuaiyu Yao
 * @create 2020-03-24 14:46
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EndpointTopologyService {

    private final TopologyDao topologyDao;
    private final EndpointMetadataDao endpointMetadataDao;

    public Topology selectEndpointTopology(Duration duration, Integer endpointId) {
        List<TopologyCall> calls = topologyDao.queryTopologyCalls(duration, endpointId, ScopeType.ENDPOINT);
        Set<Integer> endpointIds = new HashSet<>();
        for (TopologyCall call : calls) {
            endpointIds.add(call.getSource());
            endpointIds.add(call.getTarget());
        }
        List<Integer> endpointIdList = new ArrayList<>(endpointIds);
        List<Endpoint> endpoints = endpointMetadataDao.queryByIds(endpointIdList);
        List<TopologyNode> nodes = endpoints.stream()
                .map(endpoint -> {
                    TopologyNode node = new TopologyNode();
                    node.setId(endpoint.getServiceEndpointId());
                    node.setName(endpoint.getName());
                    return node;
                }).collect(Collectors.toList());

        Topology result = new Topology();
        result.setNodes(nodes);
        result.setCalls(calls);
        return result;
    }

}

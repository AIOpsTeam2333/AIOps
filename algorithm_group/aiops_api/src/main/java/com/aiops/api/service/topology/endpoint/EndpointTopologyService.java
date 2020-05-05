package com.aiops.api.service.topology.endpoint;

import com.aiops.api.dao.EndpointTopologyDao;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.EndpointTopology;
import com.aiops.api.entity.vo.response.EndpointTopologyCall;
import com.aiops.api.entity.vo.response.EndpointTopologyNode;
import com.aiops.api.service.topology.endpoint.dto.ContextIdInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Shuaiyu Yao
 * @create 2020-03-24 14:46
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EndpointTopologyService {

    private final EndpointTopologyDao endpointTopologyDao;

    public EndpointTopology selectEndpointTopology(Duration duration, Integer endpointId) {
        List<ContextIdInfo> contextIdInfoList = endpointTopologyDao.getContextIdInfo(duration, endpointId);
        if (contextIdInfoList == null || contextIdInfoList.isEmpty()) {
            //如果未找到上下文, 返回null
            EndpointTopology result = new EndpointTopology();
            result.setNodes(new ArrayList<>());
            result.setCalls(new ArrayList<>());
            return result;
        }

        String endpointName = contextIdInfoList.get(0).getName();
        //主节点
        EndpointTopologyNode mainNode = new EndpointTopologyNode();
        mainNode.setId(endpointId);
        mainNode.setValue(endpointName);

        //上游节点
        List<Integer> parentSpanIds = contextIdInfoList.stream()
                .map(ContextIdInfo::getParentSpanId)
                .collect(Collectors.toList());
        List<EndpointTopologyNode> upstreamNodes = endpointTopologyDao.selectNodesBySpanId(parentSpanIds, duration);

        //下游节点
        List<Integer> mainSpanIds = contextIdInfoList.stream()
                .map(ContextIdInfo::getSpanId)
                .collect(Collectors.toList());
        List<EndpointTopologyNode> downstreamNodes = endpointTopologyDao.selectNodesByParentSpanId(mainSpanIds, duration);

        //全部节点
        List<EndpointTopologyNode> totalNodes = new ArrayList<>(upstreamNodes.size() + downstreamNodes.size() + 1);
        totalNodes.addAll(upstreamNodes);
        totalNodes.addAll(downstreamNodes);
        totalNodes.add(mainNode);
        //拓扑关系
        List<EndpointTopologyCall> totalCalls = new ArrayList<>(upstreamNodes.size() + downstreamNodes.size());
        //加入上游调用关系
        for (EndpointTopologyNode node : upstreamNodes) {
            EndpointTopologyCall call = new EndpointTopologyCall();
            call.setSource(node.getId());
            call.setTarget(mainNode.getId());
            call.setId(call.getSource() + "_" + call.getTarget());
            totalCalls.add(call);
        }
        //加入下游调用关系
        for (EndpointTopologyNode node : downstreamNodes) {
            EndpointTopologyCall call = new EndpointTopologyCall();
            call.setSource(mainNode.getId());
            call.setTarget(node.getId());
            call.setId(call.getSource() + "_" + call.getTarget());
            totalCalls.add(call);
        }

        EndpointTopology result = new EndpointTopology();
        result.setNodes(totalNodes);
        result.setCalls(totalCalls);
        return result;
    }

}

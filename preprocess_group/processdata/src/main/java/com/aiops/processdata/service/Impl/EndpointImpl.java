package com.aiops.processdata.service.Impl;

import com.aiops.processdata.dao.EndpointRepository;
import com.aiops.processdata.entity.endpoint.Endpoint_Data;
import com.aiops.processdata.entity.endpoint.Endpoint_Info;
import com.aiops.processdata.entity.endpoint.Endpoint_InfoList;
import com.aiops.processdata.po.endpoint.EndpointPO;
import com.aiops.processdata.service.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/9 23:31
 */
@Service
public class EndpointImpl implements Endpoint {
    @Autowired
    private EndpointRepository endpointRepository;

    @Override
    public List<EndpointPO> save(Endpoint_Data endpoint_data) {
        List<EndpointPO> endpointPOList = new ArrayList<>();
        Endpoint_InfoList infoList = endpoint_data.getEndpoint_infoList();
        for (Endpoint_Info endpoint_info : infoList.getEndpoint_infoList()) {
            EndpointPO endpointPO = endpointRepository.insertEndpoint(endpoint_info);
            endpointPOList.add(endpointPO);
        }
        return endpointPOList;
    }

    @Override
    public Integer findById(String pre_id) {
        EndpointPO po = endpointRepository.findById(pre_id);
        return po == null ? -1 : po.getId();
    }

    @Override
    public Integer findByName(String name) {
        EndpointPO po = endpointRepository.findByName(name);
        return po == null ? -1 : po.getId();
    }
}

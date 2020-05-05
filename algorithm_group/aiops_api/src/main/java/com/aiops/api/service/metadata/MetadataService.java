package com.aiops.api.service.metadata;

import com.aiops.api.dao.EndpointMetadataDao;
import com.aiops.api.dao.ServiceInstanceDao;
import com.aiops.api.dao.ServiceNodeMetadataDao;
import com.aiops.api.entity.po.Database;
import com.aiops.api.entity.po.Endpoint;
import com.aiops.api.entity.po.ServiceInstance;
import com.aiops.api.service.metadata.dto.ServiceEndpointSearchDto;
import com.aiops.api.service.metadata.dto.ServiceInstanceSearchDto;
import com.aiops.api.service.metadata.dto.ServiceNodeSearchDto;
import com.aiops.api.entity.vo.response.GlobalBrief;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-23 20:11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MetadataService {

    private final EndpointMetadataDao endpointMetadataDao;
    private final ServiceInstanceDao serviceInstanceDao;
    private final ServiceNodeMetadataDao serviceNodeMetadataDao;

    public GlobalBrief getGlobalBrief() {
        GlobalBrief globalBrief = new GlobalBrief();
        globalBrief.setNumOfEndpoint(endpointMetadataDao.countAll());
        globalBrief.setNumOfService(serviceNodeMetadataDao.countAllService());
        globalBrief.setNumOfDatabase(serviceNodeMetadataDao.countAllDatabase());
        return globalBrief;
    }

    public List<com.aiops.api.entity.po.Service> getServices(
            Integer serviceId,
            String name,
            String nodeType
    ) {
        ServiceNodeSearchDto dto = new ServiceNodeSearchDto();
        dto.setNodeId(serviceId);
        dto.setName(name);
        dto.setNodeType(nodeType);
        return serviceNodeMetadataDao.selectServices(dto);
    }

    public List<Endpoint> getEndpoints(
            Integer serviceEndpointId,
            Integer serviceId,
            String name
    ) {
        ServiceEndpointSearchDto dto = new ServiceEndpointSearchDto();
        dto.setServiceEndpointId(serviceEndpointId);
        dto.setServiceId(serviceId);
        dto.setName(name);
        return endpointMetadataDao.queryServiceEndpoint(dto);
    }

    public List<ServiceInstance> getServiceInstances(
            Integer serviceInstanceId,
            Integer serviceId,
            String name,
            String language,
            String instanceUuid
    ) {
        ServiceInstanceSearchDto dto = new ServiceInstanceSearchDto();
        dto.setServiceInstanceId(serviceInstanceId);
        dto.setServiceId(serviceId);
        dto.setName(name);
        dto.setLanguage(language);
        dto.setUuid(instanceUuid);
        return serviceInstanceDao.queryServiceInstance(dto);
    }

    public List<Database> getDatabases(
            Integer databaseId,
            String name,
            String nodeType
    ) {
        ServiceNodeSearchDto dto = new ServiceNodeSearchDto();
        dto.setNodeId(databaseId);
        dto.setName(name);
        dto.setNodeType(nodeType);
        return serviceNodeMetadataDao.selectDatabase(dto);
    }
}

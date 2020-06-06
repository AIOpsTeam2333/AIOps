package com.aiops.api.service.metadata;

import com.aiops.api.dao.EndpointMetadataDao;
import com.aiops.api.dao.InstanceMetadataDao;
import com.aiops.api.dao.ServiceNodeMetadataDao;
import com.aiops.api.entity.po.Database;
import com.aiops.api.entity.po.Endpoint;
import com.aiops.api.entity.po.Instance;
import com.aiops.api.entity.po.Service;
import com.aiops.api.service.metadata.dto.ServiceEndpointSearchDto;
import com.aiops.api.service.metadata.dto.ServiceInstanceSearchDto;
import com.aiops.api.service.metadata.dto.ServiceNodeSearchDto;
import com.aiops.api.entity.vo.response.GlobalBrief;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-23 20:11
 */
@org.springframework.stereotype.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MetadataService {

    private final EndpointMetadataDao endpointMetadataDao;
    private final InstanceMetadataDao instanceMetadataDao;
    private final ServiceNodeMetadataDao serviceNodeMetadataDao;

    public GlobalBrief getGlobalBrief() {
        GlobalBrief globalBrief = new GlobalBrief();
        globalBrief.setNumOfEndpoint(endpointMetadataDao.countAll());
        globalBrief.setNumOfService(serviceNodeMetadataDao.countAllService());
        globalBrief.setNumOfDatabase(serviceNodeMetadataDao.countAllDatabase());
        return globalBrief;
    }

    public List<Service> getServices(
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

    public List<Instance> getServiceInstances(
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
        return instanceMetadataDao.queryServiceInstance(dto);
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

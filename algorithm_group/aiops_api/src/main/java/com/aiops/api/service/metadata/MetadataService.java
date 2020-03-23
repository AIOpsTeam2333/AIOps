package com.aiops.api.service.metadata;

import com.aiops.api.dao.ServiceEndpointDao;
import com.aiops.api.dao.ServiceInstanceDao;
import com.aiops.api.dao.ServiceNodeDao;
import com.aiops.api.entity.Database;
import com.aiops.api.entity.ServiceEndpoint;
import com.aiops.api.entity.ServiceInstance;
import com.aiops.api.entity.dto.ServiceEndpointSearchDto;
import com.aiops.api.entity.dto.ServiceInstanceSearchDto;
import com.aiops.api.entity.dto.ServiceNodeSearchDto;
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

    private final ServiceEndpointDao serviceEndpointDao;
    private final ServiceInstanceDao serviceInstanceDao;
    private final ServiceNodeDao serviceNodeDao;

    public GlobalBrief globalBrief() {
        GlobalBrief globalBrief = new GlobalBrief();
        globalBrief.setNumOfEndpoint(serviceEndpointDao.countAll());
        globalBrief.setNumOfService(serviceNodeDao.countAllService());
        globalBrief.setNumOfDatabase(serviceNodeDao.countAllDatabase());
        return globalBrief;
    }

    public List<com.aiops.api.entity.Service> getServices(
            Integer serviceId,
            String name,
            String nodeType
    ) {
        ServiceNodeSearchDto dto = new ServiceNodeSearchDto();
        dto.setNodeId(serviceId);
        dto.setName(name);
        dto.setNodeType(nodeType);
        return serviceNodeDao.selectServices(dto);
    }

    public List<ServiceEndpoint> getEndpoints(
            Integer serviceEndpointId,
            Integer serviceId,
            String name
    ) {
        ServiceEndpointSearchDto dto = new ServiceEndpointSearchDto();
        dto.setServiceEndpointId(serviceEndpointId);
        dto.setServiceId(serviceId);
        dto.setName(name);
        return serviceEndpointDao.queryServiceEndpoint(dto);
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
        return serviceNodeDao.selectDatabase(dto);
    }
}

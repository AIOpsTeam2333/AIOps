package com.aiops.api.dao;

import com.aiops.api.entity.po.ServiceEndpoint;
import com.aiops.api.service.metadata.dto.ServiceEndpointSearchDto;
import com.aiops.api.mapper.ServiceEndpointMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 0:00
 **/
@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceEndpointDao {

    private final ServiceEndpointMapper serviceEndpointMapper;

    public List<ServiceEndpoint> queryServiceEndpoint(ServiceEndpointSearchDto dto) {
        return serviceEndpointMapper.selectList(dto);
    }

    public Integer countAll() {
        ServiceEndpoint endpoint = new ServiceEndpoint();
        return serviceEndpointMapper.selectCount(endpoint);
    }
}

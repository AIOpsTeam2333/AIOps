package com.aiops.api.dao;

import com.aiops.api.entity.ServiceInstance;
import com.aiops.api.entity.dto.ServiceInstanceSearchDto;
import com.aiops.api.mapper.ServiceInstanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 0:27
 **/
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceInstanceDao {

    private final ServiceInstanceMapper serviceInstanceMapper;

    public List<ServiceInstance> queryServiceInstance(ServiceInstanceSearchDto dto) {
        return serviceInstanceMapper.selectList(dto);
    }

}

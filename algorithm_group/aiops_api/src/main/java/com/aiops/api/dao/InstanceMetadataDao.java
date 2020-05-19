package com.aiops.api.dao;

import com.aiops.api.entity.po.Instance;
import com.aiops.api.service.metadata.dto.ServiceInstanceSearchDto;
import com.aiops.api.mapper.InstanceMetadataMapper;
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
public class InstanceMetadataDao {

    private final InstanceMetadataMapper instanceMetadataMapper;

    public List<Instance> queryServiceInstance(ServiceInstanceSearchDto dto) {
        return instanceMetadataMapper.selectList(dto);
    }

}

package com.aiops.api.dao;

import com.aiops.api.entity.po.Database;
import com.aiops.api.entity.po.Service;
import com.aiops.api.entity.po.ServiceNode;
import com.aiops.api.service.metadata.dto.ServiceNodeSearchDto;
import com.aiops.api.mapper.ServiceNodeMetadataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 18:25
 **/
@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceNodeMetadataDao {

    private final ServiceNodeMetadataMapper serviceNodeMetadataMapper;


    public List<Service> selectServices(ServiceNodeSearchDto dto) {
        return serviceNodeMetadataMapper.selectServices(dto);
    }

    public Integer countAllService() {
        return serviceNodeMetadataMapper.countAllServices();
    }

    public List<Database> selectDatabase(ServiceNodeSearchDto dto) {
        return serviceNodeMetadataMapper.selectDatabases(dto);
    }

    public Integer countAllDatabase() {
        return serviceNodeMetadataMapper.countAllDatabases();
    }

    public List<ServiceNode> queryServicesByIds(List<Integer> ids) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return serviceNodeMetadataMapper.selectByIds(ids.stream()
                .map(a -> a + "")
                .reduce((a, b) -> a + "," + b)
                .orElse(""));
    }
}

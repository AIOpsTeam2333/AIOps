package com.aiops.api.dao;

import com.aiops.api.entity.po.Database;
import com.aiops.api.entity.po.Service;
import com.aiops.api.service.metadata.dto.ServiceNodeSearchDto;
import com.aiops.api.mapper.ServiceNodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 18:25
 **/
@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceNodeDao {

    private final ServiceNodeMapper serviceNodeMapper;

//    public List<ServiceNode> selectServiceNodes(ServiceNodeSearchDto dto) {
//        try {
//            return serviceNodeMapper.selectList(dto);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return new ArrayList<>();
//        }
//    }
//
//    public Integer countAllNode() {
//        ServiceNode serviceNode = new ServiceNode();
//        return serviceNodeMapper.selectCount(serviceNode);
//    }

    public List<Service> selectServices(ServiceNodeSearchDto dto) {
        return serviceNodeMapper.selectServices(dto);
    }

    public Integer countAllService() {
        return serviceNodeMapper.countAllServices();
    }

    public List<Database> selectDatabase(ServiceNodeSearchDto dto) {
        return serviceNodeMapper.selectDatabases(dto);
    }

    public Integer countAllDatabase() {
        return serviceNodeMapper.countAllDatabases();
    }
}

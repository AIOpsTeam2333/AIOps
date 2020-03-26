package com.aiops.api.mapper;

import com.aiops.api.config.MyMapper;
import com.aiops.api.entity.po.Database;
import com.aiops.api.entity.po.Service;
import com.aiops.api.entity.po.ServiceNode;
import com.aiops.api.service.metadata.dto.ServiceNodeSearchDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 18:25
 **/
@Repository
public interface ServiceNodeMapper extends MyMapper<ServiceNode> {

    List<Service> selectServices(ServiceNodeSearchDto serviceNodeSearchDto);

    List<Database> selectDatabases(ServiceNodeSearchDto serviceNodeSearchDto);

    Integer countAllServices();

    Integer countAllDatabases();
}

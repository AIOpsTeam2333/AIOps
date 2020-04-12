package com.aiops.api.mapper;

import com.aiops.api.config.MyMapper;
import com.aiops.api.entity.po.ServiceInstance;
import com.aiops.api.service.metadata.dto.ServiceInstanceSearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 0:19
 **/
@Repository
public interface ServiceInstanceMapper extends MyMapper<ServiceInstance> {

    List<ServiceInstance> selectList(ServiceInstanceSearchDto dto);

}

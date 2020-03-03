package com.aiops.api.mapper;

import com.aiops.api.config.MyMapper;
import com.aiops.api.entity.ServiceInstance;
import com.aiops.api.entity.dto.ServiceInstanceSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 0:19
 **/
@Mapper
public interface ServiceInstanceMapper extends MyMapper<ServiceInstance> {

    List<ServiceInstance> selectList(ServiceInstanceSearchDto dto);

}

package com.aiops.api.mapper;

import com.aiops.api.config.MyMapper;
import com.aiops.api.entity.ServiceEndpoint;
import com.aiops.api.entity.dto.ServiceEndpointSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-03 23:56
 **/
@Mapper
public interface ServiceEndpointMapper extends MyMapper<ServiceEndpoint> {

    List<ServiceEndpoint> selectList(ServiceEndpointSearchDto dto);
}

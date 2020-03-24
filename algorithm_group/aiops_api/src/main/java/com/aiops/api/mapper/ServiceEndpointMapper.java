package com.aiops.api.mapper;

import com.aiops.api.config.MyMapper;
import com.aiops.api.entity.po.ServiceEndpoint;
import com.aiops.api.service.metadata.dto.ServiceEndpointSearchDto;
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

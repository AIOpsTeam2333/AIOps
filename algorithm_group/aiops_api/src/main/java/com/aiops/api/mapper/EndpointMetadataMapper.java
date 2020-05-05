package com.aiops.api.mapper;

import com.aiops.api.config.MyMapper;
import com.aiops.api.entity.po.Endpoint;
import com.aiops.api.service.metadata.dto.ServiceEndpointSearchDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-03 23:56
 **/
@Repository
public interface EndpointMetadataMapper extends MyMapper<Endpoint> {

    List<Endpoint> selectList(ServiceEndpointSearchDto dto);
}

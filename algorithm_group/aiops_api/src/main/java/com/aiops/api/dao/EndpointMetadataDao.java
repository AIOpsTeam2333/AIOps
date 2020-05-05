package com.aiops.api.dao;

import com.aiops.api.entity.po.Endpoint;
import com.aiops.api.service.metadata.dto.ServiceEndpointSearchDto;
import com.aiops.api.mapper.EndpointMetadataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 0:00
 **/
@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EndpointMetadataDao {

    private final EndpointMetadataMapper endpointMetadataMapper;

    public List<Endpoint> queryServiceEndpoint(ServiceEndpointSearchDto dto) {
        return endpointMetadataMapper.selectList(dto);
    }

    public Integer countAll() {
        Endpoint endpoint = new Endpoint();
        return endpointMetadataMapper.selectCount(endpoint);
    }

    public List<Endpoint> queryByIds(List<Integer> ids) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return endpointMetadataMapper.selectByIds(ids.stream()
                .map(a -> a + "")
                .reduce((a, b) -> a + "," + b).orElse(""));
    }
}

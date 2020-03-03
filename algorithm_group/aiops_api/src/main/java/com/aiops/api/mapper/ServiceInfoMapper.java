package com.aiops.api.mapper;

import com.aiops.api.config.MyMapper;
import com.aiops.api.entity.ServiceInfo;
import com.aiops.api.entity.dto.ServiceInfoSearchDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 18:25
 **/
@Repository
public interface ServiceInfoMapper extends MyMapper<ServiceInfo> {

    List<ServiceInfo> selectList(ServiceInfoSearchDto serviceInfoSearchDto);

    Integer selectTotalServiceCount();
}

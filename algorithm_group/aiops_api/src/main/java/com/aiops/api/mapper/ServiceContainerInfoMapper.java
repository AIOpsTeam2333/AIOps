package com.aiops.api.mapper;

import com.aiops.api.config.MyMapper;
import com.aiops.api.entity.ServiceContainerInfo;
import com.aiops.api.entity.dto.ServiceContainerInfoSearchDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 16:18
 **/
@Repository
public interface ServiceContainerInfoMapper extends MyMapper<ServiceContainerInfo> {

    List<ServiceContainerInfo> selectList(ServiceContainerInfoSearchDto dto);
}

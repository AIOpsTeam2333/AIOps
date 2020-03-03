package com.aiops.api.dao;

import com.aiops.api.entity.ServiceInfo;
import com.aiops.api.entity.dto.ServiceInfoSearchDto;
import com.aiops.api.mapper.ServiceInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 18:25
 **/
@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceInfoDao {

    private final ServiceInfoMapper serviceInfoMapper;

    public List<ServiceInfo> selectServiceInfo(ServiceInfoSearchDto dto) {
        try {
            return serviceInfoMapper.selectList(dto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Integer selectTotalServiceCount() {
        return serviceInfoMapper.selectTotalServiceCount();
    }
}

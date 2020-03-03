package com.aiops.api.dao;

import com.aiops.api.entity.ServiceContainerInfo;
import com.aiops.api.entity.dto.ServiceContainerInfoSearchDto;
import com.aiops.api.mapper.ServiceContainerInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 16:58
 **/
@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceContainerInfoDao {

    private final ServiceContainerInfoMapper serviceContainerInfoMapper;

    /**
     * 根据id获取信息
     *
     * @param id
     * @return
     */
    public ServiceContainerInfo getServiceContainerInfoById(String id) {
        return serviceContainerInfoMapper.selectByPrimaryKey(id);
    }

    public List<ServiceContainerInfo> selectServiceContainerInfosByPrototype(ServiceContainerInfoSearchDto dto) {
        try {
            return serviceContainerInfoMapper.selectList(dto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Integer selectCurrentRunningContainerCount() {
        ServiceContainerInfo serviceContainerInfo = new ServiceContainerInfo();
        serviceContainerInfo.setIsInUse(true);

        return serviceContainerInfoMapper.selectCount(serviceContainerInfo);
    }
}

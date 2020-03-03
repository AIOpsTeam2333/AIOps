package com.aiops.api.dao;

import com.aiops.api.entity.Service;
import com.aiops.api.entity.dto.ServiceSearchDto;
import com.aiops.api.mapper.ServiceMapper;
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
public class ServiceDao {

    private final ServiceMapper serviceMapper;

    public List<Service> selectServiceInfo(ServiceSearchDto dto) {
        try {
            return serviceMapper.selectList(dto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}

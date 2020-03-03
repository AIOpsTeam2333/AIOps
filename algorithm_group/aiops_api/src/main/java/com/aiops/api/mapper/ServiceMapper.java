package com.aiops.api.mapper;

import com.aiops.api.config.MyMapper;
import com.aiops.api.entity.Service;
import com.aiops.api.entity.dto.ServiceSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 18:25
 **/
@Mapper
public interface ServiceMapper extends MyMapper<Service> {

    List<Service> selectList(ServiceSearchDto serviceSearchDto);
}

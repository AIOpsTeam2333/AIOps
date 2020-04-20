package com.aiops.processdata.dao.Impl;

import com.aiops.processdata.dao.InstanceRepository;
import com.aiops.processdata.entity.Instance.Attribute_Info;
import com.aiops.processdata.entity.Instance.ServiceInstance_Info;
import com.aiops.processdata.po.instance.AttributePO;
import com.aiops.processdata.po.instance.InstancePO;
import com.aiops.processdata.po.instance.ServiceInstancePO;
import com.aiops.processdata.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/10 18:02
 */
@Repository
public class InstanceRepositoryImpl implements InstanceRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Service service;

    @Override
    public InstancePO insertInstance(ServiceInstance_Info serviceInstance_info) {
        //判断是否已存在，暂定无原id不做判断

        Integer serviceId = getServiceId(serviceInstance_info.getId());
        serviceInstance_info.setId(serviceId.toString());//设置为实际数据库中serviceId

        Integer instanceId = insertServiceInstanceAndReturnId(serviceInstance_info);
        List<Attribute_Info> attribute_infos = serviceInstance_info.getAttributes();
        List<AttributePO> attributePOS = new ArrayList<>();
        for (Attribute_Info attribute_info : attribute_infos) {
            AttributePO attributePO = insertAttribute(attribute_info, instanceId);
            attributePOS.add(attributePO);
        }
        String uuid = serviceInstance_info.getName();//待修改，uuid未明确***************当下设置未instance.name*******************
        ServiceInstancePO serviceInstancePO = new ServiceInstancePO(instanceId, serviceId, serviceInstance_info.getName(), uuid, serviceInstance_info.getLanguage());
        return new InstancePO(serviceInstancePO, attributePOS);
    }

    /**
     * 得到实际serviceId
     *
     * @param serviceId
     * @return 数据库中serviceId
     */
    private Integer getServiceId(String serviceId) {
        return this.service.findById(serviceId);
    }


    /**
     * 插入metadata_service_instance表
     *
     * @param serviceInstance_info
     * @return 返回service_instance_id
     */
    private Integer insertServiceInstanceAndReturnId(ServiceInstance_Info serviceInstance_info) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("metadata_service_instance");
        jdbcInsert.setGeneratedKeyName("service_instance_id");
        Map<String, Object> args = new HashMap<>();
        args.put("service_id", serviceInstance_info.getId());
        args.put("name", serviceInstance_info.getName());
        args.put("instance_uuid", serviceInstance_info.getName());//待修改,uuid属性不明确*************当下设置我为serviceInstance.name************************
        args.put("language", serviceInstance_info.getLanguage());
        Integer instanceId = jdbcInsert.executeAndReturnKey(args).intValue();
        return instanceId;
    }

    /**
     * 插入metadata_service_instance_attribute
     *
     * @param attribute_info
     * @param instanceId
     * @return
     */
    private AttributePO insertAttribute(Attribute_Info attribute_info, Integer instanceId) {
        Integer attributeId = insertAttributeAndReturnId(attribute_info, instanceId);
        return new AttributePO(attributeId, instanceId, attribute_info.getName(), attribute_info.getValue());
    }

    /**
     * 插入metadata_service_instance_attribute并返回id
     *
     * @param attribute_info
     * @param instanceId
     * @return attributeid
     */
    private Integer insertAttributeAndReturnId(Attribute_Info attribute_info, Integer instanceId) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("metadata_service_instance_attribute");
        jdbcInsert.setGeneratedKeyName("service_instance_attribute_id");
        Map<String, Object> args = new HashMap<>();
        args.put("service_instance_id", instanceId);
        args.put("name", attribute_info.getName());
        args.put("value", attribute_info.getValue());
        Integer attributeId = jdbcInsert.executeAndReturnKey(args).intValue();
        return attributeId;
    }


}

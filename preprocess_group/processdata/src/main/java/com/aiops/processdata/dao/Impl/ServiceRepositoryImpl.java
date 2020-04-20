package com.aiops.processdata.dao.Impl;

import com.aiops.processdata.dao.ServiceRepository;
import com.aiops.processdata.entity.service.Service_Info;
import com.aiops.processdata.po.service.ServicePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/7 21:36
 */
@Repository
public class ServiceRepositoryImpl implements ServiceRepository {

    private static final String SELECT_SERVICE = "select n.node_id as id,n.name as name,n.description as description,n.add_timestamp as addTime,n.node_type as type from metadata_service_node n inner join process_metadata_service p on n.node_id=p.service_id";
    private static final String SELECT_SERVICE_BY_ID = SELECT_SERVICE + " where p.pre_id=?";
    private static final String SELECT_SERVICE_COUNT = "select count(*) from process_metadata_service s";
    private static final String SELECT_SERVICE_COUNT_BY_ID = SELECT_SERVICE_COUNT + " where s.pre_id=?";
    private static final String SELECT_SERVICE_BY_NAME = SELECT_SERVICE + " where n.name=?";
    private static final String SELECT_SERVICE_COUNT_BY_NAME = "select count(*) from metadata_service_node n where n.name=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ServicePO insertService(Service_Info service_info) {
        ServicePO servicePO = findById(service_info.getId());
        if (servicePO != null) return servicePO;//已存在

        //插入meta_service_node表得到node_id
        Integer serviceId = insertServiceAndReturnId(service_info);
        //注册原id与node_id映射关系
        insertProcessService(serviceId, service_info);
        //注册service_id
        insertServiceId(serviceId);
        return new ServicePO(serviceId, service_info.getName(), null, null, null);
    }

    @Override
    public ServicePO findById(String pre_id) {
        Integer count = jdbcTemplate.queryForObject(SELECT_SERVICE_COUNT_BY_ID, Integer.class, pre_id);
        if (count == 0) return null;//已存在

        RowMapper<ServicePO> rowMapper = new BeanPropertyRowMapper<>(ServicePO.class);
        List<ServicePO> servicePOList=jdbcTemplate.query(SELECT_SERVICE_BY_ID, rowMapper, pre_id);
        return servicePOList.size()==0?null:servicePOList.get(0);
    }

    @Override
    public ServicePO findByName(String name) {
        Integer count = jdbcTemplate.queryForObject(SELECT_SERVICE_COUNT_BY_NAME, Integer.class, name);
        if (count == 0) return null;
        RowMapper<ServicePO> rowMapper = new BeanPropertyRowMapper<>(ServicePO.class);
        List<ServicePO> servicePOList=jdbcTemplate.query(SELECT_SERVICE_BY_NAME, rowMapper, name);
        return servicePOList.size()==0?null:servicePOList.get(0);
    }


    /**
     * 插入metadata_service_node表
     *
     * @param service_info
     * @return node_id
     */
    private Integer insertServiceAndReturnId(Service_Info service_info) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("metadata_service_node");
        jdbcInsert.setGeneratedKeyName("node_id");
        Map<String, Object> args = new HashMap<>();
        args.put("name", service_info.getName());
        args.put("add_timestamp", new Date());
        Integer serviceId = jdbcInsert.executeAndReturnKey(args).intValue();
        return serviceId;
    }

    /**
     * 插入process_metadata_service表
     *
     * @param serviceId    node_id
     * @param service_info 包含原id的service_info
     */
    private int insertProcessService(Integer serviceId, Service_Info service_info) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("process_metadata_service");
        Map<String, Object> args = new HashMap<>();
        args.put("service_id", serviceId);
        args.put("pre_id", service_info.getId());
        return jdbcInsert.execute(args);
    }

    /**
     * 插入metadata_service
     *
     * @param serviceId node_id
     */
    private int insertServiceId(Integer serviceId) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("metadata_service");
        Map<String, Object> args = new HashMap<>();
        args.put("service_id", serviceId);
        return jdbcInsert.execute(args);
    }

}

package com.aiops.processdata.dao.Impl;

import com.aiops.processdata.dao.EndpointRepository;
import com.aiops.processdata.entity.endpoint.Endpoint_Info;
import com.aiops.processdata.po.endpoint.EndpointPO;
import com.aiops.processdata.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/9 21:16
 */
@Repository
public class EndpointRepositoryImpl implements EndpointRepository {

    private static final String SELECT_Endpoint = "select e.service_endpoint_id as id, e.service_id as serviceId,e.name as name from metadata_service_endpoint e inner join process_metadata_endpoint p on e.service_endpoint_id=p.service_endpoint_id";
    private static final String SELECT_Endpoint_BY_ID = SELECT_Endpoint + " where p.pre_id=?";
    private static final String SELECT_Endpoint_BY_Name = SELECT_Endpoint + " where e.name=?";
    private static final String SELECT_ENDPOINT_COUNT_BY_ID = "select count(*) from process_metadata_endpoint e where e.pre_id=?";
    private static final String SELECT_ENDPOINT_COUNT_BY_Name = "select count(*) from metadata_service_endpoint e where e.name=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Service service;

    @Override
    public EndpointPO insertEndpoint(Endpoint_Info endpoint_info) {
        EndpointPO endpointPO = findById(endpoint_info.getId());
        if (endpointPO != null) return endpointPO;

        Integer serviceId = getServiceId(endpoint_info.getServiceId());
        String pre_id = endpoint_info.getId();

        endpoint_info.setServiceId(serviceId.toString());//设置为数据库中实际node_id
        Integer endpointId = insertEndpointAndReturnId(endpoint_info);
        //注册原id与service_endpoint_id映射关系
        insertProcessEndpoint(endpointId, pre_id);
        return new EndpointPO(endpointId, serviceId, endpoint_info.getName());
    }

    @Override
    public EndpointPO findById(String pre_id) {
        Integer count = jdbcTemplate.queryForObject(SELECT_ENDPOINT_COUNT_BY_ID, Integer.class, pre_id);
        if (count == 0) return null;//已存在
        List<EndpointPO> endpointPOList = jdbcTemplate.query(SELECT_Endpoint_BY_ID, new BeanPropertyRowMapper<>(EndpointPO.class), pre_id);
        return endpointPOList.size()==0?null:endpointPOList.get(0);//返回第一个满足对象
    }

    @Override
    public EndpointPO findByName(String name) {
        Integer count = jdbcTemplate.queryForObject(SELECT_ENDPOINT_COUNT_BY_Name, Integer.class, name);
        if (count == 0) return null;
        RowMapper<EndpointPO> rowMapper = new BeanPropertyRowMapper<>(EndpointPO.class);
        List<EndpointPO> endpointPOList = jdbcTemplate.query(SELECT_Endpoint_BY_Name, rowMapper, name);
        return endpointPOList.size()==0?null:endpointPOList.get(0);//返回第一个满足对象
    }


    /**
     * 插入metadata_service_endpoint表
     *
     * @param endpoint_info
     * @return service_endpoint_id
     */
    private Integer insertEndpointAndReturnId(Endpoint_Info endpoint_info) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("metadata_service_endpoint");
        jdbcInsert.setGeneratedKeyName("service_endpoint_id");
        Map<String, Object> args = new HashMap<>();
        args.put("service_id", endpoint_info.getServiceId());
        args.put("name", endpoint_info.getName());
        Integer endpointId = jdbcInsert.executeAndReturnKey(args).intValue();
        return endpointId;
    }

    /**
     * 将endpoint_info中原有的serviceId换成数据库中实际的node_id
     *
     * @param serviceId
     * @return node_id
     */
    private Integer getServiceId(String serviceId) {
        return service.findById(serviceId);
    }

    /**
     * 插入process_metadata_endpoint表
     *
     * @param endpointId 实际service_endpoint_id
     * @param pre_id     endpoint_info中serviceId
     * @return 是否插入成功
     */
    private int insertProcessEndpoint(Integer endpointId, String pre_id) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("process_metadata_endpoint");
        Map<String, Object> args = new HashMap<>();
        args.put("service_endpoint_id", endpointId);
        args.put("pre_id", pre_id);
        return jdbcInsert.execute(args);
    }
}

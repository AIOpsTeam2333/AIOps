package com.aiops.processdata.dao.Impl;

import com.aiops.processdata.dao.LogRepository;
import com.aiops.processdata.entity.span.Log_Info;
import com.aiops.processdata.entity.span.Span_Info;
import com.aiops.processdata.po.span.LogPO;
import com.aiops.processdata.service.Span;
import com.aiops.processdata.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 18:08
 */
@Repository
public class LogRepositoryImpl implements LogRepository {
    private static final String SELECT_LOG_BY_PRESPANID = "select ld.log_entity_id as entityId,l.time as time,l.span_id as spanId,ld.`data` as `data` from trace_span s inner join trace_log_entity l on s.id=l.span_id inner join trace_log_entity_data ld on l.log_entity_id=ld.log_entity_id where s.span_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Span span;


    @Override
    public List<LogPO> insertLog(Span_Info span_info) {
        List<Log_Info> log_infoList = span_info.getLogs();
        List<LogPO> logPOList = new ArrayList<>();
        Integer spanId = span.findById(span_info.getTraceId() + "/" + span_info.getSegmentId() + "/" + span_info.getSpanId());
        if (spanId == -1) return null;//不存在该Span
        for (Log_Info log_info : log_infoList) {
            LogPO logPO = insertLogEntity(spanId, log_info);
            insertLogEntityData(logPO);
            logPOList.add(logPO);
        }
        return logPOList;
    }


    @Override
    public List<LogPO> findLogsBySpanId(String pre_id) {
        RowMapper<LogPO> rowMapper = new BeanPropertyRowMapper<>(LogPO.class);
        List<LogPO> logPOList = jdbcTemplate.query(SELECT_LOG_BY_PRESPANID, rowMapper, pre_id);
        return logPOList;
    }

    /**
     * 插入trace_log_entity_data表
     *
     * @param logPO
     * @return
     */
    private int insertLogEntityData(LogPO logPO) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("trace_log_entity_data");
        Map<String, Object> args = new HashMap<>();
        args.put("log_entity_id", logPO.getEntityId());
        args.put("data", logPO.getData());
        return jdbcInsert.execute(args);
    }

    /**
     * 插入trace_log_entity表
     *
     * @param spanId
     * @param log_info
     * @return
     */
    private LogPO insertLogEntity(Integer spanId, Log_Info log_info) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("trace_log_entity");
        jdbcInsert.setGeneratedKeyName("log_entity_id");
        Map<String, Object> args = new HashMap<>();
        Timestamp time = MyUtils.stampToDate(log_info.getTime());
        args.put("time", time);
        args.put("span_id", spanId);
        Integer entityId = jdbcInsert.executeAndReturnKey(args).intValue();
        String data = MyUtils.beanToJson(log_info.getData());
        return new LogPO(entityId, time, spanId, data);
    }
}

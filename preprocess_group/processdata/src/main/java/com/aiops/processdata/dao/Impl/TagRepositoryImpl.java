package com.aiops.processdata.dao.Impl;

import com.aiops.processdata.dao.TagRepository;
import com.aiops.processdata.entity.span.Span_Info;
import com.aiops.processdata.entity.span.Tag_Info;
import com.aiops.processdata.po.span.KVPO;
import com.aiops.processdata.po.span.TagPO;
import com.aiops.processdata.service.Span;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 18:09
 */
@Repository
public class TagRepositoryImpl implements TagRepository {
    private static final String SELECT_KVPO = "select kv.key_value_id as kvId,kv.key, kv.value from trace_key_value kv";
    private static final String SELECT_KVPO_BY_KV = SELECT_KVPO + " where kv.key=? and kv.value=?";
    private static final String INSERT_KV = "insert into trace_key_value(`key`,`value`) VALUES (?,?)";
    private static final String SELECT_KVPO_BY_PRESPANID = "select kv.key_value_id as kvId,kv.key as `key`,kv.`value` as `value` from trace_span s inner join trace_span_tags ts on s.id=ts.span_id inner join trace_key_value kv on kv.key_value_id=ts.key_value_id where s.span_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Span span;

    @Override
    public TagPO insertTag(Span_Info span_info) {
        List<Tag_Info> tag_infoList = span_info.getTags();
        List<KVPO> kvpoList = new ArrayList<>();
        String spanPreId = span_info.getTraceId() + "/" + span_info.getSegmentId() + "/" + span_info.getSpanId();
        Integer spanId = span.findById(spanPreId);
        if (spanId == -1) return null;//不存在该Span
        for (Tag_Info tag_info : tag_infoList) {
            KVPO kvpo = findKVPO(tag_info);
            if (kvpo == null) {//不存在，则在kv表注册
                kvpo = insertKVPO(tag_info);
            }
            insertTagPO(spanId, kvpo.getKvId());
            kvpoList.add(kvpo);
        }
        return new TagPO(spanId, kvpoList);
    }

    @Override
    public TagPO findTagBySpanId(String pre_id) {
        Integer spanId = span.findById(pre_id);
        if (spanId == -1) return null;
        RowMapper<KVPO> rowMapper = new BeanPropertyRowMapper<>(KVPO.class);
        List<KVPO> kvpoList = jdbcTemplate.query(SELECT_KVPO_BY_PRESPANID, rowMapper, pre_id);
        return new TagPO(spanId, kvpoList);
    }


    /**
     * 判断kv值是否已经在trace_key_value表存在
     * 存在返回KVPO,否则返回null
     *
     * @param tag_info
     * @return
     */
    private KVPO findKVPO(Tag_Info tag_info) {
        RowMapper<KVPO> rowMapper = new BeanPropertyRowMapper<>(KVPO.class);
        List<KVPO> kvpoList = jdbcTemplate.query(SELECT_KVPO_BY_KV, rowMapper, tag_info.getKey(), tag_info.getValue());
        return kvpoList.size() == 0 ? null : kvpoList.get(0);
    }


    /**
     * 插入trace_span_tags表
     *
     * @param spanId
     * @param kvId
     */
    private int insertTagPO(Integer spanId, Integer kvId) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("trace_span_tags");
        Map<String, Object> args = new HashMap<>();
        args.put("span_id", spanId);
        args.put("key_value_id", kvId);
        return jdbcInsert.execute(args);
    }

    /**
     * 插入trace_key_value表
     *
     * @param tag_info
     * @return
     */
    private KVPO insertKVPO(Tag_Info tag_info) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement ps = jdbcTemplate.getDataSource()
                                .getConnection().prepareStatement(INSERT_KV, new String[]{"key", "value"});
                        ps.setString(1, tag_info.getKey());
                        ps.setString(2, tag_info.getValue());
                        return ps;
                    }
                }, keyHolder);
        return new KVPO(keyHolder.getKey().intValue(), tag_info.getKey(), tag_info.getValue());
    }
}

package com.aiops.processdata.dao.Impl;

import com.aiops.processdata.dao.RefRepository;
import com.aiops.processdata.entity.span.Ref_Info;
import com.aiops.processdata.entity.span.Span_Info;
import com.aiops.processdata.po.span.RefPO;
import com.aiops.processdata.service.Span;
import com.aiops.processdata.service.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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
public class RefRepositoryImpl implements RefRepository {

    private static final String SELECT_REF_BY_SPANID_AND_TYPE = "select r.ref_id as refId,r.parent_segment_id as parentSegmentId,r.parent_span_id as parentSpanId,r.ref_type as refType from trace_ref r where r.parent_span_id=? and r.ref_type=?";
    private static final String SELECT_COUNT_REFS_BY_CONTENT = "select count(*) from trace_span_refs r where r.span_id=? and r.ref_id=?";
    private static final String SELECT_REF_BY_PRESPANID = "select r.ref_id as refId,r.parent_segment_id as parentSegmentId,r.parent_span_id as parentSpanId,r.ref_type as refType from trace_span s inner join trace_span_refs rs on s.id=rs.span_id inner join trace_ref r on r.ref_id=rs.ref_id where s.span_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Trace trace;

    @Autowired
    private Span span;

    @Override
    public List<RefPO> insertRef(Span_Info span_info) {
        List<Ref_Info> ref_infoList = span_info.getRefs();
        List<RefPO> refPOList = new ArrayList<>();
        Integer spanId = span.findById(span_info.getTraceId() + "/" + span_info.getSegmentId() + "/" + span_info.getSpanId());
        for (Ref_Info ref_info : ref_infoList) {
            RefPO refPO = insertRefPO(ref_info);
            if (refPO != null) {
                insertSpanRefs(spanId, refPO.getRefId());
                refPOList.add(refPO);
            }
        }
        return refPOList;
    }

    @Override
    public List<RefPO> findRefsBySpanId(String pre_id) {
        RowMapper<RefPO> rowMapper = new BeanPropertyRowMapper<>(RefPO.class);
        List<RefPO> refPOList = jdbcTemplate.query(SELECT_REF_BY_PRESPANID, rowMapper, pre_id);
        return refPOList;
    }

    @Override
    public RefPO findRefByConent(Ref_Info ref_info) {
        Integer parentSpanId = span.findById(ref_info.getTraceId() + "/" + ref_info.getParentSegmentId() + "/" + ref_info.getParentSpanId());
        if (parentSpanId == -1) return null;//不存在父span返回null

        RowMapper<RefPO> rowMapper = new BeanPropertyRowMapper<>(RefPO.class);
        List<RefPO> refPOList = jdbcTemplate.query(SELECT_REF_BY_SPANID_AND_TYPE, rowMapper, parentSpanId, ref_info.getType());
        return refPOList.size() == 0 ? null : refPOList.get(0);
    }

    /**
     * 插入trace_ref表
     *
     * @param ref_info
     * @return
     */
    private RefPO insertRefPO(Ref_Info ref_info) {
        //存在性检验
        RefPO refPO = findRefByConent(ref_info);
        if (refPO != null) return refPO;//已存在直接返回

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("trace_ref");
        jdbcInsert.setGeneratedKeyName("ref_id");
        Map<String, Object> args = new HashMap<>();
        Integer parentSegmentId = trace.findSegmentById(ref_info.getTraceId() + "/" + ref_info.getParentSegmentId());
        args.put("parent_segment_id", parentSegmentId);

        Integer parentSpanId = ref_info.getParentSpanId();
        if (parentSpanId != -1) {
            parentSpanId = span.findById(ref_info.getTraceId() + "/" + ref_info.getParentSegmentId() + "/" + ref_info.getParentSpanId());
            if (parentSpanId == -1) return null;//坏节点，缺失
        }

        args.put("parent_span_id", parentSpanId);
        args.put("ref_type", ref_info.getType());

        Integer refId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new RefPO(refId, parentSegmentId, parentSpanId, ref_info.getType());
    }

    /**
     * 插入trace_span_refs表
     *
     * @param spanId
     * @param refId
     */
    private int insertSpanRefs(Integer spanId, Integer refId) {
        Integer count = findRefsByConent(spanId, refId);
        if (count != 0) return -1;//已存在，不插入

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("trace_span_refs");
        Map<String, Object> args = new HashMap<>();
        args.put("span_id", spanId);
        args.put("ref_id", refId);
        return jdbcInsert.execute(args);
    }

    /**
     * 检查trace_span_refs表，有无该条记录
     *
     * @param spanId
     * @param refId
     * @return 有返回数量，无返回0
     */
    private Integer findRefsByConent(Integer spanId, Integer refId) {
        Integer count = jdbcTemplate.queryForObject(SELECT_COUNT_REFS_BY_CONTENT, Integer.class, spanId, refId);
        return count;
    }
}

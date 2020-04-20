package com.aiops.processdata.dao.Impl;

import com.aiops.processdata.dao.LogRepository;
import com.aiops.processdata.dao.RefRepository;
import com.aiops.processdata.dao.SpanRepository;
import com.aiops.processdata.dao.TagRepository;
import com.aiops.processdata.entity.span.Span_Info;
import com.aiops.processdata.po.span.*;
import com.aiops.processdata.service.Endpoint;
import com.aiops.processdata.service.Trace;
import com.aiops.processdata.utils.MyUtils;
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
 * @date 2020/4/15 18:03
 */
@Repository
public class SpanRepositoryImpl implements SpanRepository {
    private static final String SELECT_SPAN = "select s.id as id,s.span_id as spanId,s.segment_id as segmentId,s.trace_id as traceId,s.parent_span_id as parentSpanId,s.service_code as serviceCode,s.start_time as startTime,s.end_time as endTime,s.endpoint_id as endpointId,s.instance_id as instanceId,s.type as type,s.peer as peer,s.component as component,s.is_error as isError,s.layer as layer from trace_span s";
    private static final String SELECT_SPAN_BY_ID = SELECT_SPAN + " where s.span_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RefRepository refRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private Trace trace;

    @Autowired
    private Endpoint endpoint;

    @Override
    public SpanPO insertSpan(Span_Info span_info) {
        TraceSpanPO traceSpanPO = insertTraceSpan(span_info);
        List<RefPO> refPOList = refRepository.insertRef(span_info);
        TagPO tagPO = tagRepository.insertTag(span_info);
        List<LogPO> logPOList = logRepository.insertLog(span_info);
        return new SpanPO(traceSpanPO, refPOList, tagPO, logPOList);
    }

    @Override
    public TraceSpanPO findTraceSpanById(String pre_id) {
        RowMapper<TraceSpanPO> rowMapper = new BeanPropertyRowMapper<>(TraceSpanPO.class);
        List<TraceSpanPO> traceSpanPOList = jdbcTemplate.query(SELECT_SPAN_BY_ID, rowMapper, pre_id);
        return traceSpanPOList.size() == 0 ? null : traceSpanPOList.get(0);
    }

    @Override
    public SpanPO findSpanPOById(String pre_id) {
        TraceSpanPO traceSpanPO = findTraceSpanById(pre_id);
        if (traceSpanPO == null) return null;
        List<RefPO> refPOList = refRepository.findRefsBySpanId(pre_id);
        TagPO tagPO = tagRepository.findTagBySpanId(pre_id);
        List<LogPO> logPOList = logRepository.findLogsBySpanId(pre_id);
        return new SpanPO(traceSpanPO, refPOList, tagPO, logPOList);
    }

    /**
     * 插入trace_span表
     *
     * @param span_info
     * @return
     */
    private TraceSpanPO insertTraceSpan(Span_Info span_info) {
        TraceSpanPO traceSpanPO = findTraceSpanById(span_info.getTraceId() + "/" + span_info.getSegmentId() + "/" + span_info.getSpanId());
        if (traceSpanPO != null) return traceSpanPO;//已存在

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("trace_span");
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<>();
        String spanId = span_info.getTraceId() + "/" + span_info.getSegmentId() + "/" + span_info.getSpanId();
        args.put("span_id", spanId);
        Integer segmentId = trace.findSegmentById(span_info.getTraceId() + "/" + span_info.getSegmentId());
        args.put("segment_id", segmentId);
        Integer traceId = trace.findTraceById(span_info.getTraceId());
        args.put("trace_id", traceId);
        Integer parentId = span_info.getParentSpanId();
        if (parentId != -1) {
            TraceSpanPO tempTraceSpanPO = findTraceSpanById(span_info.getTraceId() + "/" + span_info.getSegmentId() + "/" + span_info.getParentSpanId());
            if (tempTraceSpanPO == null) return null;//找不到父节点 认为是坏节点
            parentId = tempTraceSpanPO.getId();
        }
        args.put("parent_span_id", parentId);
        args.put("service_code", span_info.getServiceCode());
        args.put("start_time", MyUtils.stampToDate(span_info.getStartTime()));
        args.put("end_time", MyUtils.stampToDate(span_info.getEndTime()));
        Integer endpointId = endpoint.findByName(span_info.getEndpointName());
        args.put("endpoint_id", endpointId);

        Integer instanceId = 2;//待修改，默认是2
        args.put("instance_id", instanceId);

        args.put("type", span_info.getType());
        args.put("peer", span_info.getPeer());
        args.put("component", span_info.getComponent());
        args.put("is_error", span_info.isError());
        args.put("layer", span_info.getLayer());
        Integer id = jdbcInsert.executeAndReturnKey(args).intValue();
        return new TraceSpanPO(id, spanId, segmentId, traceId, parentId, span_info.getServiceCode(), MyUtils.stampToDate(span_info.getStartTime()), MyUtils.stampToDate(span_info.getEndTime()), endpointId, instanceId, span_info.getType(), span_info.getPeer(), span_info.getComponent(), span_info.isError(), span_info.getLayer());
    }


}

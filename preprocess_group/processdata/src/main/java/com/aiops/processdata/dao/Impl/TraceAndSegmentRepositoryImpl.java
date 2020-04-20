package com.aiops.processdata.dao.Impl;

import com.aiops.processdata.dao.TraceAndSegmentRepository;
import com.aiops.processdata.entity.trace.Trace_Info;
import com.aiops.processdata.po.trace.SegmentPO;
import com.aiops.processdata.po.trace.TracePO;
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
 * @date 2020/4/14 22:08
 */
@Repository
public class TraceAndSegmentRepositoryImpl implements TraceAndSegmentRepository {

    private static final String SELECT_TRACE = "select p.trace_id as traceId from process_trace_trace p";
    private static final String SELECT_TRACE_BY_ID = SELECT_TRACE + " where p.pre_id=?";
    private static final String SELECT_SEGMENT = "select p.segment_id as segmentId,t.trace_id as traceId from process_trace_segment p inner join trace_segment t on p.segment_id=t.segment_id";
    private static final String SELECT_SEGMENT_BY_ID = SELECT_SEGMENT + " where p.pre_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public TracePO insertTrace(Trace_Info trace_info) {
        TracePO tracePO = findTraceById(trace_info.getTraceIds().get(0));
        if (tracePO != null) return tracePO;//已存在

        Integer traceId = insertTraceAndReturnId(trace_info);
        insertProcessTrace(traceId, trace_info);
        return new TracePO(traceId);
    }

    @Override
    public SegmentPO insertSegment(Trace_Info trace_info) {
        if (trace_info.getTraceIds().size() == 0 || trace_info.getSegmentId() == null) return null;

        String segmentPreId = trace_info.getTraceIds().get(0) + "/" + trace_info.getSegmentId();
        SegmentPO segmentPO = findSegmentById(segmentPreId);
        if (segmentPO != null) return segmentPO;

        //替换实际traceId
        TracePO tracePO = findTraceById(trace_info.getTraceIds().get(0));
        if (tracePO == null) return null;
        List<String> traceIds = new ArrayList<>();
        traceIds.add(tracePO.getTraceId() + "");
        Trace_Info traceInfo = new Trace_Info(trace_info.getSegmentId(), trace_info.getEndpointNames(),
                trace_info.getDuration(), trace_info.getStart(), trace_info.isError(), traceIds);

        Integer segmentId = insertSegmentAndReturnId(traceInfo);
        insertProcessSegment(segmentId, trace_info);
        return new SegmentPO(segmentId, tracePO.getTraceId());
    }


    @Override
    public TracePO findTraceById(String pre_id) {
        RowMapper<TracePO> rowMapper = new BeanPropertyRowMapper<>(TracePO.class);
        List<TracePO> tracePOList = jdbcTemplate.query(SELECT_TRACE_BY_ID, rowMapper, pre_id);
        return tracePOList.size() == 0 ? null : tracePOList.get(0);
    }

    @Override
    public SegmentPO findSegmentById(String pre_id) {
        RowMapper<SegmentPO> rowMapper = new BeanPropertyRowMapper<>(SegmentPO.class);
        List<SegmentPO> segmentPOList = jdbcTemplate.query(SELECT_SEGMENT_BY_ID, rowMapper, pre_id);
        return segmentPOList.size() == 0 ? null : segmentPOList.get(0);
    }

    /**
     * 插入trace_trace表
     *
     * @param trace_info
     * @return 返回trace_id
     */
    private Integer insertTraceAndReturnId(Trace_Info trace_info) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("trace_trace");
        jdbcInsert.setGeneratedKeyName("trace_id");
        Map<String, Object> args = new HashMap<>();
        Integer traceId = jdbcInsert.executeAndReturnKey(args).intValue();
        return traceId;
    }

    /**
     * 插入process_trace_trace表
     *
     * @param traceId
     * @param trace_info
     * @return
     */
    private int insertProcessTrace(Integer traceId, Trace_Info trace_info) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("process_trace_trace");
        Map<String, Object> args = new HashMap<>();
        args.put("trace_id", traceId);
        args.put("pre_id", trace_info.getTraceIds().get(0));
        return jdbcInsert.execute(args);
    }

    /**
     * 插入trace_segment表
     *
     * @param trace_info
     * @return segment_id
     */
    private Integer insertSegmentAndReturnId(Trace_Info trace_info) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("trace_segment");
        jdbcInsert.setGeneratedKeyName("segment_id");
        Map<String, Object> args = new HashMap<>();
        args.put("trace_id", trace_info.getTraceIds().get(0));
        Integer segmentId = jdbcInsert.executeAndReturnKey(args).intValue();
        return segmentId;
    }

    /**
     * 插入process_trace_segment表
     *
     * @param segmentId
     * @param trace_info
     */
    private int insertProcessSegment(Integer segmentId, Trace_Info trace_info) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("process_trace_segment");
        Map<String, Object> args = new HashMap<>();
        args.put("segment_id", segmentId);
        args.put("pre_id", trace_info.getTraceIds().get(0) + "/" + trace_info.getSegmentId());
        return jdbcInsert.execute(args);
    }
}

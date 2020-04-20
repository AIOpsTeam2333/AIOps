package com.aiops.processdata.dao;

import com.aiops.processdata.entity.trace.Trace_Info;
import com.aiops.processdata.po.trace.SegmentPO;
import com.aiops.processdata.po.trace.TracePO;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/14 21:54
 */
public interface TraceAndSegmentRepository {
    /**
     * 插入trace数据
     *
     * @param trace_info
     * @return trace对象
     */
    TracePO insertTrace(Trace_Info trace_info);

    /**
     * 插入segement数据
     *
     * @param trace_info
     * @return segement对象
     */
    SegmentPO insertSegment(Trace_Info trace_info);

    /**
     * 通过原id查找到数据库对象
     *
     * @param pre_id
     * @return TracePO
     */
    TracePO findTraceById(String pre_id);

    /**
     * 通过原id查找到数据库对象
     *
     * @param pre_id
     * @return SegmentPO
     */
    SegmentPO findSegmentById(String pre_id);
}

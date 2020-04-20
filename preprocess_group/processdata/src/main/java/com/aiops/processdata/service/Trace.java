package com.aiops.processdata.service;

import com.aiops.processdata.entity.trace.Trace_Data;


/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/14 23:57
 */
public interface Trace {
    /**
     * 存trace信息和segment信息
     *
     * @param trace_data
     */
    void save(Trace_Data trace_data);

    /**
     * 找到旧id对应数据库新id
     *
     * @param pre_id
     * @return
     */
    Integer findTraceById(String pre_id);

    /**
     * 找到旧id对应数据库新id
     *
     * @param pre_id
     * @return
     */
    Integer findSegmentById(String pre_id);
}

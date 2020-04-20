package com.aiops.processdata.dao;

import com.aiops.processdata.entity.span.Span_Info;
import com.aiops.processdata.po.span.LogPO;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 18:00
 */
public interface LogRepository {

    /**
     * 插入span中log信息
     *
     * @param span_info
     * @return
     */
    List<LogPO> insertLog(Span_Info span_info);

    /**
     * 根据原spanId值查找log信息列表
     *
     * @param pre_id
     * @return
     */
    List<LogPO> findLogsBySpanId(String pre_id);
}

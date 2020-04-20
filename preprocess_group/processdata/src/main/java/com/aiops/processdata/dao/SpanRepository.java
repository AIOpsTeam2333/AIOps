package com.aiops.processdata.dao;

import com.aiops.processdata.entity.span.Span_Info;
import com.aiops.processdata.po.span.SpanPO;
import com.aiops.processdata.po.span.TraceSpanPO;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 17:40
 */
public interface SpanRepository {
    /**
     * 插入span数据
     *
     * @param span_info
     * @return
     */
    SpanPO insertSpan(Span_Info span_info);

    /**
     * 根据原id查找数据库表信息
     *
     * @param pre_id
     * @return TraceSpanPO对象
     */
    TraceSpanPO findTraceSpanById(String pre_id);

    /**
     * 根据原id级联查找相关数据表
     *
     * @param pre_id
     * @return SpanPO对象
     */
    SpanPO findSpanPOById(String pre_id);

}

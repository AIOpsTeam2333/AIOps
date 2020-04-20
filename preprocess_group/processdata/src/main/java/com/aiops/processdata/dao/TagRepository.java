package com.aiops.processdata.dao;

import com.aiops.processdata.entity.span.Span_Info;
import com.aiops.processdata.po.span.TagPO;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 17:55
 */
public interface TagRepository {

    /**
     * 插入span中Tag信息
     *
     * @param span_info
     * @return
     */
    TagPO insertTag(Span_Info span_info);

    /**
     * 根据原spanId信息找到对应的TagPO
     *
     * @param pre_id
     * @return
     */
    TagPO findTagBySpanId(String pre_id);
}

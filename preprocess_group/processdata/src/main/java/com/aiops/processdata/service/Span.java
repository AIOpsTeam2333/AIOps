package com.aiops.processdata.service;

import com.aiops.processdata.entity.span.Span_Data;
import com.aiops.processdata.po.span.SpanPO;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 22:20
 */
public interface Span {
    /**
     * 存储所有的span信息
     *
     * @param span_data
     * @return 数据库对象列表
     */
    List<SpanPO> save(Span_Data span_data);

    /**
     * 根据原id查找对应的新id
     *
     * @param pre_id
     * @return 数据库对象的实际id
     */
    Integer findById(String pre_id);
}

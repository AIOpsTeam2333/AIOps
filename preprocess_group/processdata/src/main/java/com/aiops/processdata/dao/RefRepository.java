package com.aiops.processdata.dao;

import com.aiops.processdata.entity.span.Ref_Info;
import com.aiops.processdata.entity.span.Span_Info;
import com.aiops.processdata.po.span.RefPO;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 17:52
 */
public interface RefRepository {

    /***
     * 插入所有span中Ref信息
     * @param span_info
     * @return
     */
    List<RefPO> insertRef(Span_Info span_info);

    /**
     * 根据原parentSpanId找到所有Ref信息
     *
     * @param pre_id
     * @return Ref信息列表
     */
    List<RefPO> findRefsBySpanId(String pre_id);

    /**
     * 根据ref_info信息找到所有RefPO
     *
     * @param ref_info
     * @return RefPO
     */
    RefPO findRefByConent(Ref_Info ref_info);
}

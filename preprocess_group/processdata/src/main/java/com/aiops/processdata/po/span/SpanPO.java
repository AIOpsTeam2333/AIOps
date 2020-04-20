package com.aiops.processdata.po.span;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 17:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpanPO {
    private TraceSpanPO traceSpanPO;
    private List<RefPO> refPOList;
    private TagPO tagPOList;
    private List<LogPO> logPOList;

    @Override
    public boolean equals(Object o) {

        SpanPO spanPO = (SpanPO) o;
        if(this.traceSpanPO.getId()==spanPO.traceSpanPO.getId()) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return this.traceSpanPO.getId();
    }
}

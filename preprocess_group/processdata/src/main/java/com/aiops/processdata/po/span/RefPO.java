package com.aiops.processdata.po.span;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 15:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefPO {
    private Integer refId;
    private Integer parentSegmentId;
    private Integer parentSpanId;
    private String refType;
}

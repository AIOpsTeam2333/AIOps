package com.aiops.processdata.po.trace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/14 21:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SegmentPO {
    private Integer segmentId;
    private Integer traceId;
}

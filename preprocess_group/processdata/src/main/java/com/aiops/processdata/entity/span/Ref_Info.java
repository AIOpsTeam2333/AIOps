package com.aiops.processdata.entity.span;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/3 20:29
 */
@Data
@NoArgsConstructor
public class Ref_Info {
    private String traceId;
    private String parentSegmentId;
    private int parentSpanId;
    private String type;
}

package com.aiops.api.entity.vo.response;

import lombok.Data;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-28 14:59
 */
@Data
public class TraceSpanRef {

    private Integer traceId;

    private Integer parentSegmentId;

    private Integer parentSpanId;

    private String type;

}

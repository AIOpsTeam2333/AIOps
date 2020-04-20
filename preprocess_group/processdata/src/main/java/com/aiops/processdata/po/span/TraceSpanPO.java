package com.aiops.processdata.po.span;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 16:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraceSpanPO {
    private Integer id;
    private String spanId;
    private Integer segmentId;
    private Integer traceId;
    private Integer parentSpanId;
    private String serviceCode;
    private Date startTime;
    private Date endTime;
    private Integer endpointId;
    private Integer instanceId;
    private String type;
    private String peer;
    private String component;
    private boolean isError;
    private String layer;
}

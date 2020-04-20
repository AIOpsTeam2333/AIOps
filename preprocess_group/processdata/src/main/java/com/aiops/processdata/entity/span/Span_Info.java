package com.aiops.processdata.entity.span;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/3 19:50
 */
@Data
@NoArgsConstructor
public class Span_Info {
    private String traceId;
    private String segmentId;
    private int spanId;
    private int parentSpanId;
    private List<Ref_Info> refs;
    private String serviceCode;

    private Long startTime;
    private Long endTime;

    private String endpointName;
    private String type;
    private String peer;
    private String component;
    private boolean isError;
    private String layer;
    private List<Tag_Info> tags;
    private List<Log_Info> logs;

}

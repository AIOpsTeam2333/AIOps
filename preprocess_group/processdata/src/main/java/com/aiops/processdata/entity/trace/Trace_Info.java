package com.aiops.processdata.entity.trace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/14 21:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trace_Info {
    private String segmentId;
    private List<String> endpointNames;
    private Long duration;
    private String start;
    private boolean isError;
    private List<String> traceIds;
}

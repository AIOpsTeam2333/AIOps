package com.aiops.api.entity.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-28 14:50
 */
@Data
public class TraceSpan {

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Integer id;

    private Integer traceId;

    private Integer segmentId;

    private Integer spanId;

    private Integer parentSpanId;

    private List<TraceSpanRef> refs;

    private String serviceCode;

    private Long startTime;

    private Long endTime;

    private String endpointName;

    private String type;

    private String peer;

    private String component;

    private Boolean isError;

    private String layer;

    private List<KeyValue> tags;

    private List<TraceSpanLog> logs;
}

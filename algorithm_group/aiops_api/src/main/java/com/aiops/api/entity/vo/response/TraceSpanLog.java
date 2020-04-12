package com.aiops.api.entity.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-28 15:23
 **/
@Data
public class TraceSpanLog {

    private Long time;

    private List<KeyValue> data;
}

package com.aiops.api.entity.vo.response;

import lombok.Data;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 18:58
 */
@Data
public class DatabaseRecord {
    private Integer traceId;

    private Double latency;

    private String statement;
}

package com.aiops.api.entity.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 17:48
 */
@Data
public class TracePoint {

    private Integer id;

    private StringList endpointNames;

    private Integer duration;

    private Long start;

    private Boolean isError;

}

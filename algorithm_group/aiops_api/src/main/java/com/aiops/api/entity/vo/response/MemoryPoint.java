package com.aiops.api.entity.vo.response;

import lombok.Data;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 17:29
 **/
@Data
public class MemoryPoint {

    private Double value;

    private Double free;

    private Double predict;
}

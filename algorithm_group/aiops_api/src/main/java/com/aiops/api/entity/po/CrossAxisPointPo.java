package com.aiops.api.entity.po;

import lombok.Data;

import java.util.Date;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-18 17:00
 */
@Data
public class CrossAxisPointPo {

    private Date time;

    private Double value;

    private Double predict;
}

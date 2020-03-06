package com.aiops.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 16:30
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class Database extends ServiceNode {

    private Integer databaseId;
}

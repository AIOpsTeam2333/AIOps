package com.aiops.processdata.po.instance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/10 17:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributePO {
    private Integer id;
    private Integer serviceInstanceId;
    private String name;
    private String value;
}

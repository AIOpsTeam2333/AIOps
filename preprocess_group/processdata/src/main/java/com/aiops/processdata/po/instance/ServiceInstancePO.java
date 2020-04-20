package com.aiops.processdata.po.instance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/9 23:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInstancePO {
    private Integer id;
    private Integer serviceId;
    private String name;
    private String uuid;
    private String language;
}

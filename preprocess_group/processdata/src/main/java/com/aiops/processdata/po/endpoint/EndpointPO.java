package com.aiops.processdata.po.endpoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/9 20:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndpointPO {
    private Integer id;
    private Integer serviceId;
    private String name;
}

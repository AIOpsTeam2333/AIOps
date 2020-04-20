package com.aiops.processdata.entity.Instance;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/4 22:44
 */
@Data
@NoArgsConstructor
public class ServiceInstance_Info {
    private String id;
    private String name;
    private List<Attribute_Info> attributes;
    private String language;
}

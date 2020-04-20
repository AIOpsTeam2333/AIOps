package com.aiops.processdata.po.instance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/10 17:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstancePO {
    private ServiceInstancePO serviceInstancePO;
    private List<AttributePO> attributePOList;
}

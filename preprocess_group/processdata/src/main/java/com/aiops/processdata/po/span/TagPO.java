package com.aiops.processdata.po.span;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 16:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagPO {
    private Integer spanId;
    private List<KVPO> KVList;
}

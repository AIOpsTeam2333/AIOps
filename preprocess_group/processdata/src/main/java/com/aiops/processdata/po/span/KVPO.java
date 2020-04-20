package com.aiops.processdata.po.span;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 16:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KVPO {
    private Integer kvId;
    private String key;
    private String value;
}

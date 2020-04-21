package com.aiops.api.entity.vo.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-21 21:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TraceSpanLogData extends ArrayList<KeyValue> {

    public TraceSpanLogData() {
        super();
    }

}

package com.aiops.processdata.entity.trace;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/14 21:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trace_Data {
    @SerializedName("data")
    private Trace_QueryBasicTraces data;
}

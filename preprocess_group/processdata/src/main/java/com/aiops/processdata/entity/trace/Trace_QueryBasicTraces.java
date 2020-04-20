package com.aiops.processdata.entity.trace;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/14 21:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trace_QueryBasicTraces {
    @SerializedName("queryBasicTraces")
    private Trace_InfoList trace_infoList;
}

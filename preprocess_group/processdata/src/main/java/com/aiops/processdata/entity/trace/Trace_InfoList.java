package com.aiops.processdata.entity.trace;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/14 21:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trace_InfoList {
    @SerializedName("traces")
    private List<Trace_Info> traces;
}

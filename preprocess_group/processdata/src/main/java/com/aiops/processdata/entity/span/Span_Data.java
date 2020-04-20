package com.aiops.processdata.entity.span;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/3 19:49
 */
@Data
@NoArgsConstructor
public class Span_Data {
    @SerializedName("data")
    private Span_QueryTrace data;
}

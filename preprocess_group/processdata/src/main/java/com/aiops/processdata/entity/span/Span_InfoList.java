package com.aiops.processdata.entity.span;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/3 20:33
 */
@Data
@NoArgsConstructor
public class Span_InfoList {
    @SerializedName("spans")
    private List<Span_Info> spans;
}

package com.aiops.processdata.entity.service;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/4 21:01
 */
@Data
@NoArgsConstructor
public class Service_Data {
    @SerializedName("data")
    private Service_InfoList data;
}

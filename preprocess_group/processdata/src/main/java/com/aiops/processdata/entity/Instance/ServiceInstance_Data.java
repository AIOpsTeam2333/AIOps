package com.aiops.processdata.entity.Instance;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/4 22:43
 */
@Data
@NoArgsConstructor
public class ServiceInstance_Data {
    @SerializedName("data")
    private ServiceInstance_InfoList data;
}

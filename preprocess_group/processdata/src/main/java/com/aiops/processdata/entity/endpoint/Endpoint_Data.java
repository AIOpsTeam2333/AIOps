package com.aiops.processdata.entity.endpoint;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/2 22:07
 */
@Data
@NoArgsConstructor
public class Endpoint_Data {
    @SerializedName("data")
    private Endpoint_InfoList endpoint_infoList;

}

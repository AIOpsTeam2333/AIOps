package com.aiops.processdata.entity.endpoint;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/2 23:15
 */
@Data
@NoArgsConstructor
public class Endpoint_Info {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("serviceId")
    private String serviceId;

    @SerializedName("serviceName")
    private String serviceName;

}

package com.aiops.processdata.entity.endpoint;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/2 23:31
 */
@Data
@NoArgsConstructor
public class Endpoint_InfoList {

    @SerializedName("getEndpointInfo")
    private List<Endpoint_Info> endpoint_infoList;

}

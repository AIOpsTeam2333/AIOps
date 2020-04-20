package com.aiops.processdata.entity.Instance;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/4 22:44
 */
@Data
@NoArgsConstructor
public class ServiceInstance_InfoList {
    @SerializedName("getServiceInstances")
    private List<ServiceInstance_Info> serviceInstance_infos;
}

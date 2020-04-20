package com.aiops.processdata.entity.service;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/4 21:02
 */
@Data
@NoArgsConstructor
public class Service_InfoList {
    @SerializedName("getAllServices")
    private List<Service_Info> service_infoList;
}

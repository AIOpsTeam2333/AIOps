package com.aiops.processdata.po.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/7 21:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicePO {
    private Integer id;
    private String name;
    private String description;
    private Date addTime;
    private String type;
}

package com.aiops.api.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 0:13
 **/
@Data
@Table(name = "service_instance_attribute")
@ApiModel(description = "服务实例属性")
public class ServiceInstanceAttribute {

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

}

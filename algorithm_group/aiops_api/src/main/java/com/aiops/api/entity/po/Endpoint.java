package com.aiops.api.entity.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-03 23:52
 **/
@Data
@Table(name = "metadata_service_endpoint")
@ApiModel(description = "服务端点")
public class Endpoint {

    @Id
    @Column(name = "service_endpoint_id")
    private Integer serviceEndpointId;

    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "name")
    private String name;
}

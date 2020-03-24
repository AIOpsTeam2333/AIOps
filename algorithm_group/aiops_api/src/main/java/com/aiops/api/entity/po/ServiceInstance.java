package com.aiops.api.entity.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 0:11
 **/
@Data
@Table(name = "metadata_service_instance")
@ApiModel(description = "服务实例")
public class ServiceInstance {

    @Id
    @Column(name = "service_instance_id")
    private Integer serviceInstanceId;

    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "name")
    private String name;

    @Column(name = "language")
    private String language;

    @Column(name = "instance_uuid")
    private String instanceUuid;

    @OneToMany(targetEntity = ServiceInstanceAttribute.class)
    private List<ServiceInstanceAttribute> attributes;
}

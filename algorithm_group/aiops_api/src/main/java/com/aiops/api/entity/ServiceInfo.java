package com.aiops.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 18:21
 **/
@Data
@Table(name = "service_info")
public class ServiceInfo {

    @Id
    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "service_container_id")
    private String serviceContainerId;

    @ApiModelProperty(example = "2000-01-01 00:00:00")
    @Column(name = "add_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTimestamp;

}

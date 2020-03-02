package com.aiops.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 16:05
 **/
@Data
@Table(name = "service_container_info")
public class ServiceContainerInfo implements Serializable {

    @Id
    @Column(name = "service_container_id")
    private String serviceContainerId;

    @Column(name = "os")
    private String os;

    @Column(name = "ip")
    private String ip;

    @Column(name = "add_timestamp")
    private Date addTimestamp;

    @Column(name = "is_in_use")
    private Boolean isInUse;

    @Column(name = "deprecated_timestamp")
    private Date deprecatedTimestamp;
}

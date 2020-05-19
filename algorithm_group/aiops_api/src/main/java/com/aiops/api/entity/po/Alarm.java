package com.aiops.api.entity.po;

import com.aiops.api.common.enums.ScopeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-07 15:32
 */
@Data
@Table(name = "alarm_info")
public class Alarm {

    @JsonIgnore
    @Id
    @Column(name = "alarm_id")
    private Integer alarmId;

    @Column(name = "key")
    private Integer id;

    @Column(name = "message")
    private String message;

    @Column(name = "start_time")
    private Timestamp startTime;

    @ApiModelProperty(example = "Instance")
    @Column(name = "scope")
    private ScopeType scope;

}

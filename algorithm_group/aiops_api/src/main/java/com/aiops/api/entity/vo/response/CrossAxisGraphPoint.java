package com.aiops.api.entity.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 10:39
 */
@Data
public class CrossAxisGraphPoint {

    @JsonIgnore
    private Date time;

    private Double value;

}

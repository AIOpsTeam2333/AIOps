package com.aiops.api.entity.vo.response;

import com.aiops.api.entity.po.Alarm;
import lombok.Data;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-07 15:32
 **/
@Data
public class AlarmList {

    private Integer total;

    private List<Alarm> items;
}

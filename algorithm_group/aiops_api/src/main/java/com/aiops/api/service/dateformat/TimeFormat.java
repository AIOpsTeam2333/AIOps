package com.aiops.api.service.dateformat;

import com.aiops.api.common.type.StatisticsStep;

import java.util.Date;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 10:42
 **/
public interface TimeFormat {

    String date2String(Date date, StatisticsStep step);

    Date string2Date(String str, StatisticsStep step);
}

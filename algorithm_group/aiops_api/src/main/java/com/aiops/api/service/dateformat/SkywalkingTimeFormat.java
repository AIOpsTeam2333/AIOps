package com.aiops.api.service.dateformat;

import com.aiops.api.common.exception.DateParseException;
import com.aiops.api.common.type.StatisticsStep;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 10:43
 **/
@Component
public class SkywalkingTimeFormat implements TimeFormat {

    private final Map<StatisticsStep, SimpleDateFormat> formatMap;

    public SkywalkingTimeFormat() {
        formatMap = new HashMap<>();
        SimpleDateFormat minuteDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmm");
        SimpleDateFormat hourDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        SimpleDateFormat dayDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat monthDateFormat = new SimpleDateFormat("yyyy-MM");
        formatMap.put(StatisticsStep.MINUTE, minuteDateFormat);
        formatMap.put(StatisticsStep.HOUR, hourDateFormat);
        formatMap.put(StatisticsStep.DAY, dayDateFormat);
        formatMap.put(StatisticsStep.MONTH, monthDateFormat);
    }

    @Override
    public String date2String(Date date, StatisticsStep step) {
        return formatMap.get(step).format(date);
    }

    @NotNull
    @Override
    public Date string2Date(String str, StatisticsStep step) {
        try {
            return formatMap.get(step).parse(str);
        } catch (ParseException e) {
            throw new DateParseException("格式错误, " + step + "时间格式为" + formatMap.get(step).toPattern());
        }
    }
}

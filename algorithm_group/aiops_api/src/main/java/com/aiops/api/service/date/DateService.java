package com.aiops.api.service.date;

import com.aiops.api.common.type.StatisticsStep;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-18 22:04
 */
@Service
public class DateService {

    private static final Map<StatisticsStep, DateFormat> formats = new HashMap<StatisticsStep, DateFormat>() {{
        put(StatisticsStep.MINUTE, new SimpleDateFormat("yyyyMMdd HHmm"));
        put(StatisticsStep.HOUR, new SimpleDateFormat("yyyyMMdd HH"));
        put(StatisticsStep.DAY, new SimpleDateFormat("yyyyMMdd"));
        put(StatisticsStep.MONTH, new SimpleDateFormat("yyyyMM"));
    }};

    public Date format(Date date, StatisticsStep step) {
        if (date == null || step == null) return null;
        DateFormat format = formats.get(step);
        try {
            return format.parse(format.format(date));
        } catch (ParseException e) {
            return null;
        }
    }

    public List<Date> generateStepTimes(Date from, Date until, StatisticsStep step) {
        if (from == null || until == null || step == null) return new ArrayList<>();
        if (step == StatisticsStep.MONTH) {
            return generateMonthStepTimes(from, until);
        }

        return generateGeneralStepTimes(from, until, step);
    }

    private List<Date> generateGeneralStepTimes(Date from, Date until, StatisticsStep step) {
        from = format(from, step);
        until = format(until, step);
        long fromTimeStamp = from.getTime();
        long untilTimeStamp = until.getTime();
        long currentTimeStamp = fromTimeStamp;
        List<Date> result = new ArrayList<>();
        while (currentTimeStamp <= untilTimeStamp) {
            currentTimeStamp += step.getStep();
            Date newDate = new Date(currentTimeStamp);
            result.add(newDate);
        }
        return result;
    }

    private List<Date> generateMonthStepTimes(Date from, Date until) {
        DateFormat monthDateFormat = formats.get(StatisticsStep.MONTH);
        String fromStr = monthDateFormat.format(from);
        String untilStr = monthDateFormat.format(until);
        int fromYear = Integer.parseInt(fromStr.substring(0, 4));
        int fromMonth = Integer.parseInt(fromStr.substring(4)) - 1;
        int untilYear = Integer.parseInt(untilStr.substring(0, 4));
        int untilMonth = Integer.parseInt(untilStr.substring(4)) - 1;
        int fromIndex = fromYear * 12 + fromMonth;
        int untilIndex = untilYear * 12 + untilMonth;
        List<Date> result = new ArrayList<>();
        for (int i = fromIndex + 1; i <= untilIndex; i++) {
            int year = i / 12;
            int month = (i % 12) + 1;
            try {
                Date newDate = monthDateFormat.parse(String.format("%04d%02d", year, month));
                result.add(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return result;

    }
}

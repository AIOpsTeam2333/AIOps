package com.aiops.util;

import com.aiops.query.enums.Step;

import java.text.SimpleDateFormat;

public class FormatUtil {

    private static SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmm");
    private static SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHH");
    private static SimpleDateFormat format3 = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat format4 = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat format5 = new SimpleDateFormat();

    public static SimpleDateFormat createDateFormatByStep(Step step){
        if (step == Step.MINUTE){
            return format1;
        }
        else if (step == Step.HOUR){
            return format2;
        }
        else if (step == Step.DAY){
            return format3;
        }
        else if (step == Step.MONTH){
            return format4;
        }
        else
            return format5;
    }
}

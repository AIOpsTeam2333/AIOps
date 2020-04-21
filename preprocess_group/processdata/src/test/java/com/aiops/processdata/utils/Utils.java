package com.aiops.processdata.utils;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/21 15:54
 */
public class Utils {

    @Test
    public void stampToDate() {
        Date date = new Date(1585294678472L);
        String current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
        System.out.println(current);
        date = new Date(1585294678780L);
        System.out.println(date);
        current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);

        Timestamp time = Timestamp.valueOf(current);
        System.out.println(current);
        time = new Timestamp(1585294678472L);
        System.out.println(time);
    }
}

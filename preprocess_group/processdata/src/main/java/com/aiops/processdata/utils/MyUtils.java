package com.aiops.processdata.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.sql.Timestamp;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/2 23:39
 */
public class MyUtils {
    /**
     * 读文件
     *
     * @param filePath 文件路径
     * @return 文件内容
     */
    public static String readFile(String filePath) {
        String json = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
                    filePath)));
            String temp = "";
            while ((temp = reader.readLine()) != null) {
                json += temp;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 时间戳转化为时间
     *
     * @param timestamp
     * @return
     */
    public static Timestamp stampToDate(Long timestamp) {
        //  SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new Timestamp(timestamp);
    }

    /**
     * javaBean转换为json串
     *
     * @param object
     * @return 转化后的json串
     */
    public static String beanToJson(Object object) {
        Gson g = new GsonBuilder().create();
        return g.toJson(object);
    }
}


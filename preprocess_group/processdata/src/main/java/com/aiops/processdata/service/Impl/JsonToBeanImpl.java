package com.aiops.processdata.service.Impl;

import com.aiops.processdata.service.JsonToBean;
import com.aiops.processdata.utils.MyUtils;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/4 21:16
 */
@Service
public class JsonToBeanImpl implements JsonToBean {
    @Override
    public <T> T convert(String filePath, Class<T> tClass) {
        String json = MyUtils.readFile(filePath);
        return new Gson().fromJson(json, tClass);
    }
}

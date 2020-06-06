package com.aiops.query.parser;

import com.aiops.model.MetricEndpointDO;
import com.aiops.query.enums.Step;
import com.aiops.util.FormatUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MetricEndpointParser {

    public static MetricEndpointDO parseResponse(Step step, JSONObject response) throws ParseException {
        //处理格式
        JSONArray values = response.getJSONObject("data").getJSONObject("getLinearIntValues").getJSONArray("values");
        JSONObject value = values.getJSONObject(0);
        String[] ids = value.getString("id").split("_");

        //解析时间
        SimpleDateFormat format = FormatUtil.createDateFormatByStep(step);
        Timestamp timestamp = new Timestamp(format.parse(ids[0]).getTime());
        double val = value.getDouble("value");
        int id = Integer.parseInt(ids[1]);

        //建立DO对象并返回
        return new MetricEndpointDO(id, val, timestamp);
    }
}

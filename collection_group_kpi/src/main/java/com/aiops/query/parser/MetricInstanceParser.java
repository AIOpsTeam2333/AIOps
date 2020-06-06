package com.aiops.query.parser;

import com.aiops.model.MetricInstanceDO;
import com.aiops.query.enums.Step;
import com.aiops.util.FormatUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MetricInstanceParser {

    // 输入格式：
    // {
    //    "data": {
    //        "getLinearIntValues": {
    //            "values": [
    //                {
    //                    "id": "202003_4",
    //                    "value": 0
    //                },
    //                {
    //                    "id": "202004_4",
    //                    "value": 0
    //                }
    //            ]
    //        }
    //    }
    //}
    public static MetricInstanceDO parseResponse(Step step, JSONObject response) throws ParseException {
        JSONArray values = response.getJSONObject("data").getJSONObject("getLinearIntValues").getJSONArray("values");
        JSONObject value = values.getJSONObject(0);

        String[] ids = value.getString("id").split("_");

        SimpleDateFormat format = FormatUtil.createDateFormatByStep(step);
        Timestamp timestamp = new Timestamp(format.parse(ids[0]).getTime());
        double val = value.getDouble("value");
        int id = Integer.parseInt(ids[1]);
        return new MetricInstanceDO(id, val, timestamp);
    }
}

package com.aiops.query.parser;

import com.aiops.model.MetricAllDO;
import com.aiops.query.enums.Step;
import com.aiops.util.FormatUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MetricAllParser {

    //格式：{"data":{"getLinearIntValues":{"values":[{"id":"202004202228","value":30},{"id":"202004202229","value":60}]}}}

    public static MetricAllDO parseResponse(Step step, JSONObject response) throws ParseException {
        JSONArray values = response.getJSONObject("data").getJSONObject("getLinearIntValues").getJSONArray("values");
        JSONObject value = values.getJSONObject(0);

        SimpleDateFormat format = FormatUtil.createDateFormatByStep(step);
        Timestamp timestamp = new Timestamp(format.parse(value.getString("id")).getTime());
        double val = value.getDouble("value");
        return new MetricAllDO(val, timestamp);
    }
}

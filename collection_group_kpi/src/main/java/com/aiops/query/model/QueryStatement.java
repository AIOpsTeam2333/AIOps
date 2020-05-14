package com.aiops.query.model;

import com.alibaba.fastjson.JSONObject;

public class QueryStatement {
    private static final String QUERY = "query";
    private static final String VARIABLES = "variables";
    private static final String DURATION = "duration";
    private static final String METRICCONDITION = "metriccondition";

    private JSONObject value;

    public QueryStatement(String query) {
        this.value = new JSONObject();
        this.value.put(QUERY, query);
        this.value.put(VARIABLES, new JSONObject());
    }

    public String toJSONString() {
        return this.value.toJSONString();
    }

    public void addDuration(Duration duration) {
        this.addParam(DURATION, duration);
    }

    public void addMetricConditon(MetricCondition metricCondition) {
        this.addParam(METRICCONDITION, metricCondition);
    }

    public void addParam(String key, Object value) {
        this.value.getJSONObject(VARIABLES).put(key, value);
    }
}

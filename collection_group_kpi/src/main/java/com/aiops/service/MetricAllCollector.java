package com.aiops.service;

import com.aiops.dao.MetricAllDAO;
import com.aiops.model.MetricAllDO;
import com.aiops.query.QueryHelper;
import com.aiops.query.enums.Step;
import com.aiops.query.model.Duration;
import com.aiops.query.model.MetricCondition;
import com.aiops.query.model.QueryStatement;
import com.aiops.query.parser.MetricAllParser;
import com.aiops.util.FormatUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
@EnableScheduling
public class MetricAllCollector {

    private static String[] metrics = {"all_p50", "all_p75", "all_p90", "all_p95", "all_p99"};
    private static String queryType = "getLinearIntValues";

    @Autowired
    private MetricAllDAO metricAllDAO;
    @Autowired
    private RetryCollector retryCollector;

    public void collectAll(Step step){

        Date date = new Date();
        for (String metric : metrics){
            collectSingle(queryType, metric, step, date);
        }
        //收集heatmap的逻辑与主分支不一致
        collectHeatMap(step, date);
        System.out.println("Collect All(Global) Info By " + step + ": " + new Date());
    }

    private void collectSingle(String querytype, String metric, Step step, Date date){

        QueryStatement statement = QueryHelper.getQueryStatement(querytype);
        statement.addDuration(new Duration(date, step));
        statement.addMetricConditon(new MetricCondition(metric));
        MetricAllDO metricAllDO;

        try {
            JSONObject json = QueryHelper.query(statement);
            metricAllDO = MetricAllParser.parseResponse(step, json);
        } catch (ParseException e) {
            retryCollector.addRetryQueryStatement(statement, metric, step);
            return;
        }

        metricAllDAO.insertMetricAllDO(metricAllDO, metric, step);
    }

    private void collectHeatMap(Step step, Date date){
        //创建查询
        QueryStatement statement = QueryHelper.getQueryStatement("getThermodynamic");
        Duration duration = new Duration(date, step);
        statement.addDuration(duration);
        statement.addMetricConditon(new MetricCondition("all_heatmap"));
        JSONObject json = QueryHelper.query(statement);

        //创建timestamp
        SimpleDateFormat format = new SimpleDateFormat();
        if (step == Step.MINUTE){
            format = new SimpleDateFormat("yyyy-MM-dd HHmm");
        }
        else if (step == Step.HOUR){
            format = new SimpleDateFormat("yyyy-MM-dd HH");
        }
        else if (step == Step.DAY){
            format = new SimpleDateFormat("yyyy-MM-dd");
        }
        else if (step == Step.MONTH){
            format = new SimpleDateFormat("yyyy-MM");
        }
        Timestamp timestamp = null;
        try {
            timestamp = new Timestamp(format.parse(duration.getStart()).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //解析返回值
        JSONArray values = json.getJSONObject("data").getJSONObject("getThermodynamic").getJSONArray("nodes");
        for (int i=0; i<=20; i++){
            JSONArray value = values.getJSONArray(i);
            metricAllDAO.insertHeatMap(i, value.getDouble(2), timestamp, step);
        }
    }
}

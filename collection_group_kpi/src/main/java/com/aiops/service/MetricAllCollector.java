package com.aiops.service;

import com.aiops.dao.MetricAllDAO;
import com.aiops.model.MetricAllDO;
import com.aiops.query.QueryHelper;
import com.aiops.query.enums.Step;
import com.aiops.query.model.Duration;
import com.aiops.query.model.MetricCondition;
import com.aiops.query.model.QueryStatement;
import com.aiops.query.parser.MetricAllParser;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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

        for (String metric : metrics){
            collectSingle(queryType, metric, step);
        }
        System.out.println("Collect All(Global) Info By " + step + ": " + new Date());
    }

    private void collectSingle(String querytype, String metric, Step step){

        QueryStatement statement = QueryHelper.getQueryStatement(querytype);
        statement.addDuration(new Duration(new Date(), step));
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
}

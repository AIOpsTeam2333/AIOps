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
import com.fasterxml.jackson.databind.annotation.JsonAppend;
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

    public void collectAll(Step step){
        try {
            for (String metric : metrics){
                collectSingle(queryType, metric, step);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Collect All(Global) Info By " + step + ": " + new Date());
    }

    private void collectSingle(String querytype, String metric, Step step) throws ParseException {

        QueryStatement statement = QueryHelper.getQueryStatement(querytype);
        statement.addDuration(new Duration(new Date(), step));
        statement.addMetricConditon(new MetricCondition(metric));

        JSONObject json = QueryHelper.query(statement);

        MetricAllDO metricAllDO = MetricAllParser.parseResponse(step, json);
        metricAllDAO.insertMetricAllDO(metricAllDO, metric, step);
    }
}

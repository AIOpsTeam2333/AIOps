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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@EnableScheduling
public class MetricAllCollector {

    private static String[] metrics = {"all_p50", "all_p75", "all_p90", "all_p95", "all_p99"};
    private static String queryType = "getLinearIntValues";

    @Scheduled(cron = "0 */1 * * * ?")
    public void collectAllPerMinute(){
        collectAll(Step.MINUTE);
    }

    @Scheduled(cron = "0 0 */1 * * ?")
    public void collectAllPerHour(){
        collectAll(Step.HOUR);
    }

    @Scheduled(cron = "0 0 23 * * ?")
    public void collectAllPerDay(){
        collectAll(Step.DAY);
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void collectAllPerMonth(){
        collectAll(Step.MONTH);
    }

    private void collectAll(Step step){
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
        new MetricAllDAO().insertMetricAllDO(metricAllDO, metric, step);
//        System.out.print("Metric: "+metric+", TimeStamp: " + metricAllDO.getTimestamp() + ", Value: " + metricAllDO.getValue()+"\n");
    }
}

package com.aiops.service;

import com.aiops.dao.MetricServiceDAO;
import com.aiops.model.MetricServiceDO;
import com.aiops.query.QueryHelper;
import com.aiops.query.enums.Step;
import com.aiops.query.model.Duration;
import com.aiops.query.model.MetricCondition;
import com.aiops.query.model.QueryStatement;
import com.aiops.query.parser.MetricServiceParser;
import com.aiops.service.holder.MetaDataHolder;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
@EnableScheduling
public class MetricServiceCollector {

    private static String[] metrics = {"service_resp_time", "service_sla", "service_cpm", "service_p99",
            "service_p95", "service_p90", "service_p75", "service_p50", "service_apdex"};
    private static String queryType = "getLinearIntValues";

    @Scheduled(cron = "0 */1 * * * ?")
    public void collectAllPerMinute(){
        collectService(Step.MINUTE);
    }

    @Scheduled(cron = "0 0 */1 * * ?")
    public void collectAllPerHour(){
        collectService(Step.HOUR);
    }

    @Scheduled(cron = "0 0 23 * * ?")
    public void collectAllPerDay(){
        collectService(Step.DAY);
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void collectAllPerMonth(){
        collectService(Step.MONTH);
    }

    public void collectService(Step step){
        List<String> services = MetaDataHolder.getServices();
        try {
            for (String metric : metrics){
                for (String serviceId : services){
                    collectSingle(queryType, metric, serviceId, step);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Collect Service Info By " + step + ": " + new Date());
    }

    @Autowired
    private MetricServiceDAO metricServiceDAO;

    public void collectSingle(String querytype, String metric, String serviceId, Step step) throws ParseException {

        QueryStatement statement = QueryHelper.getQueryStatement(querytype);
        statement.addDuration(new Duration(new Date(), step));
        statement.addMetricConditon(new MetricCondition(metric, serviceId));

        JSONObject json = QueryHelper.query(statement);

        MetricServiceDO metricServiceDO = MetricServiceParser.parseResponse(step, json);
        metricServiceDAO.insertMetricServiceDO(metricServiceDO, metric, step);
//        System.out.print("Metric: "+metric+", ServiceId: "+serviceId+", TimeStamp: " + metricServiceDO.getTimestamp() + ", Value: " + metricServiceDO.getValue()+"\n");
    }

}

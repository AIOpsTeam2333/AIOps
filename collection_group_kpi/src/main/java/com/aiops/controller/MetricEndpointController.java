package com.aiops.controller;

import com.aiops.query.enums.Step;
import com.aiops.service.MetricEndpointCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@EnableScheduling
public class MetricEndpointController {

    @Autowired
    private MetricEndpointCollector metricEndpointCollector;

    //每分钟定时收集
    @Scheduled(cron = "0 */1 * * * ?")
    public void collectEndpointPerMinute() {
        metricEndpointCollector.collectEndpoint(Step.MINUTE);
    }

    //每小时定时收集
    @Scheduled(cron = "0 0 */1 * * ?")
    public void collectEndpointPerHour() {
        metricEndpointCollector.collectEndpoint(Step.HOUR);
    }

    //每日定时收集
    @Scheduled(cron = "0 0 23 * * ?")
    public void collectEndpointPerDay() {
        metricEndpointCollector.collectEndpoint(Step.DAY);
    }

    //每月定时收集
    @Scheduled(cron = "0 0 0 1 * ?")
    public void collectEndpointPerMonth() {
        metricEndpointCollector.collectEndpoint(Step.MONTH);
    }
}

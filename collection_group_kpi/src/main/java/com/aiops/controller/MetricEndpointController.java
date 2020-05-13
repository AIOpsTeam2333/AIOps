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

    @Scheduled(cron = "0 */1 * * * ?")
    public void collectEndpointPerMinute(){
        metricEndpointCollector.collectEndpoint(Step.MINUTE);
    }

    @Scheduled(cron = "0 0 */1 * * ?")
    public void collectEndpointPerHour(){
        metricEndpointCollector.collectEndpoint(Step.HOUR);
    }

    @Scheduled(cron = "0 0 23 * * ?")
    public void collectEndpointPerDay(){
        metricEndpointCollector.collectEndpoint(Step.DAY);
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void collectEndpointPerMonth(){
        metricEndpointCollector.collectEndpoint(Step.MONTH);
    }
}

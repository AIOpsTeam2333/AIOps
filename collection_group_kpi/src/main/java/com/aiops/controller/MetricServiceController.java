package com.aiops.controller;

import com.aiops.query.enums.Step;
import com.aiops.service.MetricServiceCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@EnableScheduling
public class MetricServiceController {

    @Autowired
    private MetricServiceCollector metricServiceCollector;

    @Scheduled(cron = "0 */1 * * * ?")
    public void collectServicePerMinute(){
        metricServiceCollector.collectService(Step.MINUTE);
    }

    @Scheduled(cron = "0 0 */1 * * ?")
    public void collectServicePerHour(){
        metricServiceCollector.collectService(Step.HOUR);
    }

    @Scheduled(cron = "0 0 23 * * ?")
    public void collectServicePerDay(){
        metricServiceCollector.collectService(Step.DAY);
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void collectServicePerMonth(){
        metricServiceCollector.collectService(Step.MONTH);
    }
}

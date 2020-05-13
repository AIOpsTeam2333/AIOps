package com.aiops.controller;

import com.aiops.query.enums.Step;
import com.aiops.service.MetricAllCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@EnableScheduling
public class MetricAllController {

    @Autowired
    private MetricAllCollector metricAllCollector;

    @Scheduled(cron = "0 */1 * * * ?")
    public void collectAllPerMinute(){
        metricAllCollector.collectAll(Step.MINUTE);
    }

    @Scheduled(cron = "0 0 */1 * * ?")
    public void collectAllPerHour(){
        metricAllCollector.collectAll(Step.HOUR);
    }

    @Scheduled(cron = "0 0 23 * * ?")
    public void collectAllPerDay(){
        metricAllCollector.collectAll(Step.DAY);
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void collectAllPerMonth(){
        metricAllCollector.collectAll(Step.MONTH);
    }
}

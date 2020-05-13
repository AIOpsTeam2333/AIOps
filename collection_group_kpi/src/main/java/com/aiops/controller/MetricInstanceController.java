package com.aiops.controller;

import com.aiops.query.enums.Step;
import com.aiops.service.MetricInstanceCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@EnableScheduling
public class MetricInstanceController {

    @Autowired
    private MetricInstanceCollector metricInstanceCollector;

    @Scheduled(cron = "0 */1 * * * ?")
    public void collectInstancePerMinute(){
        metricInstanceCollector.collectInstance(Step.MINUTE);
    }

    @Scheduled(cron = "0 0 */1 * * ?")
    public void collectInstancePerHour(){
        metricInstanceCollector.collectInstance(Step.HOUR);
    }

    @Scheduled(cron = "0 0 23 * * ?")
    public void collectInstancePerDay(){
        metricInstanceCollector.collectInstance(Step.DAY);
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void collectInstancePerMonth(){
        metricInstanceCollector.collectInstance(Step.MONTH);
    }
}

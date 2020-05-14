package com.aiops.controller;

import com.aiops.service.RetryCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@EnableScheduling
public class RetryQueryController {

    @Autowired
    private RetryCollector retryCollector;

    @Scheduled(cron = "0 */5 * * * ?")
    public void retryUnsuccessfulQuery(){
        retryCollector.retryUnsuccessfulQuery();
    }
}

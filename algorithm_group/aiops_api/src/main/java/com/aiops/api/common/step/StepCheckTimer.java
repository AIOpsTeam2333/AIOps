package com.aiops.api.common.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-18 21:36
 **/
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class StepCheckTimer {

    private final StepChecker stepChecker;

    //每天2:00执行
    @Scheduled(cron = "0 0 2 * * *")
    public void stepCheckSchedule() {
        stepChecker.checkAllStepsAndFill();
    }
}

package com.aiops.api.common.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-18 21:34
 **/
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class StepCheckInitiator implements ApplicationRunner {

    private final StepChecker stepChecker;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        stepChecker.checkAllStepsAndFill();
    }
}

package com.aiops.api.common.step;

import com.aiops.api.common.enums.StatisticsStep;
import com.aiops.api.dao.StepDao;
import com.aiops.api.service.date.DateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-18 21:35
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
class StepChecker {

    private final StepDao stepDao;
    private final DateService dateService;
    @Value("${aiops.step.reserved-number}")
    private Long reservedNumber;

    void checkAllStepsAndFill() {
        log.info("开始检查时间step...");
        for (StatisticsStep step : StatisticsStep.values()) {
            log.info("检查step: " + step.getName());
            checkStepAndFill(step);
        }
        log.info("检查时间step结束.");
    }

    private void checkStepAndFill(StatisticsStep step) {
        Date maxTime = stepDao.getMaxStepTime(step);
        if (maxTime == null) {
            maxTime = new Date(new Date().getTime() - reservedNumber * step.getStep());
        }

        //计算要存储到的时间
        Date now = dateService.format(new Date(), step);
        Date untilTime = new Date(now.getTime() + reservedNumber * step.getStep());

        //如果要存储到的时间比数据库最大时间大, 即计算新的时间step并存入
        if (maxTime.before(untilTime)) {
            List<Date> dateList = dateService.generateStepTimes(maxTime, untilTime, step);
            if (!dateList.isEmpty()){
                stepDao.saveStepTime(dateList, step);
                log.info("已存入" + dateList.size() + "个时间step.");
            }
        }

    }
}

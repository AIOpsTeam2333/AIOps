package com.aiops.api.dao;

import com.aiops.api.common.type.StatisticsStep;
import com.aiops.api.mapper.StepMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-18 21:41
 **/
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StepDao {

    private final StepMapper stepMapper;

    public Date getMaxStepTime(StatisticsStep step) {
        return stepMapper.selectMaxTimeInStep(step.getName());
    }

    public void saveStepTime(List<Date> dateList, StatisticsStep step) {
        stepMapper.saveTimes(dateList, step.getName());
    }
}

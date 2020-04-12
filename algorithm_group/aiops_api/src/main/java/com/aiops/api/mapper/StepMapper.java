package com.aiops.api.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-18 21:38
 */
@Repository
public interface StepMapper {


    Date selectMaxTimeInStep(@Param("step") String step);

    void saveTimes(@Param("dataList") List<Date> dateList, @Param("step") String step);
}

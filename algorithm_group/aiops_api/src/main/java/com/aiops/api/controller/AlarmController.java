package com.aiops.api.controller;

import com.aiops.api.common.validation.NeedIdGroup;
import com.aiops.api.entity.vo.request.CommonRequestBodyAlarm;
import com.aiops.api.entity.vo.request.CommonRequestBodyKpi;
import com.aiops.api.entity.vo.response.AlarmList;
import com.aiops.api.service.alarm.AlarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Shuaiyu Yao
 * @create 2020-04-07 15:29
 */
@Slf4j
@Api(tags = {"报警信息api"})
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @ApiOperation(value = "报警信息查询")
    @PostMapping("/")
    public AlarmList getAlarms(
            @RequestBody @Validated CommonRequestBodyAlarm commonRequestBodyAlarm
    ) {
        return alarmService.getAlarmList(commonRequestBodyAlarm);
    }

}

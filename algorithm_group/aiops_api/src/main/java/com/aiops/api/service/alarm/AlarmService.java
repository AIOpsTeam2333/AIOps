package com.aiops.api.service.alarm;

import com.aiops.api.common.enums.ScopeType;
import com.aiops.api.dao.AlarmDao;
import com.aiops.api.entity.po.Alarm;
import com.aiops.api.entity.vo.request.CommonRequestBodyAlarm;
import com.aiops.api.entity.vo.response.AlarmList;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-07 16:49
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlarmService {

    @Value("${aiops.paging.size}")
    private Integer defaultPageSize;

    private final AlarmDao alarmDao;

    public AlarmList getAlarmList(
            CommonRequestBodyAlarm commonRequestBodyAlarm
    ) {
        if (commonRequestBodyAlarm == null) return new AlarmList();

        Date from = commonRequestBodyAlarm.getDurationFrom();
        Date to = commonRequestBodyAlarm.getDurationTo();
        ScopeType scope = commonRequestBodyAlarm.getScope();
        String keyword = commonRequestBodyAlarm.getKeyword();
        //分页
        Integer pageSize = commonRequestBodyAlarm.getPagingSize();
        if (pageSize == null) pageSize = defaultPageSize;
        Integer pageNum = commonRequestBodyAlarm.getPagingNum();
        if (pageNum == null) pageNum = 1;

        //查询
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Alarm> pageInfo = new PageInfo<>(alarmDao.queryAlarms(from, to, scope, keyword));
        AlarmList result = new AlarmList();
        result.setItems(pageInfo.getList());
        result.setTotal((int) pageInfo.getTotal());
        PageHelper.clearPage();
        return result;
    }

}

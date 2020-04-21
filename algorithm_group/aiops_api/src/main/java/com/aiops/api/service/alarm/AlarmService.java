package com.aiops.api.service.alarm;

import com.aiops.api.common.enums.ScopeType;
import com.aiops.api.dao.AlarmDao;
import com.aiops.api.entity.po.Alarm;
import com.aiops.api.entity.vo.request.RequestBodyAlarm;
import com.aiops.api.entity.vo.request.Paging;
import com.aiops.api.entity.vo.response.AlarmList;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final AlarmDao alarmDao;
    private final Paging defaultPaging;

    public AlarmList getAlarmList(
            RequestBodyAlarm requestBodyAlarm
    ) {
        if (requestBodyAlarm == null) return new AlarmList();

        Date from = requestBodyAlarm.getDurationFrom();
        Date to = requestBodyAlarm.getDurationTo();
        ScopeType scope = requestBodyAlarm.getScope();
        String keyword = requestBodyAlarm.getKeyword();
        //分页
        Integer pageSize = requestBodyAlarm.getPagingSize();
        if (pageSize == null) pageSize = defaultPaging.getPageSize();
        Integer pageNum = requestBodyAlarm.getPagingNum();
        if (pageNum == null) pageNum = defaultPaging.getPageNum();

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

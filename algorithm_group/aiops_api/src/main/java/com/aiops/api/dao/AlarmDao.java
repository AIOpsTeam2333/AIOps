package com.aiops.api.dao;

import com.aiops.api.common.enums.ScopeType;
import com.aiops.api.entity.po.Alarm;
import com.aiops.api.mapper.AlarmMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-07 16:33
 */
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlarmDao {

    private final AlarmMapper alarmMapper;

    public List<Alarm> queryAlarms(
            Date from,
            Date to,
            ScopeType scope,
            String keyword
    ) {
        return alarmMapper.queryAlarms(from, to, scope, keyword);
    }

}

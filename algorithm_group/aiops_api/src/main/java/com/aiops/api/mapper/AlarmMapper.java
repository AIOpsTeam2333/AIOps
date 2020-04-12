package com.aiops.api.mapper;

import com.aiops.api.common.enums.ScopeType;
import com.aiops.api.entity.po.Alarm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-07 16:21
 */
@Repository
public interface AlarmMapper {

    List<Alarm> queryAlarms(
            @Param("from") Date from,
            @Param("to") Date to,
            @Param("scope") ScopeType scope,
            @Param("keyword") String keyword
    );
}

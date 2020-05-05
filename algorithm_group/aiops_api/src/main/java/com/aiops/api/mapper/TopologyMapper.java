package com.aiops.api.mapper;

import com.aiops.api.entity.vo.response.TopologyCall;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-05-05 16:56
 */
@Repository
public interface TopologyMapper {

    List<TopologyCall> queryTopologyCall(
            @Param("from") Date from,
            @Param("to") Date to,
            @Param("id") Integer id,
            @Param("scope") String scope
    );

}

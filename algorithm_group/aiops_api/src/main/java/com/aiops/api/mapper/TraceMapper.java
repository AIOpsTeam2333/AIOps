package com.aiops.api.mapper;

import com.aiops.api.entity.vo.response.TracePoint;
import com.aiops.api.service.trace.dto.TraceSearchDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-25 14:04
 */
@Repository
public interface TraceMapper {

    List<TracePoint> queryTraces(TraceSearchDto dto);

}

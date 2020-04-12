package com.aiops.api.dao;

import com.aiops.api.entity.vo.response.TracePoint;
import com.aiops.api.mapper.TraceMapper;
import com.aiops.api.service.trace.dto.TraceSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-25 15:28
 */
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TraceDao {

    private final TraceMapper traceMapper;

    public List<TracePoint> queryTraces(TraceSearchDto traceSearchDto) {
        return traceMapper.queryTraces(traceSearchDto);
    }
}

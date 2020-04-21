package com.aiops.api.service.trace;

import com.aiops.api.common.enums.QueryOrder;
import com.aiops.api.dao.TraceDao;
import com.aiops.api.entity.vo.request.RequestBodyTrace;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.request.Paging;
import com.aiops.api.entity.vo.response.TracePoint;
import com.aiops.api.entity.vo.response.TraceGraph;
import com.aiops.api.entity.vo.response.TraceSpan;
import com.aiops.api.service.trace.dto.TraceSearchDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-24 19:00
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TraceService {

    private final Paging defaultPaging;
    private final TraceDao traceDao;

    public TraceGraph queryTracesInfoByEndpointId(Duration duration, Integer endpointId) {
        RequestBodyTrace requestBodyTrace = new RequestBodyTrace();
        requestBodyTrace.setEndpointId(endpointId);
        requestBodyTrace.setQueryOrder(QueryOrder.BY_DURATION);
        requestBodyTrace.setDuration(duration);
        requestBodyTrace.setPaging(defaultPaging);
        return queryTracesInfo(requestBodyTrace);
    }


    public TraceGraph queryTracesInfo(
            RequestBodyTrace requestBodyTrace
    ) {
        if (requestBodyTrace == null) {
            return new TraceGraph();
        }

        TraceSearchDto traceSearchDto = traceBody2TraceSearchDto(requestBodyTrace);
        Paging paging = requestBodyTrace.getPaging();
        PageHelper.startPage(paging);
        PageInfo<TracePoint> pageInfo = new PageInfo<>(traceDao.queryTraces(traceSearchDto));
        TraceGraph result = new TraceGraph();
        result.setTraces(pageInfo.getList());
        result.setTotal((int) pageInfo.getTotal());
        PageHelper.clearPage();
        return result;
    }


    private TraceSearchDto traceBody2TraceSearchDto(RequestBodyTrace requestBodyTrace) {
        if (requestBodyTrace == null) {
            return null;
        }

        TraceSearchDto traceSearchDto = new TraceSearchDto();
        traceSearchDto.setServiceId(requestBodyTrace.getServiceId());
        traceSearchDto.setEndpointId(requestBodyTrace.getEndpointId());
        traceSearchDto.setServiceInstanceId(requestBodyTrace.getServiceInstanceId());
        traceSearchDto.setTraceState(Objects.toString(requestBodyTrace.getTraceState()));
        traceSearchDto.setQueryOrder(Objects.toString(requestBodyTrace.getQueryOrder()));
        traceSearchDto.setFrom(requestBodyTrace.getDurationFrom());
        traceSearchDto.setTo(requestBodyTrace.getDurationTo());
        traceSearchDto.setMinTraceDuration(requestBodyTrace.getMinTraceDuration());
        traceSearchDto.setMaxTraceDuration(requestBodyTrace.getMaxTraceDuration());
        return traceSearchDto;
    }


    public List<TraceSpan> queryTraceSpans(Integer traceId) {
        return traceDao.querySpans(traceId);
    }
}

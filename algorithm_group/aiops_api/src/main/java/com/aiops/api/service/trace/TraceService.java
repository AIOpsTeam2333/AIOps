package com.aiops.api.service.trace;

import com.aiops.api.common.enums.QueryOrder;
import com.aiops.api.dao.TraceDao;
import com.aiops.api.entity.vo.request.CommonRequestBodyTrace;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.request.Paging;
import com.aiops.api.entity.vo.response.TracePoint;
import com.aiops.api.entity.vo.response.TraceGraph;
import com.aiops.api.service.trace.dto.TraceSearchDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-24 19:00
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TraceService {

    @Value("${aiops.paging.size}")
    private Integer defaultPageSize;
    private final TraceDao traceDao;

    public TraceGraph queryTracesInfoByEndpointId(Duration duration, Integer endpointId) {
        CommonRequestBodyTrace commonRequestBodyTrace = new CommonRequestBodyTrace();
        commonRequestBodyTrace.setEndpointId(endpointId);
        commonRequestBodyTrace.setQueryOrder(QueryOrder.BY_DURATION);
        commonRequestBodyTrace.setDuration(duration);
        Paging paging = new Paging();
        paging.setPageSize(defaultPageSize);
        paging.setPageNum(1);
        commonRequestBodyTrace.setPaging(paging);
        return queryTracesInfo(commonRequestBodyTrace);
    }


    public TraceGraph queryTracesInfo(
            CommonRequestBodyTrace commonRequestBodyTrace
    ) {
        if (commonRequestBodyTrace == null) {
            return new TraceGraph();
        }

        TraceSearchDto traceSearchDto = traceBody2TraceSearchDto(commonRequestBodyTrace);
        Integer pageNum = commonRequestBodyTrace.getPagingNum();
        Integer pageSize = commonRequestBodyTrace.getPagingSize();
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<TracePoint> pageInfo = new PageInfo<>(traceDao.queryTraces(traceSearchDto));
        TraceGraph result = new TraceGraph();
        result.setTraces(pageInfo.getList());
        result.setTotal((int) pageInfo.getTotal());
        PageHelper.clearPage();
        return result;
    }


    private TraceSearchDto traceBody2TraceSearchDto(CommonRequestBodyTrace commonRequestBodyTrace) {
        if (commonRequestBodyTrace == null) {
            return null;
        }

        TraceSearchDto traceSearchDto = new TraceSearchDto();
        traceSearchDto.setServiceId(commonRequestBodyTrace.getServiceId());
        traceSearchDto.setEndpointId(commonRequestBodyTrace.getEndpointId());
        traceSearchDto.setServiceInstanceId(commonRequestBodyTrace.getServiceInstanceId());
        traceSearchDto.setTraceState(Objects.toString(commonRequestBodyTrace.getTraceState()));
        traceSearchDto.setQueryOrder(Objects.toString(commonRequestBodyTrace.getQueryOrder()));
        traceSearchDto.setFrom(commonRequestBodyTrace.getDurationFrom());
        traceSearchDto.setTo(commonRequestBodyTrace.getDurationTo());
        traceSearchDto.setMinTraceDuration(commonRequestBodyTrace.getMinTraceDuration());
        traceSearchDto.setMaxTraceDuration(commonRequestBodyTrace.getMaxTraceDuration());
        return traceSearchDto;
    }
}

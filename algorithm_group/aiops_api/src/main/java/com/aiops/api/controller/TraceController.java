package com.aiops.api.controller;

import com.aiops.api.common.validation.NeedIdGroup;
import com.aiops.api.entity.vo.request.RequestBodyTrace;
import com.aiops.api.entity.vo.response.TraceGraph;
import com.aiops.api.entity.vo.response.TraceSpan;
import com.aiops.api.service.trace.TraceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-25 13:38
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = {"调用链数据查询"})
@RequestMapping("/trace")
@RestController
public class TraceController {

    private final TraceService traceService;

    @ApiOperation(value = "查询trace列表, 可分页")
    @PostMapping("/trace")
    public TraceGraph queryTraces(
            @RequestBody @Validated({NeedIdGroup.class}) RequestBodyTrace requestBodyTrace
    ) {
        return traceService.queryTracesInfo(requestBodyTrace);
    }


    @ApiOperation(value = "根据traceId查询所属span")
    @GetMapping("/span")
    public List<TraceSpan> querySpan(
            @RequestParam("traceId")
            @NotNull(message = "traceId不能为空")
                    Integer traceId
    ) {
        return traceService.queryTraceSpans(traceId);
    }
}

package com.aiops.api.controller;

import com.aiops.api.common.validation.NeedIdGroup;
import com.aiops.api.entity.vo.request.CommonRequestBodyKpi;
import com.aiops.api.entity.vo.request.CommonRequestBodyTrace;
import com.aiops.api.entity.vo.response.TraceGraph;
import com.aiops.api.entity.vo.response.TraceSpan;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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


    @ApiOperation(value = "查询trace列表, 可分页")
    @PostMapping("/trace")
    public TraceGraph queryTraces(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBodyTrace commonRequestBodyTrace
    ) {

        return null;
    }


    @ApiOperation(value = "根据traceId查询所属span")
    @GetMapping("/span")
    public List<TraceSpan> querySpan(
            @RequestParam("traceId") Integer traceId
    ) {

        return null;
    }
}

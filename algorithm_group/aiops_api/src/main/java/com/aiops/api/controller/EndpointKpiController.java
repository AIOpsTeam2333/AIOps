package com.aiops.api.controller;

import com.aiops.api.common.validation.NeedIdGroup;
import com.aiops.api.entity.vo.request.CommonRequestBody;
import com.aiops.api.entity.vo.response.EndpointKpiAll;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 11:13
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = {"端点指标数据查询"})
@RestController
@RequestMapping("/endpoint")
public class EndpointKpiController {

    @ApiOperation(value = "端点指标数据")
    @PostMapping("/")
    public EndpointKpiAll endpointKpiAllData(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBody commonRequestBody,
            BindingResult bindingResult
    ) {
        return new EndpointKpiAll();
    }

}

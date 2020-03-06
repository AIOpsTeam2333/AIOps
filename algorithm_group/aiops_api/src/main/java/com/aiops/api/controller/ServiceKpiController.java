package com.aiops.api.controller;

import com.aiops.api.common.validation.NeedIdGroup;
import com.aiops.api.entity.vo.request.CommonRequestBody;
import com.aiops.api.entity.vo.response.ServiceKpiAll;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 11:11
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = {"服务指标数据查询"})
@RestController
@RequestMapping("/service")
public class ServiceKpiController {

    @ApiOperation(value = "服务指标数据")
    @PostMapping("/")
    public ServiceKpiAll serviceKpiAllData(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBody commonRequestBody,
            BindingResult bindingResult
    ) {
        return new ServiceKpiAll();
    }

}

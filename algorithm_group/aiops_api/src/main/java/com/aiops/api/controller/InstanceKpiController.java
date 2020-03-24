package com.aiops.api.controller;

import com.aiops.api.common.validation.NeedIdGroup;
import com.aiops.api.entity.vo.request.CommonRequestBodyKpi;
import com.aiops.api.entity.vo.response.InstanceKpiAll;
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
 * @create 2020-03-06 11:21
 **/
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = {"实例指标数据查询"})
@RestController
@RequestMapping("/instance")
public class InstanceKpiController {

    @ApiOperation(value = "实例指标视图")
    @PostMapping("/")
    public InstanceKpiAll instanceKpiAllData(
            @RequestBody @Validated({NeedIdGroup.class}) CommonRequestBodyKpi commonRequestBodyKpi,
            BindingResult bindingResult
    ) {
        return new InstanceKpiAll();
    }

}

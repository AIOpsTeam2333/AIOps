package com.aiops.api.controller;

import com.aiops.api.dao.ServiceNodeDao;
import com.aiops.api.dao.ServiceEndpointDao;
import com.aiops.api.entity.dto.GlobalBrief;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 9:56
 **/
@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ResponseBody
@Api(tags = {"全局指标数据查询"})
@RequestMapping("/global")
public class GlobalKpiController {

    private final ServiceEndpointDao serviceEndpointDao;
    private final ServiceNodeDao serviceNodeDao;

    @ApiOperation(value = "全局视图")
    @GetMapping("/globalBrief")
    public GlobalBrief globalBrief() {
        GlobalBrief globalBrief = new GlobalBrief();
        globalBrief.setNumOfEndpoint(serviceEndpointDao.countAll());
        globalBrief.setNumOfService(serviceNodeDao.countAllService());
        globalBrief.setNumOfDatabase(serviceNodeDao.countAllDatabase());
        return globalBrief;
    }
}

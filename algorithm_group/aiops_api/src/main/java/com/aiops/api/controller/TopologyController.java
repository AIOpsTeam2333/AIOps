package com.aiops.api.controller;

import com.aiops.api.entity.vo.request.RequestBodyTopology;
import com.aiops.api.entity.vo.response.Topology;
import com.aiops.api.service.topology.service.ServiceTopologyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-20 19:44
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = {"拓扑图查询"})
@RequestMapping("/topology")
@RestController
public class TopologyController {

    private final ServiceTopologyService serviceTopologyService;

    @ApiOperation(value = "查询拓扑关系")
    @PostMapping("/topology")
    public Topology queryTopo(
            @RequestBody @Validated RequestBodyTopology requestBodyTopology
    ) {
        return serviceTopologyService.getServiceTopology(requestBodyTopology.getDuration(), requestBodyTopology.getServiceId());
    }


}

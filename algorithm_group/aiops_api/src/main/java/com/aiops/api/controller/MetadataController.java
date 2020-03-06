package com.aiops.api.controller;

import com.aiops.api.dao.ServiceNodeDao;
import com.aiops.api.dao.ServiceEndpointDao;
import com.aiops.api.dao.ServiceInstanceDao;
import com.aiops.api.entity.*;
import com.aiops.api.entity.dto.ServiceEndpointSearchDto;
import com.aiops.api.entity.dto.ServiceInstanceSearchDto;
import com.aiops.api.entity.dto.ServiceNodeSearchDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-02 16:54
 **/
@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/metadata")
@ResponseBody
@Api(tags = {"元数据查询"})
public class MetadataController {

    private final ServiceNodeDao serviceNodeDao;
    private final ServiceEndpointDao serviceEndpointDao;
    private final ServiceInstanceDao serviceInstanceDao;

    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @ApiOperation(value = "获取Service元数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "service_id", value = "Service的Id", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "Service名称, 按名称查询, 部分匹配", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "node_type", value = "节点类型, 部分匹配, 如Spring", paramType = "query", dataType = "string")
    })
    @GetMapping("/getServices")
    public List<Service> getServices(
            @RequestParam(name = "service_id", required = false) Integer serviceId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "node_type", required = false) String nodeType
    ) {
        ServiceNodeSearchDto dto = new ServiceNodeSearchDto();
        dto.setNodeId(serviceId);
        dto.setName(name);
        dto.setNodeType(nodeType);
        return serviceNodeDao.selectServices(dto);
    }

    @ApiOperation(value = "获取ServiceEndpoint元数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "service_endpoint_id", value = "ServiceEndpoint的Id", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "service_id", value = "Service的Id", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "Endpoint关键字, 按名称查询, 部分匹配", paramType = "query", dataType = "string"),
    })
    @GetMapping("/getEndpoints")
    public List<ServiceEndpoint> getEndpoints(
            @RequestParam(name = "service_endpoint_id", required = false) Integer serviceEndpointId,
            @RequestParam(name = "service_id", required = false) Integer serviceId,
            @RequestParam(name = "keyword", required = false) String name
    ) {
        ServiceEndpointSearchDto dto = new ServiceEndpointSearchDto();
        dto.setServiceEndpointId(serviceEndpointId);
        dto.setServiceId(serviceId);
        dto.setName(name);
        return serviceEndpointDao.queryServiceEndpoint(dto);
    }

    @ApiOperation(value = "获取ServiceInstance元数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "service_instance_id", value = "ServiceInstance的Id", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "service_id", value = "Service的Id", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "Service名称, 按名称查询, 部分匹配", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "language", value = "程序使用的语言, 如JAVA等", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "instance_uuid", value = "实例uuid", paramType = "query", dataType = "string"),
    })
    @GetMapping("/getServiceInstances")
    public List<ServiceInstance> getServiceInstances(
            @RequestParam(name = "service_instance_id", required = false) Integer serviceInstaceId,
            @RequestParam(name = "service_id", required = false) Integer serviceId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "language", required = false) String language,
            @RequestParam(name = "instance_uuid", required = false) String instanceUuid
    ) {
        ServiceInstanceSearchDto dto = new ServiceInstanceSearchDto();
        dto.setServiceInstanceId(serviceInstaceId);
        dto.setServiceId(serviceId);
        dto.setName(name);
        dto.setLanguage(language);
        dto.setUuid(instanceUuid);
        return serviceInstanceDao.queryServiceInstance(dto);
    }

    @ApiOperation(value = "获取database数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "database_id", value = "Database的Id", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "Database名称, 按名称查询, 部分匹配", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "node_type", value = "节点类型, 部分匹配, 如mysql", paramType = "query", dataType = "string")
    })
    @GetMapping("/getDatabases")
    public List<Database> getDatabases(
            @RequestParam(name = "database_id", required = false) Integer databaseId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "node_type", required = false) String nodeType
    ) {
        ServiceNodeSearchDto dto = new ServiceNodeSearchDto();
        dto.setNodeId(databaseId);
        dto.setName(name);
        dto.setNodeType(nodeType);
        return serviceNodeDao.selectDatabase(dto);
    }
}
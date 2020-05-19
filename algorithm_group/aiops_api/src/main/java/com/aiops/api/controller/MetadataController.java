package com.aiops.api.controller;

import com.aiops.api.entity.po.Database;
import com.aiops.api.entity.po.Service;
import com.aiops.api.entity.po.Endpoint;
import com.aiops.api.entity.po.Instance;
import com.aiops.api.service.metadata.MetadataService;
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

    private final MetadataService metadataService;

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
        return metadataService.getServices(serviceId, name, nodeType);
    }

    @ApiOperation(value = "获取ServiceEndpoint元数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "service_endpoint_id", value = "ServiceEndpoint的Id", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "service_id", value = "Service的Id", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "Endpoint关键字, 按名称查询, 部分匹配", paramType = "query", dataType = "string"),
    })
    @GetMapping("/getEndpoints")
    public List<Endpoint> getEndpoints(
            @RequestParam(name = "service_endpoint_id", required = false) Integer serviceEndpointId,
            @RequestParam(name = "service_id", required = false) Integer serviceId,
            @RequestParam(name = "keyword", required = false) String name
    ) {
        return metadataService.getEndpoints(serviceEndpointId, serviceId, name);
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
    public List<Instance> getServiceInstances(
            @RequestParam(name = "service_instance_id", required = false) Integer serviceInstanceId,
            @RequestParam(name = "service_id", required = false) Integer serviceId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "language", required = false) String language,
            @RequestParam(name = "instance_uuid", required = false) String instanceUuid
    ) {
        return metadataService.getServiceInstances(serviceInstanceId, serviceId, name, language, instanceUuid);
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
        return metadataService.getDatabases(databaseId, name, nodeType);
    }
}

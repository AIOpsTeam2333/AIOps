package com.aiops.api.controller;

import com.aiops.api.dao.ServiceContainerInfoDao;
import com.aiops.api.dao.ServiceInfoDao;
import com.aiops.api.entity.ServiceContainerInfo;
import com.aiops.api.entity.ServiceInfo;
import com.aiops.api.entity.dto.ServiceBasicInfoDto;
import com.aiops.api.entity.dto.ServiceContainerInfoSearchDto;
import com.aiops.api.entity.dto.ServiceInfoSearchDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/service")
@ResponseBody
@Api(tags = {"Service查询"})
public class ServiceController {

    private final ServiceContainerInfoDao serviceContainerInfoDao;
    private final ServiceInfoDao serviceInfoDao;

    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @Transactional
    @ApiOperation(value = "获取Service相关的概览")
    @GetMapping("/info")
    public ServiceBasicInfoDto getBasicInfo() {
        ServiceBasicInfoDto result = new ServiceBasicInfoDto();
        result.setCurrentRunningServiceContainerCount(serviceContainerInfoDao.selectCurrentRunningContainerCount());
        result.setTotalServiceCount(serviceInfoDao.selectTotalServiceCount());
        return result;
    }

    @Transactional
    @ApiOperation(value = "获取Service信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "service_id", value = "Service的Id", dataType = "integer", paramType = "query", example = "1"),
            @ApiImplicitParam(name = "name", value = "Service名称, 按名称查询, 部分匹配", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "service_container_id", value = "Service所属的Container的Id", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "add_time_from", value = "添加时间筛选起点, 格式为yyyy-MM-dd HH:mm:ss", paramType = "query", dataType = "date", format = "yyyy-MM-dd HH:mm:ss"),
            @ApiImplicitParam(name = "add_time_to", value = "添加时间筛选终点, 格式为yyyy-MM-dd HH:mm:ss", paramType = "query", dataType = "date", format = "yyyy-MM-dd HH:mm:ss")
    })
    @GetMapping("/service")
    public List<ServiceInfo> getServiceInfos(
            @RequestParam(name = "service_id", required = false) Integer serviceId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "service_container_id", required = false) String serviceContainerId,
            @RequestParam(name = "add_time_from", required = false) Date addTimeFrom,
            @RequestParam(name = "add_time_to", required = false) Date addTimeTo
    ) {
        ServiceInfoSearchDto dto = new ServiceInfoSearchDto();
        dto.setServiceId(serviceId);
        dto.setName(name);
        dto.setServiceContainerId(serviceContainerId);
        dto.setAddTimeFrom(addTimeFrom);
        dto.setAddTimeFrom(addTimeTo);
        return serviceInfoDao.selectServiceInfo(dto);
    }

    @Transactional
    @ApiOperation(value = "获取ServiceContainer信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "service_container_id", value = "Container的Id", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "os", value = "Container的操作系统,可输入系统名前缀和版本号,不区分大小写", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "ip", value = "ip地址", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "add_time_from", value = "添加时间筛选起点, 格式为yyyy-MM-dd HH:mm:ss", paramType = "query", dataType = "date", format = "yyyy-MM-dd HH:mm:ss"),
            @ApiImplicitParam(name = "add_time_to", value = "添加时间筛选终点, 格式为yyyy-MM-dd HH:mm:ss", paramType = "query", dataType = "date", format = "yyyy-MM-dd HH:mm:ss"),
            @ApiImplicitParam(name = "is_in_use", value = "是否还在使用", paramType = "query", dataType = "boolean"),
            @ApiImplicitParam(name = "deprecated_time_from", value = "弃用时间筛选起点, 格式为yyyy-MM-dd HH:mm:ss", paramType = "query", dataType = "date", format = "yyyy-MM-dd HH:mm:ss"),
            @ApiImplicitParam(name = "deprecated_time_to", value = "弃用时间筛选终点, 格式为yyyy-MM-dd HH:mm:ss", paramType = "query", dataType = "date", format = "yyyy-MM-dd HH:mm:ss")
    })
    @GetMapping("/container")
    public List<ServiceContainerInfo> getServiceContainerInfos(
            @RequestParam(name = "service_container_id", required = false) String id,
            @RequestParam(name = "os", required = false) String os,
            @RequestParam(name = "ip", required = false) String ip,
            @RequestParam(name = "add_time_from", required = false) Date addTimeFrom,
            @RequestParam(name = "add_time_to", required = false) Date addTimeTo,
            @RequestParam(name = "is_in_use", required = false) Boolean isInUse,
            @RequestParam(name = "deprecated_time_from", required = false) Date deprecatedTimeFrom,
            @RequestParam(name = "deprecated_time_to", required = false) Date deprecatedTimeTo
    ) {
        ServiceContainerInfoSearchDto dto = new ServiceContainerInfoSearchDto();
        dto.setServiceContainerId(id);
        dto.setOs(os);
        dto.setIp(ip);
        dto.setAddTimeFrom(addTimeFrom);
        dto.setAddTimeTo(addTimeTo);
        dto.setIsInUse(isInUse);
        dto.setDeprecatedTimeFrom(deprecatedTimeFrom);
        dto.setDeprecatedTimeTo(deprecatedTimeTo);
        return serviceContainerInfoDao.selectServiceContainerInfosByPrototype(dto);
    }
}

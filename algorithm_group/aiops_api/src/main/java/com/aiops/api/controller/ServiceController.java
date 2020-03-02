package com.aiops.api.controller;

import com.aiops.api.dao.ServiceContainerInfoDao;
import com.aiops.api.dao.ServiceInfoDao;
import com.aiops.api.entity.ServiceContainerInfo;
import com.aiops.api.entity.ServiceInfo;
import com.aiops.api.entity.dto.ServiceContainerInfoSearchDto;
import com.aiops.api.entity.dto.ServiceInfoSearchDto;
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
@RequestMapping("/service")
public class ServiceController {

    private final ServiceContainerInfoDao serviceContainerInfoDao;
    private final ServiceInfoDao serviceInfoDao;

    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }


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

    @GetMapping("/container")
    @ResponseBody
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

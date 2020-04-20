package com.aiops.processdata.service.Impl;

import com.aiops.processdata.dao.ServiceRepository;
import com.aiops.processdata.entity.service.Service_Data;
import com.aiops.processdata.entity.service.Service_Info;
import com.aiops.processdata.entity.service.Service_InfoList;
import com.aiops.processdata.po.service.ServicePO;
import com.aiops.processdata.service.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/7 22:05
 */
@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public List<ServicePO> save(Service_Data service_data) {
        List<ServicePO> servicePOList = new ArrayList<>();
        Service_InfoList service_infoList = service_data.getData();
        for (Service_Info service_info : service_infoList.getService_infoList()) {
            servicePOList.add(this.serviceRepository.insertService(service_info));
        }
        return servicePOList;
    }

    @Override
    public Integer findById(String pre_id) {
        ServicePO po = serviceRepository.findById(pre_id);
        return po == null ? -1 : po.getId();
    }

    @Override
    public Integer findByName(String name) {
        ServicePO po = serviceRepository.findByName(name);
        return po == null ? -1 : po.getId();
    }
}

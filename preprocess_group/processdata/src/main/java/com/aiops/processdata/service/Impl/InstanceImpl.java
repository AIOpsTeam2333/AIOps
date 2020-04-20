package com.aiops.processdata.service.Impl;

import com.aiops.processdata.dao.InstanceRepository;
import com.aiops.processdata.entity.Instance.ServiceInstance_Data;
import com.aiops.processdata.entity.Instance.ServiceInstance_Info;
import com.aiops.processdata.entity.Instance.ServiceInstance_InfoList;
import com.aiops.processdata.po.instance.InstancePO;
import com.aiops.processdata.service.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/10 20:40
 */
@Service
public class InstanceImpl implements Instance {
    @Autowired
    private InstanceRepository instanceRepository;

    @Override
    public List<InstancePO> save(ServiceInstance_Data serviceInstance_data) {
        ServiceInstance_InfoList serviceInstance_infoList = serviceInstance_data.getData();
        List<InstancePO> instancePOList = new ArrayList<>();
        for (ServiceInstance_Info serviceInstance_info : serviceInstance_infoList.getServiceInstance_infos()) {
            InstancePO instancePO = instanceRepository.insertInstance(serviceInstance_info);
            instancePOList.add(instancePO);
        }
        return instancePOList;
    }
}

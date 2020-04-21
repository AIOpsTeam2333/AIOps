package com.aiops.processdata.service;

import com.aiops.processdata.config.Config;
import com.aiops.processdata.entity.Instance.ServiceInstance_Data;
import com.aiops.processdata.po.instance.InstancePO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/10 20:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class InstanceTest {
    @Autowired
    private Init init;
    @Autowired
    private Instance instance;

    @Test
    public void save() {
        ServiceInstance_Data serviceInstance_data = init.initServiceInstance();
        List<InstancePO> instancePOList = instance.save(serviceInstance_data);
        System.out.println(instancePOList);
    }
}

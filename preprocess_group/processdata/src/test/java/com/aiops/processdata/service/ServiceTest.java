package com.aiops.processdata.service;

import com.aiops.processdata.config.Config;
import com.aiops.processdata.entity.service.Service_Data;
import com.aiops.processdata.po.service.ServicePO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/7 22:13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class ServiceTest {
    @Autowired
    private Init init;

    @Autowired
    private Service service;

    @Test
    public void save(){
        Service_Data service_data=init.initService();
        List<ServicePO> servicePOList=service.save(service_data);
        System.out.println(servicePOList);
    }
    @Test
    public void findById(){
        Integer t=service.findById("11");
        System.out.println(t);
        Integer t2=service.findById("100");
        System.out.println(t2);
    }

    @Test
    public void findByName(){
        Integer t=service.findByName("Config");
        System.out.println(t);
        Integer t2=service.findByName("");
        System.out.println(t2);
    }
}

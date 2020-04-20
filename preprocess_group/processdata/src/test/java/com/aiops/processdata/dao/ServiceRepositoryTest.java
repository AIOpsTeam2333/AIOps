package com.aiops.processdata.dao;

import com.aiops.processdata.config.Config;
import com.aiops.processdata.po.service.ServicePO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/9 18:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class ServiceRepositoryTest {

    @Autowired
    private ServiceRepository repository;

    @Test
    public void findById(){
        ServicePO servicePO=repository.findById("11");
        System.out.println(servicePO);
        ServicePO servicePO2=repository.findById("200");
        System.out.println(servicePO2);
    }
    @Test
    public void findByName(){
        ServicePO servicePO=repository.findByName("Config");
        System.out.println(servicePO);
        ServicePO servicePO2=repository.findByName("");
        System.out.println(servicePO2);
    }
}

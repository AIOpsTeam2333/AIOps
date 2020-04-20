package com.aiops.processdata.service;

import com.aiops.processdata.config.Config;
import com.aiops.processdata.entity.endpoint.Endpoint_Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/10 0:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class EndpointTest {
    @Autowired
    private Init init;

    @Autowired
    private Endpoint endpoint;

    @Test
    public void save(){
        Endpoint_Data endpoint_data=init.initEndpoint();
        System.out.println(endpoint.save(endpoint_data));
    }

    @Test
    public void findById(){
        System.out.println(endpoint.findById("9"));
        System.out.println(endpoint.findById("5"));
    }

    @Test
    public void findByName(){
        System.out.println(endpoint.findByName("/"));
        System.out.println(endpoint.findByName(""));
    }
}

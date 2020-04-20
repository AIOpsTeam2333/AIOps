package com.aiops.processdata.dao;

import com.aiops.processdata.config.Config;
import com.aiops.processdata.entity.endpoint.Endpoint_Data;
import com.aiops.processdata.entity.endpoint.Endpoint_Info;
import com.aiops.processdata.po.endpoint.EndpointPO;
import com.aiops.processdata.service.Init;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/9 22:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class EndpointRepositoryTest {

    @Autowired
    private Init init;

    @Autowired
    private EndpointRepository endpointRepository;

    @Test
    public void insert(){
        Endpoint_Data endpoint_data=init.initEndpoint();
        List<EndpointPO> endpointPOList=new ArrayList<>();
        for(Endpoint_Info endpoint_info:endpoint_data.getEndpoint_infoList().getEndpoint_infoList()){

            EndpointPO endpointPO=endpointRepository.insertEndpoint(endpoint_info);
            endpointPOList.add(endpointPO);

        }
        System.out.println(endpointPOList);
    }

    @Test
    public void findById(){
        EndpointPO endpointPO=endpointRepository.findById("2");
        System.out.println(endpointPO);
    }

    @Test
    public void findByName(){
        EndpointPO endpointPO=endpointRepository.findByName("{POST}/");
        EndpointPO endpointPO2=endpointRepository.findByName("/");
        EndpointPO endpointPO3=endpointRepository.findByName("abc/");
        System.out.println(endpointPO);
        System.out.println(endpointPO2);
        System.out.println(endpointPO3);
    }
}

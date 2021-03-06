package com.aiops.api;

import com.aiops.api.common.enums.StatisticsStep;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.Topology;
import com.aiops.api.service.topology.endpoint.EndpointTopologyService;
import com.aiops.api.service.topology.service.ServiceTopologyService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-24 17:27
 */
@SpringBootTest
public class TopologyTests {

    @Autowired
    private EndpointTopologyService endpointTopologyService;
    @Autowired
    private ServiceTopologyService serviceTopologyService;

    @Test
    @Ignore
    public void endpointTopoTest1() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        Duration duration = new Duration();
        duration.setStart(simpleDateFormat.parse("201001"));
        duration.setEnd(simpleDateFormat.parse("202012"));
        duration.setStep(StatisticsStep.MONTH);
        Integer endpointId = 21;

        Topology endpointTopology = endpointTopologyService.selectEndpointTopology(duration, endpointId);
        System.out.println(endpointTopology);
    }

    @Test
    @Ignore
    public void serviceTopoTest1() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        Duration duration = new Duration();
        duration.setStart(simpleDateFormat.parse("201001"));
        duration.setEnd(simpleDateFormat.parse("202012"));
        duration.setStep(StatisticsStep.MONTH);
        Integer serviceId = 1;

        Topology serviceTopology = serviceTopologyService.getServiceTopology(duration, serviceId);
        System.out.println(serviceTopology);
    }

}

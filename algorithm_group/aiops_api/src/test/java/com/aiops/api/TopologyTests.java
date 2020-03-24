package com.aiops.api;

import com.aiops.api.common.enums.StatisticsStep;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.EndpointTopology;
import com.aiops.api.service.topology.endpoint.EndpointTopologyService;
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

    @Test
    public void endpointTopoTest1() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        Duration duration = new Duration();
        duration.setStart(simpleDateFormat.parse("201001"));
        duration.setEnd(simpleDateFormat.parse("202012"));
        duration.setStep(StatisticsStep.MONTH);
        Integer endpointId = 1;

        EndpointTopology endpointTopology = endpointTopologyService.selectEndpointTopology(duration, endpointId);
        System.out.println(endpointTopology);
    }

}

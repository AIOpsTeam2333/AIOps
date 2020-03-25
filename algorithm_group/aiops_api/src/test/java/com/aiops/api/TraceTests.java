package com.aiops.api;

import com.aiops.api.common.enums.StatisticsStep;
import com.aiops.api.entity.vo.request.Duration;
import com.aiops.api.entity.vo.response.TracesGraph;
import com.aiops.api.service.trace.TraceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-25 16:13
 */
@SpringBootTest
public class TraceTests {

    @Autowired
    private TraceService traceService;

    @Test
    public void TraceTest1() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        Duration duration = new Duration();
        duration.setStart(simpleDateFormat.parse("201001"));
        duration.setEnd(simpleDateFormat.parse("202012"));
        duration.setStep(StatisticsStep.MONTH);
        Integer endpointId = 1;
        TracesGraph tracesGraph = traceService.queryTracesInfoByEndpointId(duration, endpointId);
        System.out.println(tracesGraph);
    }

}

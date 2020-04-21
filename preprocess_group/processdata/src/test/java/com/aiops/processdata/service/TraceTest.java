package com.aiops.processdata.service;

import com.aiops.processdata.config.Config;
import com.aiops.processdata.entity.trace.Trace_Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 0:07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class TraceTest {
    @Autowired
    private Trace trace;
    @Autowired
    private Init init;

    @Test
    public void save() {
        Trace_Data trace_data = init.initTrace();
        trace.save(trace_data);
    }

    @Test
    public void findTraceById() {
        Integer traceId = trace.findTraceById("2.76.15867606640990505");
        System.out.println(traceId);
        Integer traceId2 = trace.findTraceById("");
        System.out.println(traceId2);
    }

    @Test
    public void findSegmentById() {
        Integer segmentId = trace.findSegmentById("2.76.15867606640990505/2.76.15867606640990504");
        System.out.println(segmentId);
        Integer segmentId2 = trace.findSegmentById("");
        System.out.println(segmentId2);
    }
}

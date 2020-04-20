package com.aiops.processdata.dao;

import com.aiops.processdata.config.Config;
import com.aiops.processdata.entity.trace.Trace_Data;
import com.aiops.processdata.entity.trace.Trace_Info;
import com.aiops.processdata.po.trace.SegmentPO;
import com.aiops.processdata.po.trace.TracePO;
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
 * @date 2020/4/14 23:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class TraceAndSegmentRepositoryTest {
    @Autowired
    private Init init;
    @Autowired
    private TraceAndSegmentRepository traceAndSegmentRepository;

    @Test
    public void insertTraceAndSegment(){
        Trace_Data trace_data=init.initTrace();
        List<TracePO> tracePOList=new ArrayList<>();
        List<SegmentPO> segmentPOList=new ArrayList<>();
        for(Trace_Info trace_info:trace_data.getData().getTrace_infoList().getTraces()){
            TracePO tracePO=traceAndSegmentRepository.insertTrace(trace_info);
            SegmentPO segmentPO=traceAndSegmentRepository.insertSegment(trace_info);
            tracePOList.add(tracePO);
            segmentPOList.add(segmentPO);
        }
        System.out.println(tracePOList);
        System.out.println(segmentPOList);
    }


}

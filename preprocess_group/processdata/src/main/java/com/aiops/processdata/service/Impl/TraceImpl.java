package com.aiops.processdata.service.Impl;

import com.aiops.processdata.dao.TraceAndSegmentRepository;
import com.aiops.processdata.entity.trace.Trace_Data;
import com.aiops.processdata.entity.trace.Trace_Info;
import com.aiops.processdata.po.trace.SegmentPO;
import com.aiops.processdata.po.trace.TracePO;
import com.aiops.processdata.service.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/15 0:00
 */
@Service
public class TraceImpl implements Trace {
    @Autowired
    private TraceAndSegmentRepository traceAndSegmentRepository;


    @Override
    public void save(Trace_Data trace_data) {
        List<Trace_Info> trace_infoList = trace_data.getData().getTrace_infoList().getTraces();
        for (Trace_Info trace_info : trace_infoList) {
            traceAndSegmentRepository.insertTrace(trace_info);
            traceAndSegmentRepository.insertSegment(trace_info);
        }
    }

    @Override
    public Integer findTraceById(String pre_id) {
        TracePO tracePO = traceAndSegmentRepository.findTraceById(pre_id);
        return tracePO == null ? -1 : tracePO.getTraceId();
    }

    @Override
    public Integer findSegmentById(String pre_id) {
        SegmentPO segmentPO = traceAndSegmentRepository.findSegmentById(pre_id);
        return segmentPO == null ? -1 : segmentPO.getSegmentId();
    }
}

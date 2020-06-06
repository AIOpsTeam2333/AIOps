package com.aiops.processdata.service.Impl;

import com.aiops.processdata.entity.Instance.ServiceInstance_Data;
import com.aiops.processdata.entity.endpoint.Endpoint_Data;
import com.aiops.processdata.entity.service.Service_Data;
import com.aiops.processdata.entity.span.Span_Data;
import com.aiops.processdata.entity.trace.Trace_Data;
import com.aiops.processdata.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/5/20 21:31
 */
@org.springframework.stereotype.Service
public class PreprocessAndStoreImpl implements PreprocessAndStore {

    @Autowired
    private Service service;

    @Autowired
    private Endpoint endpoint;

    @Autowired
    private Instance instance;

    @Autowired
    private Trace trace;

    @Autowired
    private Span span;


    @Override
    public boolean saveInstance(String instanceJson) {
        ServiceInstance_Data serviceInstance_data=new Gson().fromJson(instanceJson, ServiceInstance_Data.class);
        instance.save(serviceInstance_data);
        return true;
    }

    @Override
    public boolean saveService(String serviceJson) {
        Service_Data service_data=new Gson().fromJson(serviceJson,Service_Data.class);
        service.save(service_data);
        return true;
    }

    @Override
    public boolean saveEndpoint(String endpointJson) {
        Endpoint_Data endpoint_data=new Gson().fromJson(endpointJson,Endpoint_Data.class);
        endpoint.save(endpoint_data);
        return true;
    }

    @Override
    public boolean saveTrace(String traceJson) {
        Trace_Data trace_data=new Gson().fromJson(traceJson,Trace_Data.class);
        trace.save(trace_data);
        return true;
    }

    @Override
    public boolean saveSpan(String spanJson) {
        Span_Data span_data=new Gson().fromJson(spanJson,Span_Data.class);
        span.save(span_data);
        return true;
    }
}

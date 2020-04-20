package com.aiops.processdata.service.Impl;

import com.aiops.processdata.entity.Instance.ServiceInstance_Data;
import com.aiops.processdata.entity.endpoint.Endpoint_Data;
import com.aiops.processdata.entity.service.Service_Data;
import com.aiops.processdata.entity.span.Span_Data;
import com.aiops.processdata.entity.trace.Trace_Data;
import com.aiops.processdata.service.Init;
import com.aiops.processdata.service.JsonToBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/4 22:26
 */
@Service
public class InitImpl implements Init {
    @Autowired
    private ResourceBundle bundle;

    @Autowired
    private JsonToBean jsonToBean;


    @Override
    public Span_Data initSpan() {
        //读取配置文件
        String filepath = getClass().getClassLoader().getResource(bundle.getString("SPAN_FILEPATH")).getPath();
        Span_Data span_data = jsonToBean.convert(filepath, Span_Data.class);
        System.out.println("span对象读取成功，共有 " + span_data.getData().getQueryTrace().getSpans().size() + " 条span信息");
        return span_data;
    }

    @Override
    public Endpoint_Data initEndpoint() {
        //读取配置文件
        String filepath = getClass().getClassLoader().getResource(bundle.getString("ENDPOINT_FILEPATH")).getPath();

        Endpoint_Data endpoint_data = jsonToBean.convert(filepath, Endpoint_Data.class);
        System.out.println("endpoint对象读取成功，共有 " + endpoint_data.getEndpoint_infoList().getEndpoint_infoList().size() + " 条endpoint信息");
        return endpoint_data;
    }

    @Override
    public Service_Data initService() {
        String filepath = getClass().getClassLoader().getResource(bundle.getString("SERVICE_FILEPATH")).getPath();

        Service_Data service_data = jsonToBean.convert(filepath, Service_Data.class);
        System.out.println("service对象读取成功，共有 " + service_data.getData().getService_infoList().size() + " 条service信息");
        return service_data;
    }

    @Override
    public ServiceInstance_Data initServiceInstance() {
        String filepath = getClass().getClassLoader().getResource(bundle.getString("SERVICEINSTANCE_FILEPATH")).getPath();

        ServiceInstance_Data serviceInstance_data = jsonToBean.convert(filepath, ServiceInstance_Data.class);
        System.out.println("serviceInstance对象读取成功，共有 " + serviceInstance_data.getData().getServiceInstance_infos().size() + " 条serviceinstance信息");
        return serviceInstance_data;
    }

    @Override
    public Trace_Data initTrace() {
        String filepath = getClass().getClassLoader().getResource(bundle.getString("TRACE_FILEPATH")).getPath();

        Trace_Data trace_data = jsonToBean.convert(filepath, Trace_Data.class);
        System.out.println("trace对象读取成功，共有 " + trace_data.getData().getTrace_infoList().getTraces().size() + " 条trace信息");
        return trace_data;
    }
}

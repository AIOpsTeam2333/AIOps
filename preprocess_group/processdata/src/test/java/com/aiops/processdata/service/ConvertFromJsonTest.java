package com.aiops.processdata.service;

import com.aiops.processdata.config.Config;
import com.aiops.processdata.entity.Instance.ServiceInstance_Data;
import com.aiops.processdata.entity.endpoint.Endpoint_Data;
import com.aiops.processdata.entity.service.Service_Data;
import com.aiops.processdata.entity.span.Span_Data;
import com.aiops.processdata.entity.trace.Trace_Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ResourceBundle;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/4 14:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class ConvertFromJsonTest {
    @Autowired
    private ResourceBundle bundle;

    @Autowired
    private JsonToBean jsonToBean;

    @Autowired
    private Init init;

    @Test
    public void testEndpoint() {
        //读取配置文件
        String filepath = getClass().getClassLoader().getResource(bundle.getString("ENDPOINT_FILEPATH")).getPath();

        Endpoint_Data endpoint_data = jsonToBean.convert(filepath, Endpoint_Data.class);
        System.out.println("endpoint转化个数为：" + endpoint_data.getEndpoint_infoList().getEndpoint_infoList().size());
    }

    @Test
    public void testSpan() {
        //读取配置文件
        String filepath = getClass().getClassLoader().getResource(bundle.getString("SPAN_FILEPATH")).getPath();

        Span_Data span_data = jsonToBean.convert(filepath, Span_Data.class);
        System.out.println("span转化个数为：" + span_data.getData().getQueryTrace().getSpans().size());
    }

    @Test
    public void testService() {
        String filepath = getClass().getClassLoader().getResource(bundle.getString("SERVICE_FILEPATH")).getPath();
        Service_Data service_data = jsonToBean.convert(filepath, Service_Data.class);
        System.out.println("service转化个数为：" + service_data.getData().getService_infoList().size());
    }

    @Test
    public void testServiceInstance() {
        String filepath = getClass().getClassLoader().getResource(bundle.getString("SERVICEINSTANCE_FILEPATH")).getPath();
        ServiceInstance_Data serviceInstance_data = jsonToBean.convert(filepath, ServiceInstance_Data.class);
        System.out.println("instance转化个数为：" + serviceInstance_data.getData().getServiceInstance_infos().size());
    }

    @Test
    public void testTrace() {
        Trace_Data trace_data = init.initTrace();
        System.out.println("trace转化个数为：" + trace_data.getData().getTrace_infoList().getTraces().size());
    }
}

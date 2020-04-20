package com.aiops.processdata.run;

import com.aiops.processdata.config.Config;
import com.aiops.processdata.entity.Instance.ServiceInstance_Data;
import com.aiops.processdata.entity.endpoint.Endpoint_Data;
import com.aiops.processdata.entity.service.Service_Data;
import com.aiops.processdata.entity.span.Span_Data;
import com.aiops.processdata.entity.trace.Trace_Data;
import com.aiops.processdata.po.span.SpanPO;
import com.aiops.processdata.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/3/27 13:56
 */

public class Runner {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        Init init = (Init) applicationContext.getBean("initImpl");
        Span_Data span_data = init.initSpan();
        Endpoint_Data endpoint_data = init.initEndpoint();
        Service_Data service_data = init.initService();
        ServiceInstance_Data serviceInstance_data = init.initServiceInstance();
        Trace_Data trace_data = init.initTrace();

        Service service = (Service) applicationContext.getBean("serviceImpl");
        Instance instance = (Instance) applicationContext.getBean("instanceImpl");
        Endpoint endpoint = (Endpoint) applicationContext.getBean("endpointImpl");
        Trace trace = (Trace) applicationContext.getBean("traceImpl");
        Span span = (Span) applicationContext.getBean("spanImpl");

        service.save(service_data);
        endpoint.save(endpoint_data);
        instance.save(serviceInstance_data);
        trace.save(trace_data);
        List<SpanPO> spanPOList=span.save(span_data);
        Set<SpanPO> spanPOSet=new HashSet<>(spanPOList);
        System.out.println("SpanPO的数量为 "+spanPOSet.size());
        int counttag=0;
        for(SpanPO spanPO:spanPOSet){
            if (spanPO.getTagPOList().getKVList().size()!=0) counttag++;
        }
        System.out.println("SpanPO中存在tag的数量为 "+counttag);
        int countError=0;
        for(SpanPO spanPO:spanPOSet){
            if (spanPO.getTraceSpanPO().isError()==true) countError++;
        }
        System.out.println("SpanPO中存在isError的数量为 "+countError);
        int countlog=0;
        for(SpanPO spanPO:spanPOSet){
            if (spanPO.getLogPOList().size()!=0) {
                countlog++;
                System.out.println("unemptylog spanId= "+spanPO.getTraceSpanPO().getId()+" pre_id= "+spanPO.getTraceSpanPO().getSpanId());
            }
        }
        System.out.println("SpanPO中存在log的数量为 "+countlog);
        int countref=0;
        for(SpanPO spanPO:spanPOSet){
            if (spanPO.getRefPOList().size()!=0) countref++;
        }
        System.out.println("SpanPO中存在ref的数量为 "+countref);
    }


}

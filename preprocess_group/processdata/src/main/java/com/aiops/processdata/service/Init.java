package com.aiops.processdata.service;

import com.aiops.processdata.entity.Instance.ServiceInstance_Data;
import com.aiops.processdata.entity.endpoint.Endpoint_Data;
import com.aiops.processdata.entity.service.Service_Data;
import com.aiops.processdata.entity.span.Span_Data;
import com.aiops.processdata.entity.trace.Trace_Data;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/4 22:31
 */
public interface Init {
    /**
     * 初始化生成span对象
     *
     * @return json转换来的Span_Data对象
     */
    Span_Data initSpan();

    /**
     * 初始化生成endpoint对象
     *
     * @return json转换来的Endpoint_Data
     */
    Endpoint_Data initEndpoint();

    /**
     * 初始化生成service对象
     *
     * @return json转换来的Service_Data对象
     */
    Service_Data initService();

    /**
     * 初始化生成serviceInstance对象
     *
     * @return json转换来的ServiceInstance_Data对象
     */
    ServiceInstance_Data initServiceInstance();

    /**
     * 初始化生成Trace对象
     *
     * @return json转换来的Trace_Data对象
     */
    Trace_Data initTrace();
}

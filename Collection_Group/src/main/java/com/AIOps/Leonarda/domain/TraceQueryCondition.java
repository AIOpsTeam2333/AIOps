package com.AIOps.Leonarda.domain;

import com.AIOps.Leonarda.domain.trace.QueryOrder;
import com.AIOps.Leonarda.domain.trace.TraceState;

/**
 * @author: leonard lian
 * @description: Memory
 * @date: create in 02:26 2020-04-13
 */
public class TraceQueryCondition {
    private String serviceId;
    private String serviceInstanceId;
    private String traceId;
    private String endpointId;
    private String endpointName;
    private int minTraceDuration;
    private int maxTraceDuration;
    private QueryDuration queryDuration;
    private TraceState traceState;
    private QueryOrder queryOrder;
    private Paging paging;

    public String getEndpointId() {
        return endpointId;
    }

    public int getMaxTraceDuration() {
        return maxTraceDuration;
    }

    public int getMinTraceDuration() {
        return minTraceDuration;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setEndpointId(String endpointId) {
        this.endpointId = endpointId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public void setServiceInstanceId(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    public void setMinTraceDuration(int minTraceDuration) {
        this.minTraceDuration = minTraceDuration;
    }

    public void setMaxTraceDuration(int maxTraceDuration) {
        this.maxTraceDuration = maxTraceDuration;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Paging getPaging() {
        return paging;
    }

    public QueryDuration getQueryDuration() {
        return queryDuration;
    }

    public TraceState getTraceState() {
        return traceState;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public void setQueryDuration(QueryDuration queryDuration) {
        this.queryDuration = queryDuration;
    }

    public void setTraceState(TraceState traceState) {
        this.traceState = traceState;
    }

    public QueryOrder getQueryOrder() {
        return queryOrder;
    }

    public void setQueryOrder(QueryOrder queryOrder) {
        this.queryOrder = queryOrder;
    }

    public TraceQueryCondition(QueryDuration queryDuration, TraceState traceState, QueryOrder queryOrder, Paging paging){
        this.serviceId="0";
        this.queryDuration=queryDuration;
        this.traceState=traceState;
        this.paging=paging;
        this.queryOrder=queryOrder;
    }
}


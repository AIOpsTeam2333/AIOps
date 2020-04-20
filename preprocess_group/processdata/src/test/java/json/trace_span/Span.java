package json.trace_span;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Span {
    String traceId;
    String serviceCode;
    String endpointName;
    String type;
    int parentSpanId;
    String layer;

    @SerializedName("business_name")
    ArrayList<Tag> tags;
    int spanId;
    String component;
    boolean isError;
    ArrayList<Ref> refs;
    String peer;
    String segmentId;
    Long startTime;
    Long endTime;
    ArrayList<Log> logs;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getParentSpanId() {
        return parentSpanId;
    }

    public void setParentSpanId(int parentSpanId) {
        this.parentSpanId = parentSpanId;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public int getSpanId() {
        return spanId;
    }

    public void setSpanId(int spanId) {
        this.spanId = spanId;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public ArrayList<Ref> getRefs() {
        return refs;
    }

    public void setRefs(ArrayList<Ref> refs) {
        this.refs = refs;
    }

    public String getPeer() {
        return peer;
    }

    public void setPeer(String peer) {
        this.peer = peer;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<Log> logs) {
        this.logs = logs;
    }


}

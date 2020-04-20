package json.trace_span;

public class Ref {
    String traceId;
    String parentSegmentId;
    String type;
    int parentSpanId;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getParentSegmentId() {
        return parentSegmentId;
    }

    public void setParentSegmentId(String parentSegmentId) {
        this.parentSegmentId = parentSegmentId;
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
}

package com.aiops.model;

import java.sql.Timestamp;

public class MetricServiceDO {
    private String id;
    private int serviceId;
    private double value;
    private double predict;
    private Timestamp timestamp;

    public MetricServiceDO(int serviceId, double value, Timestamp timestamp) {
        this.serviceId = serviceId;
        this.value = value;
        this.predict = 0.0;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getPredict() {
        return predict;
    }

    public void setPredict(double predict) {
        this.predict = predict;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

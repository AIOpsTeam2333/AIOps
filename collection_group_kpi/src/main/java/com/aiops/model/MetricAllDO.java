package com.aiops.model;

import java.sql.Timestamp;

public class MetricAllDO {
    private String id;
    private double value;
    private double predict;
    private Timestamp timestamp;

    public MetricAllDO(double value, Timestamp timestamp) {
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

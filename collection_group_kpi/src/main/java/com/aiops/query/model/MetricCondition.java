package com.aiops.query.model;

public class MetricCondition {
    private String name;
    private String id;

    public MetricCondition(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public MetricCondition(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

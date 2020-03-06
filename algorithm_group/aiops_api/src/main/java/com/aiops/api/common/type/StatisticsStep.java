package com.aiops.api.common.type;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 10:56
 **/
public enum StatisticsStep {

    MONTH("month"),
    DAY("day"),
    HOUR("hour"),
    MINUTE("minute");

    private String name;

    StatisticsStep(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

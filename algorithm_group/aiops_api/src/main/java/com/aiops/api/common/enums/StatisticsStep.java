package com.aiops.api.common.enums;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-04 10:56
 **/
public enum StatisticsStep {

    MONTH("month", 30 * 24 * 60 * 60 * 1000L),
    DAY("day", 24 * 60 * 60 * 1000L),
    HOUR("hour", 60 * 60 * 1000L),
    MINUTE("minute", 60 * 1000L);

    private String name;
    private long step;

    StatisticsStep(String name, long step) {
        this.name = name;
        this.step = step;
    }

    public String getName() {
        return name;
    }

    public long getStep() {
        return step;
    }
}

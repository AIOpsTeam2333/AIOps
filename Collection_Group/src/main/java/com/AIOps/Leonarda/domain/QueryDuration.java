package com.AIOps.Leonarda.domain;

import com.AIOps.Leonarda.domain.common.Step;

/**
 * @author: leonard lian
 * @description: Memory
 * @date: create in 02:32 2020-04-13
 */
public class QueryDuration {
    private String start;
    private String end;
    private Step step;

    public String getEnd() {
        return end;
    }

    public String getStart() {
        return start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public QueryDuration(String start,String end, Step step){
        this.start=start;
        this.end=end;
        this.step=step;
    }
}

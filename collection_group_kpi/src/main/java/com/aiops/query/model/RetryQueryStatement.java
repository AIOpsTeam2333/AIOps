package com.aiops.query.model;

import com.aiops.query.enums.Step;

public class RetryQueryStatement extends QueryStatement {

    private int retryCount = 3;
    private String metric;
    private Step step;

    public RetryQueryStatement(QueryStatement statement, String metric, Step step){
        this.setValue(statement.getValue());
        this.setMetric(metric);
        this.setStep(step);
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
}

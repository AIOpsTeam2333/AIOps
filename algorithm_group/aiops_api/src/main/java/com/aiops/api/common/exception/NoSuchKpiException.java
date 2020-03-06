package com.aiops.api.common.exception;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 15:11
 */
public class NoSuchKpiException extends RuntimeException {

    private String kpi;

    public NoSuchKpiException(String kpi) {
        this.kpi = kpi;
    }

    public String getKpi() {
        return kpi;
    }
}

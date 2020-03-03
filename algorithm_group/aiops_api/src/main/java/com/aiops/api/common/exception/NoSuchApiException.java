package com.aiops.api.common.exception;


/**
 * @author Shuaiyu Yao
 * @create 2020-03-03 11:03
 **/
public class NoSuchApiException extends RuntimeException {

    private final String api;

    public NoSuchApiException(String api) {
        this.api = api;
    }

    public String getApi() {
        return api;
    }
}

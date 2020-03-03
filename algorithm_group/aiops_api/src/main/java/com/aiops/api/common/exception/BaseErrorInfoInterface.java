package com.aiops.api.common.exception;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-03 10:49
 **/
public interface BaseErrorInfoInterface {
    /**
     * 错误码
     */
    String getResultCode();

    /**
     * 错误描述
     */
    String getResultMsg();
}

package com.aiops.api.common.exception;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-03 10:49
 **/
public enum CommonEnum implements BaseErrorInfo {
    // 数据操作错误定义
    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400", "请求的数据格式不符!"),
    NOT_FOUND("404", "未找到API!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!");


    /**
     * 错误码
     */
    private String resultCode;

    /**
     * 错误描述
     */
    private String resultMsg;

    CommonEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }

}

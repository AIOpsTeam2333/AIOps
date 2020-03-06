package com.aiops.api.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-03 10:40
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理空指针的异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResultBody exceptionHandler(HttpServletRequest req, NullPointerException e) {
        log.error("发生空指针异常！原因是:", e);
        return ResultBody.error(CommonEnum.BODY_NOT_MATCH);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResultBody exceptionHandler(MethodArgumentTypeMismatchException e) {
        log.error("传入参数格式不符！原因是:", e);
        return ResultBody.error(CommonEnum.BODY_NOT_MATCH);
    }

    @ExceptionHandler(value = NoSuchApiException.class)
    @ResponseBody
    public ResultBody exceptionHandler(NoSuchApiException e) {
        log.error("未找到API:" + e.getApi());
        return ResultBody.error(CommonEnum.NOT_FOUND);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResultBody exceptionHandler(BindException e) {
        log.error("数据验证错误:" + e.getLocalizedMessage());
        return ResultBody.error(e.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((a, b) -> a + ", " + b)
                .orElse(""));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public ResultBody exceptionHandler(HttpMessageNotReadableException e) {
        log.error("传参错误:" + e.getLocalizedMessage());
        return ResultBody.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(value = HttpMessageConversionException.class)
    @ResponseBody
    public ResultBody exceptionHandler(HttpMessageConversionException e) {
        log.error("Http信息解析错误:" + e.getLocalizedMessage());
        return ResultBody.error(CommonEnum.BODY_NOT_MATCH);
    }

    @ExceptionHandler(value = NoSuchKpiException.class)
    @ResponseBody
    public ResultBody exceptionHandler(NoSuchKpiException e) {
        log.error("Kpi类型解析错误:" + e.getKpi());
        return ResultBody.error("kpi名称错误: " + e.getKpi());
    }

    /**
     * 处理其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBody exceptionHandler(Exception e) {
        log.error("未知异常！原因是:", e);
        return ResultBody.error(CommonEnum.INTERNAL_SERVER_ERROR);
    }
}

package com.aiops.api.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public ExceptionResultBody exceptionHandler(HttpServletRequest req, NullPointerException e) {
        log.error("发生空指针异常！原因是:", e);
        return new ExceptionResultBody(CommonEnum.BODY_NOT_MATCH);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ExceptionResultBody exceptionHandler(MethodArgumentTypeMismatchException e) {
        log.error("传入参数格式不符！原因是:", e);
        return new ExceptionResultBody(CommonEnum.BODY_NOT_MATCH);
    }

    @ExceptionHandler(value = NoSuchApiException.class)
    @ResponseBody
    public ExceptionResultBody exceptionHandler(NoSuchApiException e) {
        log.error("未找到API:" + e.getApi());
        return new ExceptionResultBody(CommonEnum.NOT_FOUND);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ExceptionResultBody exceptionHandler(BindException e) {
        log.error("数据验证错误:" + e.getLocalizedMessage());
        return new ExceptionResultBody(e.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((a, b) -> a + ", " + b)
                .orElse(""));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public ExceptionResultBody exceptionHandler(HttpMessageNotReadableException e) {
        log.error("传参错误:" + e.getLocalizedMessage());
        return new ExceptionResultBody(e.getLocalizedMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ExceptionResultBody exceptionHandler(MethodArgumentNotValidException e) {
        log.error("参数错误:" + e.getBindingResult().toString());
        return new ExceptionResultBody(e.getBindingResult().getFieldErrors().stream()
                .map(a -> a.getField() + " " + a.getDefaultMessage())
                .reduce((a, b) -> a + ", " + b)
                .orElse(""));
    }

    @ExceptionHandler(value = HttpMessageConversionException.class)
    @ResponseBody
    public ExceptionResultBody exceptionHandler(HttpMessageConversionException e) {
        log.error("Http信息解析错误:" + e.getLocalizedMessage());
        return new ExceptionResultBody(CommonEnum.BODY_NOT_MATCH);
    }

    @ExceptionHandler(value = NoSuchKpiException.class)
    @ResponseBody
    public ExceptionResultBody exceptionHandler(NoSuchKpiException e) {
        log.error("Kpi类型解析错误:" + e.getKpi());
        return new ExceptionResultBody("kpi名称错误: " + e.getKpi());
    }

    /**
     * 处理其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ExceptionResultBody exceptionHandler(Exception e) {
        log.error("未知异常！原因是:", e);
        return new ExceptionResultBody(CommonEnum.INTERNAL_SERVER_ERROR);
    }
}

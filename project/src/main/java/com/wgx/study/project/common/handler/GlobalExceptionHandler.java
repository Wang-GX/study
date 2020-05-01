package com.wgx.study.project.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public Response handleCommonException(CommonException e) {
        Response response = new Response();
        response.setMessage(e.getMessage());
        response.setCode(e.getCode());
        return response;
    }
}

package com.wgx.study.project.SpringValidator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ControllerAdvice/@RestControllerAdvice + @ExceptionHandler ：全局(controller层)级别的异常处理器
 * service层的异常全部向上抛出到controller层，再由全局异常处理器捕获，进行统一处理。
 * 如果想要直接在service层将异常统一捕获并处理，可以通过aop实现(意义不大)。
 */
@Slf4j
@ControllerAdvice
public class SpringValidatorGlobalExceptionHandler {
    /**
     * 对方法参数校验异常处理方法(仅对于表单提交有效，对于以json格式提交将会失效)
     * 如果是表单类型的提交，则spring会采用表单数据的处理类进行处理（进行参数校验错误时会抛出BindException异常）
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, Object>> handlerBindException(BindException exception) {
        return handlerNotValidException(exception);
    }

    /**
     * 对方法参数校验异常处理方法(前端提交的方式为json格式出现异常时会被该异常类处理)
     * json格式提交时，spring会采用json数据的数据转换器进行处理（进行参数校验时错误是抛出MethodArgumentNotValidException异常）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handlerArgumentNotValidException(MethodArgumentNotValidException exception) {
        return handlerNotValidException(exception);
    }

    public ResponseEntity<Map<String, Object>> handlerNotValidException(Exception e) {
        log.debug("begin resolve argument exception");//debug级别的日志，在info级别下不会被输出
        BindingResult result;
        if (e instanceof BindException) {
            BindException exception = (BindException) e;
            result = exception.getBindingResult();
        } else {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            result = exception.getBindingResult();
        }

        Map<String, Object> maps;
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            maps = new HashMap<>(fieldErrors.size());
            fieldErrors.forEach(error -> {
                maps.put(error.getField(), error.getDefaultMessage());
            });
        } else {
            maps = Collections.EMPTY_MAP;
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(maps);
    }
}

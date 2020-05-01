package com.wgx.study.project.common.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 公共异常类
 */
@Data
@AllArgsConstructor
public class CommonException extends RuntimeException{

    /**
     * 错误代码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

}

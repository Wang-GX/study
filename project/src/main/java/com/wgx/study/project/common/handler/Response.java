package com.wgx.study.project.common.handler;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 公共返回类
 */
@Data
@Accessors(chain = true)
public class Response<T> {

    @ApiModelProperty("响应码")
    private String code;

    @ApiModelProperty("响应消息")
    private String message;

    @ApiModelProperty("响应数据")
    private T data;
}
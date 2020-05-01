package com.wgx.study.project.Jwt;

import lombok.Data;

import java.util.Date;

/**
 * JWT的载荷对象
 */
@Data
public class Payload<T> {

    /**
     * JWT的唯一标识
     */
    private String id;

    /**
     * JWT的载荷数据对象
     */
    private T info;

    /**
     * JWT的过期时间
     */
    private Date expiration;
}
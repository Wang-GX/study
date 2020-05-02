package com.wgx.study.project.Jwt;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * JWT的载荷数据对象
 */
@Data
@Accessors(chain = true)
public class UserInfo {

    private Long id;//用户id

    private String username;//用户名
    
    private String role;//用户角色
}
package com.wgx.study.project.common.pojo;

import lombok.Data;

import java.util.List;

@Data
public class UserResultMap2 {

    private Integer id;

    //测试一对多
    private List<User> userList;
}

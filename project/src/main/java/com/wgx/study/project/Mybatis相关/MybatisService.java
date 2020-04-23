package com.wgx.study.project.Mybatis相关;

import com.wgx.study.project.common.pojo.User;
import com.wgx.study.project.common.pojo.UserResultMap1;
import com.wgx.study.project.common.pojo.UserResultMap2;

import java.util.List;

public interface MybatisService {

    User getUser();

    void insertUser(User user);

    List<User> testIf(User user);

    List<User> testChoose(User user);

    List<User> testWhere(User user);

    Integer testSet(User user);

    List<User> testForEach(List<Integer> userAgeList);

    UserResultMap1 testResultMap1();

    List<UserResultMap2> testResultMap2();
}

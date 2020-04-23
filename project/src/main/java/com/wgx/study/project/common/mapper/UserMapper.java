package com.wgx.study.project.common.mapper;

import com.wgx.study.project.MybatisPlus.MyBaseMapper;
import com.wgx.study.project.common.pojo.User;
import com.wgx.study.project.common.pojo.UserResultMap1;
import com.wgx.study.project.common.pojo.UserResultMap2;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//@DS("slave_2")
//@DS("slave_1")
public interface UserMapper extends MyBaseMapper<User> {

    User getUser();

    void insertUser(@Param("user") User user);

    List<User> testIf(@Param("user") User user);

    List<User> testChoose(@Param("user") User user);

    List<User> testWhere(@Param("user") User user);

    Integer testSet(@Param("user") User user);

    List<User> testForEach(@Param("userAgeList") List<Integer> userAgeList);

    UserResultMap1 testResultMap1();

    List<UserResultMap2> testResultMap2();

}

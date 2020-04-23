package com.wgx.study.project.定时任务_乐观锁.乐观锁实现;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wgx.study.project.common.mapper.UserMapper;
import com.wgx.study.project.common.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 使用乐观锁的方式实现分布式锁
 */
@Component
public class OptimisticLockTest {

    @Autowired
    private UserMapper userMapper;

    //@Scheduled(cron = "*/1 * * * * ?")
    public void execute1() {

        //查询数据版本号并记录
        Integer oldVersion = this.userMapper.selectById(1).getVersion();//1
        System.out.println("定时任务1 oldVersion：" + oldVersion);

        //执行代码逻辑
        User user = new User();
        user.setUserSex("2");
        user.setVersion(oldVersion + 1);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 1).eq("version", oldVersion);

        //更新前查询数据最新版本号
        //依据version = oldVersion进行更新，如果version已经被修改则此处更新必然失败
        if (this.userMapper.update(user, wrapper) == 1) {
            System.out.println("定时任务1执行成功，newVersion：" + (oldVersion + 1));
        } else {
            System.out.println("定时任务1执行失败");
        }

    }

    //@Scheduled(cron = "*/1 * * * * ?")
    public void execute2() {

        //查询数据版本号并记录
        Integer oldVersion = this.userMapper.selectById(1).getVersion();//1
        System.out.println("定时任务2 oldVersion：" + oldVersion);

        //执行代码逻辑
        User user = new User();
        user.setUserSex("2");
        user.setVersion(oldVersion + 1);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 1).eq("version", oldVersion);

        //更新前查询数据最新版本号
        //依据version = oldVersion进行更新，如果version已经被修改则此处更新必然失败
        if (this.userMapper.update(user, wrapper) == 1) {
            System.out.println("定时任务2执行成功，newVersion：" + (oldVersion + 1));
        } else {
            System.out.println("定时任务2执行失败");
        }

    }

}

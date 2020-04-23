package com.wgx.study.project.MybatisPlusTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wgx.study.project.ProjectApplication;
import com.wgx.study.project.common.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = ProjectApplication.class)
class ActiveRecordTest {

    //TODO 注意：虽然使用ActiveRecord屏蔽了Mapper接口，但实际上还是通过Mapper接口(映射器：Mapper接口+xml)完成的对数据库的操作。
    //TODO 这里只演示了部分方法，可以对比BaseMapper的测试类继续完善其他方法，使用方式基本一致。

    /**
     * 根据id查询
     */
    @Test
    void testSelectById() {
        User user = new User();
        user.setId(1);
        System.out.println("查询结果为：" + user.selectById(user));
    }

    /**
     * 根据id查询
     */
    @Test
    void testSelectByCondition() {
        User user = new User();

        //构建查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "zhangsan");
        List<User> users = user.selectList(wrapper);
        System.out.println("查询结果为：" + users);
    }


    /**
     * 新增数据
     */
    @Test
    void testInsert() {
        User user = new User();
        user.setUserName("zhangsan");
        user.setUserSex("1");
        user.setUserAge("1");
        boolean insert = user.insert();
        System.out.println(insert);
    }


    /**
     * 根据id更新数据
     */
    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(1);
        user.setUserAge("2");
        boolean updateById = user.updateById();
        System.out.println(updateById);
    }

    /**
     * 根据id删除数据
     */
    @Test
    void testDelete() {
        User user = new User();
        user.setId(1);
        boolean deleteById = user.deleteById();
        System.out.println(deleteById);
    }

}

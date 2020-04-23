package com.wgx.study.project.MybatisPlusTest;

import com.wgx.study.project.common.mapper.UserMapper;
import com.wgx.study.project.common.mapper.UserMapper2;
import com.wgx.study.project.common.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisPluginTest {

    /**
     * 测试全表更新时阻断插件的效果
     */
    @Test
    void testAllTableUpdate(){
        User user = new User();
        user.setUserAge("20");
        //条件为null时，表示对全表进行更新
        user.update(null);
    }

    /**
     * 测试全表删除时阻断插件的效果
     */
    @Test
    void testAllTableDelete(){
        User user = new User();
        user.setUserAge("20");
        //条件为null时，表示对全表进行更新
        user.delete(null);
    }

    /**
     * 测试乐观锁插件(使用AR)
     * 注意：乐观锁插件只支持 updateById(id) 与 update(entity, wrapper) 方法
     */
    @Test
    void testOptimisticLock1(){

        User user = new User();
        user.setId(1);//设置查询条件
        Integer oldVersion = user.selectById().getVersion();//查询数据版本号并记录
        System.out.println("oldVersion = " + oldVersion);

        System.out.println("执行逻辑代码");

        //更新前查询数据最新版本号
        user.setVersion(oldVersion);//设置乐观锁版本号为之前查询时记录下的版本号
        user.setUserSex("2");//设置修改内容
        user.updateById();//执行的SQL语句会将oldVersion作为更新条件，(oldVersion + 1)作为更新的值。如果其他线程更新过数据，版本号的值也会被修改(严格递增)，那么更新条件不满足，更新失败。
        /**
         * 执行的SQL语句为(【】中的内容是在我们使用了乐观锁插件之后，执行updateById和update方法时MybatisPlus自动为我们添加的)：
         *  UPDATE user SET user_sex = '2' 【, version= (oldVersion + 1）】
         *  WHERE id = 1 【AND version = oldVersion】;
         */
        System.out.println("newVersion = " + user.getVersion());//最新的版本号会回显到实体类对象中

    }

    /**
     * 测试乐观锁插件(使用BaseMapper)
     */
    @Test
    void testOptimisticLock2(){

        //查询并记录当前版本号
        User user = new User();
        user.setId(1);
        Integer oldVersion = this.userMapper.selectById(user).getVersion();
        System.out.println("oldVersion = " + oldVersion);

        //设置更新内容
        user.setUserSex("2");
        //设置乐观锁版本号
        user.setVersion(oldVersion);

        //执行更新操作
        int count = this.userMapper.updateById(user);
        System.out.println("数据库受影响的行数为：" + count);

        //获取回显的版本号
        System.out.println("newVersion = " + user.getVersion());

    }


    @Autowired
    private UserMapper userMapper;

    /**
     * 测试SQL注入器
     */
    @Test
    void testSqlInjector(){
        List<User> users = userMapper.findAll();
        System.out.println(users);
    }

    /**
     * 测试自动填充
     */
    @Test
    void testAutoFill(){
        User user = new User();
        int count = this.userMapper.insert(user);
        System.out.println("数据库受影响的行数为：" + count);
    }

    /**
     * 测试逻辑删除
     */
    @Test
    void testLogicDelete(){
        int count = this.userMapper.deleteById(1);
        System.out.println("数据库受影响的行数为：" + count);
    }

    @Autowired
    private UserMapper2 userMapper2;

    /**
     * 测试多数据源
     */
    @Test
    void testMultipleDataSources(){
        //查询数据库1连接的数据库中的数据
        User user = userMapper.selectById(1);
        System.out.println("user = " + user);

        //查询数据源2连接的数据库中的数据
        User user1 = userMapper2.selectById(1);
        System.out.println("user1 = " + user1);
    }
}

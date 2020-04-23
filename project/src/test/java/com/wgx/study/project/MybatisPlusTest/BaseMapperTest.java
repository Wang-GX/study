package com.wgx.study.project.MybatisPlusTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wgx.study.project.ProjectApplication;
import com.wgx.study.project.common.mapper.UserMapper;
import com.wgx.study.project.common.pojo.User;
import com.wgx.study.project.common.pojo.UserDTO;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SpringBoot整合单元测试：
 * (1) import org.junit.Test;
 *     @RunWith(SpringRunner.class)+@SpringBootTest(引导类名.class);
 * (2) import org.junit.jupiter.api.Test;
 *     @SpringBootTest(引导类名.class);
 */


@SpringBootTest(classes = ProjectApplication.class)
class BaseMapperTest {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void showSqlSessionFactory() {

        //TODO(**)这里的注释待完善，并且要将MybatisPlus的SQL语句注入到Mybatis容器中的过程梳理清楚
        //定义的SQL语句，最终会被解析成statement对象注入到Mybatis容器中。
        //在下面这行代码处打断点，查看mappedStatements属性值。
        System.out.println(sqlSessionFactory);
    }

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据id查询(查询结果可以为null)
     */
    @Test
    void testSelectById() {
        User user = userMapper.selectById(1);
        System.out.println("查询到的数据：" + user);
    }

    /**
     * 根据Ids批量查询
     */
    @Test
    void testSelectByIds() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        List<User> users = userMapper.selectBatchIds(ids);
        System.out.println("查询到的数据：" + users);
    }

    /**
     * 根据条件查询一条数据(如果有多条数据符合查询条件则会报错，查询结果可以为null)
     */
    @Test
    void testSelectOne() {
        //构建查询条件，多个条件之间是and关系
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "zhangsan").eq("user_sex", 1);
        User user = userMapper.selectOne(wrapper);
        System.out.println("查询到的数据：" + user);
    }


    /**
     * 根据条件查询多条数据
     */
    @Test
    void testSelectList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "zhangsan");
        List<User> users = userMapper.selectList(wrapper);
        System.out.println("查询到的数据：" + users);
    }

    /**
     * 根据条件查询数据
     * eq：等于
     * ne：不等于
     * gt：大于
     * ge：大于等于
     * lt：小于
     * le：小于等于
     * between：BETWEEN 值1 AND 值2
     * notBetween：NOT BETWEEN 值1 AND 值2
     * in：字段 IN (值1 , 值2...)
     * notIn：字段 NOT IN (值1 , 值2...)
     */
    @Test
    void testSelectRange() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "zhangsan").lt("user_age",18);
        List<User> users = userMapper.selectList(wrapper);
        System.out.println("查询到的数据：" + users);
    }

    /**
     * 根据or条件查询数据
     */
    @Test
    void testSelectOr() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "zhangsan").or().eq("user_sex",1);
        List<User> users = userMapper.selectList(wrapper);
        System.out.println("查询到的数据：" + users);
    }

    /**
     * 指定查询字段(否则查询全部字段)
     */
    @Test
    void testSelectField() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "zhangsan").select("user_name","user_age");
        List<User> users = userMapper.selectList(wrapper);
        System.out.println("查询到的数据：" + users);
    }


    /**
     * 根据条件查询数据的数量
     */
    @Test
    void testSelectCount() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "zhangsan");
        Integer count = userMapper.selectCount(wrapper);
        System.out.println("查询到的数据：" + count);
    }


    /**
     * 模糊查询
     * like：LIKE '%值%'
     * notLike：NOT LIKE '%值%'
     * likeLeft：LIKE '%值'
     * likeRight：LIKE '值%'
     */
    @Test
    void testSelectFuzzy() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("user_name","王");
        List<User> users = userMapper.selectList(wrapper);
        System.out.println("查询到的数据：" + users);
    }


    /**
     * 分页查询(必须配置分页插件，见MybatisPlusConfiguration类)
     */
    @Test
    void testSelectPage() {
        Page<User> page = new Page<>(1, 10);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "zhangsan");
        IPage<User> iPage = this.userMapper.selectPage(page, wrapper);
        System.out.println("数据总条数：" + iPage.getTotal());
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("当前页数：" + iPage.getCurrent());
        List<User> records = iPage.getRecords();
        System.out.println("查询到的数据：" + records);
    }


    /**
     * 对查询结果进行排序
     * orderByAsc：升序排序：ORDER BY 字段, ... ASC
     * 例: orderByAsc("id", "name") ---> order by id ASC,name ASC
     * orderByDesc：降序排序：ORDER BY 字段, ... DESC
     * 例: orderByDesc("id", "name") ---> order by id DESC,name DESC
     */
    @Test
    void testSelectOrderBy() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name","zhangsan").orderByAsc("user_age");
        List<User> users = userMapper.selectList(wrapper);
        System.out.println("查询到的数据：" + users);
    }



    /**
     * 新增
     */
    @Test
    void testInsert() {
        //User user = new User();
        UserDTO user = new UserDTO();
        user.setUserName("mybatisPlus");
        user.setUserSex("1");
        user.setUserAge("10");
        user.setFatherHeight("9");
        user.setTest("test");
        int count = this.userMapper.insert(user);
        System.out.println("数据库受影响的行数为：" + count);
        //主键回显
        System.out.println(user.getId());
    }

    /**
     * 根据id更新
     * 只会更新设置值的字段
     * 如果是通过select查询出来的数据，修改了某个字段的值后作为该方法的参数，那么有值的字段都会被重新update
     */
    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(1);
        user.setUserAge("100");
        int count = this.userMapper.updateById(user);
        System.out.println("数据库受影响的行数为：" + count);
    }

    /**
     * 根据条件更新
     * 只会更新指定字段的值
     * 如果是通过select查询出来的数据，修改了某个字段的值后作为该方法的参数，那么有值的字段都会被重新update
     */
    @Test
    void testUpdateByCondition() {
        User user = new User();
        user.setUserAge("100");

        //构建条件：某个字段的值
//      QueryWrapper<User> wrapper = new QueryWrapper<>();
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_name", "mybatisPlus");//TODO 注意：参数都为字段名和字段值，而不是属性名
        int count = this.userMapper.update(user, wrapper);
        System.out.println("数据库受影响的行数为：" + count);

    }

    /**
     * 根据id删除
     */
    @Test
    void testDeleteById() {
        int count = this.userMapper.deleteById(1);
        System.out.println("数据库受影响的行数为：" + count);
    }

    /**
     * 根据Ids批量删除
     */
    @Test
    void testDeleteByIds() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        int count = this.userMapper.deleteBatchIds(ids);
        System.out.println("数据库受影响的行数为：" + count);
    }

    /**
     * 根据条件删除
     */
    @Test
    void testDeleteByCondition1() {
        //多个条件之间是and关系
        Map<String, Object> map = new HashMap<>();
        map.put("user_name", "zhangsan");
        map.put("user_sex", 1);
        int count = this.userMapper.deleteByMap(map);
        System.out.println("数据库受影响的行数为：" + count);
    }

    /**
     * 根据条件删除
     */
    @Test
    void testDeleteByCondition2() {
        User user = new User();
        user.setUserName("zhangsan");
        user.setUserSex("1");
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        int count = this.userMapper.delete(wrapper);
        System.out.println("数据库受影响的行数为：" + count);
    }


}

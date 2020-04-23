package com.wgx.study.project.Mybatis相关;

import com.wgx.study.project.common.pojo.User;
import com.wgx.study.project.common.pojo.UserResultMap1;
import com.wgx.study.project.common.pojo.UserResultMap2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "Mybatis相关", tags = "Mybatis相关")
@RestController
@RequestMapping("/mybatis")
public class MybatisController {

    @Autowired
    @Qualifier(value = "mybatisServiceImpl")
    //@Autowired根据类型注入，@Qualifier根据Bean的名字注入
    //如果一个接口有两个及以上实现类时，@Autowired需要结合@Qualifier注解一起使用(注意：后者不能单独使用)
    //使用@Component及其衍生注解标识的Spring的Bean，默认的名字为类名(首字母小写)
    private MybatisService mybatisService;

    /**
     * 测试类型别名
     *
     * @return
     */
    @ApiOperation(value = "测试类型别名")
    @PostMapping("getUser")
    public User getUser() {
        return this.mybatisService.getUser();
    }

    /**
     * 测试自增主键回显以及@Param注解标识对象
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "测试自增主键回显以及@Param注解标识对象")
    @PostMapping("insertUser")
    public void insertUser(@RequestBody User user) {
        this.mybatisService.insertUser(user);
    }

    //以下为动态SQL相关测试
    @ApiOperation(value = "测试动态SQL")
    @PostMapping("dynamicSQL")
    public void testDynamicSQL() {
        //<if>标签测试
        User user1 = new User();
        user1.setUserName("zhangsan");
        List<User> userList1 = this.mybatisService.testIf(user1);
        System.out.println("if" + userList1);

        //<choose>/<when>/<otherwise>标签测试
        User user2 = new User();
        user2.setUserSex("1");
        List<User> userList2 = this.mybatisService.testChoose(user2);
        System.out.println("choose:" + userList2);

        //<where>/<set>/<trim>标签测试
        User user3 = new User();
        user3.setUserName("zhangsan");
        user3.setUserSex("1");
        user3.setUserAge("18");
        List<User> userList3 = this.mybatisService.testWhere(user3);
        System.out.println(userList3);
        System.out.println("where:" + userList3);
        Integer count = this.mybatisService.testSet(user3);
        System.out.println("set：" + count);

        //<foreach>标签测试
        List<Integer> userAgeList = new ArrayList<>();
        userAgeList.add(18);
        userAgeList.add(19);
        userAgeList.add(20);
        List<User> userList4 = this.mybatisService.testForEach(userAgeList);
        System.out.println("foreach：" + userList4);

    }

    @ApiOperation(value = "测试resultMap(未完成!)")
    @PostMapping("resultMap")
    public void testResultMap() {
        UserResultMap1 userResultMap1 = this.mybatisService.testResultMap1();
        System.out.println("userResultMap1 = " + userResultMap1);
        List<UserResultMap2> userResultMap2s = this.mybatisService.testResultMap2();
        System.out.println(userResultMap2s.size());
        System.out.println("userResultMap2 = " + userResultMap2s);
    }

}

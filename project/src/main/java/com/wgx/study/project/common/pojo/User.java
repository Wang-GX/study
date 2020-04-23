package com.wgx.study.project.common.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
/**
 * 下面这两个注解都将是在使用MybatisPlus代码生成器时自动生成的，可以认为是MybatisPlus的推荐配置，不需要过于纠结。
 */
@EqualsAndHashCode(callSuper = false)//生成的hashCode和equals方法稍有差异，默认值为false
//@Accessors(chain = true)//调用set方法返回值为当前对象，方便链式调用设置属性值
@TableName("user")
public class User extends Model<User> implements Serializable {
    //可以点击IDEA左侧的Structure查看当前类的结构(属性，方法)

    @ApiModelProperty(value = "主键id", name = "主键id")
    @TableId(value = "id", type = IdType.AUTO)//指定id类型为数据库自增长
    private Integer id;
    @ApiModelProperty(value = "用户名", name = "用户名")
    @TableField(value = "user_name")
    private String userName;
    @ApiModelProperty(value = "用户性别", name = "用户性别")
    @TableField(value = "user_sex", fill = FieldFill.INSERT)//调用MybatisPlus的api执行insert语句时触发该属性值的自动填充
    private String userSex;
    @ApiModelProperty(value = "用户年龄", name = "用户年龄")
    @TableField("user_age")
    private String userAge;
    @TableField(exist = false)
    private String fatherName;
    @TableField(exist = false)
    private String fatherHeight;

    //TODO 对于@Version注解和@TableLogic注解标识的属性，不能简单地当成实体类中的普通属性处理。分为以下两种情况：
    //(1)没有配置对应的MybatisPlus插件(例如：实体类中添加了version属性，并且使用@Version注解标识该属性，但是没有配置乐观锁插件)
    //   此时该属性和实体类中的其他普通属性相同。可以作为查询条件进行查询或者作为更新值进行更新。
    //   乐观锁插件：
    //   user.setSex(2);
    //   user.setVersion(oldVersion);
    //   user.updateById(1);
    //   --> UPDATE user SET user_sex=2, version = oldVersion WHERE id=1;
    //   逻辑删除：
    //   3.1.1+不需要配置逻辑删除插件，自动生效
    //(2)配置了对应的MybatisPlus插件(例如：实体类中添加了version属性，并且使用@Version注解标识该属性，并且配置乐观锁插件)
    //   此时该属性不能再像普通属性一样作为查询条件或者更新条件，而是由MybatisPlus对应的插件进行管理。
    //   乐观锁插件：
    //   user.setSex(2);
    //   user.setVersion(oldVersion);
    //   user.updateById(1);
    //   --> UPDATE user SET user_sex=2, version = oldVersion+1 WHERE id=1 and version = oldVersion;
    //   逻辑删除：
    //   user.deletedById(1);
    //   update user set deleted = 1 where id = 1 and deleted = 0;


    //乐观锁版本号，如果需要使用乐观锁插件，确保数据库中有此字段
    @Version
    private Integer version;

    //逻辑删除标记(0：未删除/1：删除)，如果需要使用逻辑删除，确保数据库中有此字段
    @TableLogic
    private Integer deleted;

}

/**
 * 关于@TableField注解的理解误区：
 * 该注解确实有映射属性和字段的功能，但是并不是只有标识了该注解的属性才会和数据表中的字段完成映射。当使用@TableName注解标识类后，实体类和数据表，同名或满足驼峰匹配的属性和字段就已经完成了自动映射。
 * @TableField注解主要解决了两个问题
 *  (1)属性名和字段名不匹配(并且也不满足驼峰匹配)时无法完成自动映射，通过该注解可以将属性和字段进行手动映射。如：
 *      @TableField(value = "user_name")
 *      private String name;
 *  (2)在调用MybatisPlus的api时会自动生成SQL语句，并且只要是设置了值的属性就会被包含在生成的SQL语句中。导致的结果就是实体类中每一个使用的属性在数据表中必须存在着一个对应的字段，否则在执行SQL语句时就有可能报错。
 *     通过该注解可以忽略掉实体类中的某些属性，即使设置了值也不会被包含在生成的SQL语句中。如：
 *     @TableField(exist = false)
 *     private String fatherName;
 *
 */
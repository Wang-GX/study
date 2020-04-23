package com.wgx.study.project.common.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends User{

    //使用MybatisPlus操作数据库时最好使用entity对象，而不要使用dto对象。可能不会出现问题，但是并不合理。


    /**
     * 前端-->dto对象-->                                    -->entity对象-->数据库
     *                    service层：从dto对象获取所需参数
     * 前端<--dto对象<--                                    <--entity对象--<数据库
     */


    //测试属性
    private String test;

}

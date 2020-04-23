package com.wgx.study.project.MybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

//TODO 标识自动填充的属性，在执行对应的SQL语句时，一定会作为对应的条件，如果在自动填充器中没有赋值，那么会设置对应的值为null
//TODO 例如，create_time标识UPDATE，那么再执行updateById时，拼接的SQL语句中一定会出现：set create_time = ?，如果在updateFill方法中没有设置值，那么该值为null
//TODO 没有设置自动填充时，如果该值为null则不会出现在拼接的SQL语句中
//TODO 如果在执行相应操作(insert或update)时没有填充(为null)这个属性，就不要让这个属性的自动填充范围包含在这个操作范围之内
//TODO 解决方式：手写SQL或者修改属性上的注解或者修改自动填充器逻辑
//TODO 建议：create_time和create_user的填充策略为insert，update_time和update_user的填充时机为update。保证在触发时一定会设置值进去，而对于不设置值(为null)的属性不应该包含在此范围中。
public class MyMetaObjectHandler implements MetaObjectHandler {

    //插入数据时自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        //先获取插入数据时传入的值
        Object userSex = getFieldValByName("userSex", metaObject);
        //如果为null，就填充默认值
        if (userSex == null) {
            setFieldValByName("userSex", "1", metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}

package com.wgx.study.project.MybatisPlus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;

/**
 * 自定义SQL注入器
 */
public class MySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList() {
        List<AbstractMethod> methodList = super.getMethodList();//TODO：特别注意这行代码
        methodList.add(new FindAll());
        return methodList;
    }

}
package com.wgx.study.project.Spring相关;

import org.springframework.stereotype.Component;

public class SpringCycleDependenceTest {
    /**
     * Spring循环依赖问题：
     * https://www.cnblogs.com/tiger-fu/p/8961361.html
     * 引言：循环依赖就是N个类中循环嵌套引用，如果在日常开发中我们用new对象的方式发生这种循环依赖的话程序会在运行时一直循环调用，直至内存溢出报错。下面说一下Spring是如果解决循环依赖的。
     *
     * 第一种：构造器参数循环依赖
     * 表示通过构造器注入构成的循环依赖，此依赖是无法解决的，只能抛出BeanCurrentlyIn CreationException异常表示循环依赖。
     * 如在创建A类时，构造器需要B类，那将去创建B，在创建B类时又发现需要C类，则又去创建C，最终在创建C时发现又需要A，从而形成一个环，没办法创建。
     * Spring容器会将每一个正在创建的Bean标识符放在一个“当前创建Bean池”中，Bean标识符在创建过程中将一直保持。
     * 在这个池中，因此如果在创建Bean过程中发现自己已经在“当前创建Bean池”里时将抛出。
     * BeanCurrentlyInCreationException异常表示循环依赖；而对于创建完毕的Bean将从“当前创建Bean池”中清除掉。
     *
     * 第二种：setter方法参数循环依赖
     * Spring是先将Bean对象实例化之后再设置对象属性的。
     * Spring先是用构造实例化Bean对象，此时Spring会将这个实例化结束的对象放到一个Map中，并且Spring提供了获取这个未设置属性的实例化对象引用的方法。
     * 当Spring实例化了A、B、C后，紧接着会去设置对象的属性，此时A依赖B，就会去Map中取出存在里面的单例B对象，以此类推，不会出来循环的问题、
     */
}

@Component
class A{
    private B b;
//    public A(B b) {
//      this.b = b;
//    }
    public void setB(B b) {
        this.b = b;
    }
}

@Component
class B{
    private C c;
//    public B(C c) {
//        this.c = c;
//    }
    public void setC(C c) {
        this.c = c;
    }
}

@Component
class C{
    private A a;
//    public C(A a) {
//        this.a = a;
//    }
    public void setA(A a) {
        this.a = a;
    }
}
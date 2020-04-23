package com.wgx.study.project.Lambda表达式;

public class LambdaTest {
    public static void main(String[] args) {

        //使用匿名内部类来调用接口中的抽象方法，本质上是接口的实现类对象调用重写后的方法
        boolean result1 = new MyFunctionalInterface() {
            @Override
            public boolean compare(int a, int b) {
                return (a - b) > 0;
            }
        }.compare(1, 2);

        System.out.println(result1);

        //使用Lambda表达式
        boolean result2 = ((MyFunctionalInterface) ((a, b) -> (a - b) > 0)).compare(1, 2);

        System.out.println(result2);


    }
}

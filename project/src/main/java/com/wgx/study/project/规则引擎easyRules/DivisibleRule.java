package com.wgx.study.project.规则引擎easyRules;

import org.jeasy.rules.annotation.*;
import org.jeasy.rules.support.UnitRuleGroup;

public class DivisibleRule {


    @Rule(name = "被2和3同时整除", description = "number如果被2和3同时整除，打印：number is two and three")
    public static class DivisibleByTwoAndThree extends UnitRuleGroup {

        //通过构造方法注入指定规则，形成一组规则。同时满足时，同时执行，否则都不执行，进入下一个优先级的规则进行判断。
        public DivisibleByTwoAndThree(Object... rules) {
            for (Object rule : rules) {
                addRule(rule);
            }
        }

        @Override //优先级注解：return 数值越小，优先级越高
        public int getPriority() {
            return 0;
        }
    }


    @Rule(name = "被2整除", description = "number如果被3整除，打印：number is two")
    public static class DivsibleByTwo_Rules {

        @Condition //条件判断注解：如果return true， 执行Action
        public boolean isTwoJudge(@Fact("number") int number) {
            return number % 2 == 0;
        }

        @Action //执行方法注解
        public void isTwoAction(@Fact("number") int number) {
            System.out.print("输出结果：[" + number + "] is two !");
        }

        @Priority //优先级注解：return 数值越小，优先级越高
        public int getPriority() {
            return 1;
        }
    }


    @Rule(name = "被3整除", description = "number如果被3整除，打印：number is three")
    public static class DivisibleByThree_Rules {

        @Condition //条件判断注解：如果return true， 执行Action
        public boolean isThreeJudge(@Fact("number") int number) {
            return number % 3 == 0;
        }

        @Action //执行方法注解
        public void isThreeAction(@Fact("number") int number) {
            System.out.print("输出结果：[" + number + "] is three !");
        }

        @Priority //优先级注解：return 数值越小，优先级越高
        public int getPriority() {
            return 2;
        }
    }


    @Rule(name = "既不被2整除也不被3整除", description = "打印：number")
    public static class Other_Rules {
        @Condition
        public boolean isOther(@Fact("number") int number) {
            return number % 2 != 0 || number % 3 != 0;
        }

        @Action
        public void otherAction(@Fact("number") int number) {
            System.out.print("输出结果：[" + number + "] !");
        }

        @Priority
        public int getPriority() {
            return 3;
        }
    }

}

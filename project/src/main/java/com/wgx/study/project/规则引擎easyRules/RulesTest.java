package com.wgx.study.project.规则引擎easyRules;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RulesEngineParameters;

public class RulesTest {

    public static void main(String[] args) {

        //设置规则执行引擎参数
        RulesEngineParameters parameters = new RulesEngineParameters().skipOnFirstAppliedRule(true);//只要匹配到第一条规则就跳过后面规则匹配
        //创建规则执行引擎
        RulesEngine rulesEngine = new DefaultRulesEngine(parameters);

        //创建规则
        Rules rules = new Rules();

        //注册规则
        rules.register(new DivisibleRule.DivisibleByThree_Rules());
        rules.register(new DivisibleRule.DivsibleByTwo_Rules());
        rules.register(new DivisibleRule.DivisibleByTwoAndThree(new DivisibleRule.DivsibleByTwo_Rules(),new DivisibleRule.DivisibleByThree_Rules()));
        rules.register(new DivisibleRule.Other_Rules());

        //创建事实
        Facts facts = new Facts();
        for (int i=1 ; i<=50 ; i++){
            //规则因素，对应的name，要和规则里面的@Fact 一致
            facts.put("number", i);
            //执行规则
            rulesEngine.fire(rules, facts);
            System.out.println();
        }
    }
}

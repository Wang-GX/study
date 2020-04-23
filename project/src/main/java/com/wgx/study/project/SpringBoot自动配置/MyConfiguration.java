package com.wgx.study.project.SpringBoot自动配置;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MyProperties.class)//指定注入的属性类，可以通过该注解隐式注入，也可以通过@Autowired显式注入。后者需要声明属性类为Spring的bean。
public class MyConfiguration {

    /*@Bean
    public Person getPerson(){
        return new Person();
    }*/

    //Person person = new Person();

    @Bean(name = "weapon")
    @ConditionalOnClass({WeaponMust1.class, WeaponMust2.class})//TODO(*)该注解暂未测试
    //该注解指定的类必须存在才会执行这个方法
    public Weapon createWeapon(MyProperties properties) {
        //测试@ConditionalOnClass注解
        Weapon weapon = new Weapon();
        weapon.setWeaponName(properties.getWeaponName());
        weapon.setAttackPower(properties.getAttackPower());
        return weapon;
    }

    @Bean(name = "cloths")
    //@ConditionalOnProperty(prefix = "game.config", value = {"clothsName","defensivePower"},matchIfMissing = {"荆棘之甲",""})
    @ConditionalOnProperty(prefix = "game.config", name = {"sex"},matchIfMissing = true)
    //matchIfMissing = true：选择的属性值无论是否为null都会执行这个方法
    //matchIfMissing = false(默认)：选择的属性值如果为null(配置文件中没有配置并且属性类中没有给默认值)就不执行这个方法，TODO 报错
    public Cloths createCloths(MyProperties properties) {
        //测试@ConditionalOnProperty注解
        Cloths cloths = new Cloths();
        cloths.setClothsName(properties.getClothsName());
        cloths.setDefensivePower(properties.getDefensivePower());
        return cloths;
    }

    @Bean(name = "person")
    @ConditionalOnMissingBean(Person.class)
    public Person createPerson(MyProperties properties,Weapon weapon,Cloths cloths){
        //测试@ConditionalOnMissingBean注解，测试OK！
        //如果【IOC容器中】不存在指定类型的bean对象，这个方法才会执行，否则不执行
        Person person = new Person();
        person.setName(properties.getPersonName());
        person.setSex(properties.getSex());
        person.setAge(properties.getAge());
        person.setName(properties.getPersonName());
        person.setWeapon(weapon);
        person.setCloths(cloths);
        return person;
    }
}

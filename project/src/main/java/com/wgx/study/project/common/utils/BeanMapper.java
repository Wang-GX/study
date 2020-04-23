package com.wgx.study.project.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wgx.study.project.common.pojo.OrikaDiffDTO1;
import com.wgx.study.project.common.pojo.OrikaDiffDTO2;
import com.wgx.study.project.common.pojo.User;
import com.wgx.study.project.common.pojo.UserDTO;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * 参考(代码来源)：https://www.cnblogs.com/jcjssl/p/9395091.html
 * 参考(常见Bean映射工具分析评测及Orika介绍)：https://www.jianshu.com/p/40e0e64797b9
 * <p>
 * 简单封装orika, 实现深度的BeanOfClassA<->BeanOfClassB复制
 * <p>
 * 不要使用Apache Common BeanUtils进行类复制，每次就行反射查询对象的属性列表, 非常缓慢.
 * <p>
 * 注意: 需要参考本模块的POM文件，显式引用orika.
 */
@Slf4j
public class BeanMapper {

    private static MapperFacade mapper;

    static {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapper = mapperFactory.getMapperFacade();
    }

    /**
     * 简单的复制出新类型对象.
     * <p>
     * 通过source.getClass() 获得源Class
     */
    public static <S, D> D map(S source, Class<D> destinationClass) {
        return mapper.map(source, destinationClass);
    }

    /**
     * 极致性能的复制出新类型对象.
     * <p>
     * 预先通过BeanMapper.getType() 静态获取并缓存Type类型，在此处传入
     */
    public static <S, D> D map(S source, Type<S> sourceType, Type<D> destinationType) {
        return mapper.map(source, sourceType, destinationType);
    }

    /**
     * 简单的复制出新对象列表到ArrayList
     * <p>
     * 不建议使用mapper.mapAsList(Iterable<S>,Class<D>)接口, sourceClass需要反射，实在有点慢
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Class<S> sourceClass, Class<D> destinationClass) {
        return mapper.mapAsList(sourceList, TypeFactory.valueOf(sourceClass), TypeFactory.valueOf(destinationClass));
    }

    /**
     * 极致性能的复制出新类型对象到ArrayList.
     * <p>
     * 预先通过BeanMapper.getType() 静态获取并缓存Type类型，在此处传入
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Type<S> sourceType, Type<D> destinationType) {
        return mapper.mapAsList(sourceList, sourceType, destinationType);
    }

    /**
     * 简单复制出新对象列表到数组
     * <p>
     * 通过source.getComponentType() 获得源Class
     */
    public static <S, D> D[] mapArray(final D[] destination, final S[] source, final Class<D> destinationClass) {
        return mapper.mapAsArray(destination, source, destinationClass);
    }

    /**
     * 极致性能的复制出新类型对象到数组
     * <p>
     * 预先通过BeanMapper.getType() 静态获取并缓存Type类型，在此处传入
     */
    public static <S, D> D[] mapArray(D[] destination, S[] source, Type<S> sourceType, Type<D> destinationType) {
        return mapper.mapAsArray(destination, source, sourceType, destinationType);
    }

    /**
     * 预先获取orika转换所需要的Type，避免每次转换.
     */
    public static <E> Type<E> getType(final Class<E> rawType) {
        return TypeFactory.valueOf(rawType);
    }


    //只测试极致性能的两种转换
    public static void main(String[] args) {

        //转换规则：同名属性复制，其他属性全部废弃

        log.info("----------------------------------------------------------------对象互转----------------------------------------------------------------");

        //对象A<->对象B
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("张三");
        userDTO.setUserSex("1");
        userDTO.setTest("test");
        //TODO 子类的toString方法无法打印输出从父类继承的属性的值(但实际上是存在的，可以通过get方法获取)，可以通过转换为JSON查看。
        log.info("源UserDTO：" + JSONObject.toJSONString(userDTO, SerializerFeature.WriteMapNullValue));
        //dto->entity
        User User = BeanMapper.map(userDTO, BeanMapper.getType(UserDTO.class), BeanMapper.getType(User.class));
        log.info("目标User：" + JSONObject.toJSONString(User, SerializerFeature.WriteMapNullValue));
        //entity->dto
        log.info("回转目标UserDTO：" + JSONObject.toJSONString(BeanMapper.map(User, BeanMapper.getType(User.class), BeanMapper.getType(UserDTO.class)), SerializerFeature.WriteMapNullValue));


        log.info("----------------------------------------------------------------集合互转----------------------------------------------------------------");


        //集合A<->集合B
        List<UserDTO> userDTOList = new ArrayList<>();
        UserDTO userDTO1 = new UserDTO();
        userDTO1.setUserName("张三");
        userDTO1.setUserSex("1");
        userDTO1.setTest("test");
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setUserName("李四");
        userDTO2.setUserSex("2");
        userDTO2.setTest("test");
        userDTOList.add(userDTO1);
        userDTOList.add(userDTO2);
        log.info("源UserDTOList：" + JSONObject.toJSONString(userDTOList, SerializerFeature.WriteMapNullValue));
        //List<dto>->List<entity>
        List<User> userList = BeanMapper.mapList(userDTOList, BeanMapper.getType(UserDTO.class), BeanMapper.getType(User.class));
        log.info("目标UserList：" + JSONObject.toJSONString(userList, SerializerFeature.WriteMapNullValue));
        //List<entity>->List<dto>
        log.info("回转目标UserDTOList：" + JSONObject.toJSONString(BeanMapper.mapList(userList, BeanMapper.getType(User.class), BeanMapper.getType(UserDTO.class)), SerializerFeature.WriteMapNullValue));

        log.info("----------------------------------------------------------------性能测试----------------------------------------------------------------");

        Type<UserDTO> typeUserDTO = BeanMapper.getType(UserDTO.class);
        Type<User> typeUser = BeanMapper.getType(User.class);

        long begin1 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            BeanMapper.map(userDTO, typeUserDTO, typeUser);
        }
        long end1 = System.currentTimeMillis();
        log.info("对象转换时间：" + (end1 - begin1));

        List<UserDTO> userDTOListPerformanceTest = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            userDTOListPerformanceTest.add(userDTO);
        }
        long begin2 = System.currentTimeMillis();
        BeanMapper.mapList(userDTOListPerformanceTest, typeUserDTO, typeUser);
        long end2 = System.currentTimeMillis();
        log.info("集合转换时间：" + (end2 - begin2));

        log.info("----------------------------------------------------------------指定不同名字段的映射规则----------------------------------------------------------------");
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(OrikaDiffDTO1.class, OrikaDiffDTO2.class).field("name1", "name2").field("age1", "age2").byDefault().register();
        //TODO 注意，使用自定义字段映射器时，需要调用byDefault方法。否则只有field方法声明的字段才能映射成功，同名同类型的字段无法映射成功。
        OrikaDiffDTO2 orikaDiffDTO2 = mapperFactory.getMapperFacade().map(new OrikaDiffDTO1().setName1("张三").setAge1(18).setSex(2), OrikaDiffDTO2.class);
        log.info(JSONObject.toJSONString(orikaDiffDTO2, SerializerFeature.WriteMapNullValue));

    }
}

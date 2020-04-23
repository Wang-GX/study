package com.wgx.study.project.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BeanCopierUtils {


    /**
     * 对象转换
     * TODO 性能大幅优于orika
     * @param source
     * @param destinationClass
     * @param <S>
     * @param <D>
     * @return
     */
    public static <S, D> D map(S source, Class<D> destinationClass){
        BeanCopier copier = BeanCopier.create(source.getClass(),destinationClass,false);
        D destination = null;
        try {
            destination = destinationClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        copier.copy(source,destination,null);
        return destination;
    }


    /**
     * 集合转换
     * TODO 由于BeanCopier没有提供批量转换的功能，所以集合的转换其实是循环调用对象转换，性能略低于orika提供的原生的集合转换的api
     * @param sourceList
     * @param destinationClass
     * @param <S>
     * @param <D>
     * @return
     */
    public static <S, D> List<D> mapList(List<S> sourceList, Class<D> destinationClass){
        List<D> destinationList = new ArrayList<>();
        for (S source : sourceList) {
            destinationList.add(map(source, destinationClass));
        }
        return destinationList;
    }
}
package com.thatday.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thatday.common.exception.TDExceptionHandler;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class BeanUtil extends cn.hutool.core.bean.BeanUtil {

    /**
     * 对象集合转换，两个对象的属性名字需要一样
     */
    public static <T, E> List<T> transTo(List<E> fromList, Class<T> clazz) {
        return transTo(fromList, clazz, null);
    }

    /**
     * 对象集合转换，两个对象的属性名字需要一样，并可自定义设置一些参数
     */
    public static <T, E> List<T> transTo(List<E> sourceList, Class<T> clazz, BiConsumer<T, E> biConsumer) {
        try {
            List<T> toList = new ArrayList<>();
            for (E source : sourceList) {
                T target = clazz.newInstance();
                copyProperties(source, target);
                if (biConsumer != null) {
                    biConsumer.accept(target, source);
                }
                toList.add(target);
            }
            return toList;
        } catch (Exception e) {
            throw TDExceptionHandler.throwGlobalException("数据转换异常,请联系程序猿!", e);
        }
    }

    /**
     * 对象转换，E转为t对象
     */
    public static <T, E> T transTo(E source, Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            copyProperties(source, t);
            return t;
        } catch (Exception ex) {
            throw TDExceptionHandler.throwGlobalException("数据转换异常,请联系程序猿!", ex);
        }
    }

    /**
     * 接口常量转为指定类型的List
     */
    public static <T> List<T> interfaceTransToVal(Class<?> clazz, Class<T> toClazz) throws IllegalAccessException {
        List<T> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            T t = (T) field.get(clazz);
            if (t.getClass() == toClazz) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * Map 转为对象，字段格式要一致
     */
    public static <T> T mapTransToObject(Map<String, Object> map, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(map);
        return objectMapper.readValue(jsonStr, clazz);
    }

    /**
     * 集合根据某个关键字进行分组
     */
    public static <T> Map<String, List<T>> groupBy(List<T> tList, Function<T, String> stringKey) {
        Map<String, List<T>> map = new HashMap<>();
        for (T t : tList) {
            if (map.containsKey(stringKey.apply(t))) {
                map.get(stringKey.apply(t)).add(t);

            } else {
                List<T> list = new ArrayList<>();
                list.add(t);
                map.put(stringKey.apply(t), list);
            }
        }
        return map;
    }
}

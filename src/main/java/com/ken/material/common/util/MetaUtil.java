package com.ken.material.common.util;

import com.ken.material.common.annotation.Meta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
public class MetaUtil {

    public static List<Kv> getKvs(Class<? extends Enum> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        return Stream.of(declaredFields).map(declaredField -> {
            Kv kv = new Kv(declaredField.getName(), declaredField.getName());
            if (declaredField.isAnnotationPresent(Meta.class)) {
                Meta annotation = declaredField.getAnnotation(Meta.class);
                if (!"".equals(annotation.value())) {
                    kv.setValue(annotation.value());
                }
            }
            return kv;
        }).collect(Collectors.toList());
    }

    public static Kv getKv(Enum obj) {
        String enumName = ((Enum<?>) obj).name();  // 先获取枚举名
        Field field = null;
        try {
            field = obj.getClass().getDeclaredField(enumName);
        } catch (NoSuchFieldException e) {
            return null;
        }
        Kv kv = new Kv(enumName, enumName);
        if (field.isAnnotationPresent(Meta.class)) {
            Meta annotation = field.getAnnotation(Meta.class);
            if (!"".equals(annotation.value())) {
                kv.setValue(annotation.value());
            }
        }
        return kv;
    }

    public static class Kv {
        private String id;
        private String value;

        public Kv(String id, String value) {
            this.id = id;
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Kv{" +
                    "id='" + id + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}


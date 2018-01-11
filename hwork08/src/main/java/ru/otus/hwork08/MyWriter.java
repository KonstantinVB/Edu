package ru.otus.hwork08;

import java.lang.reflect.Field;
import java.lang.reflect.Array;
import org.json.simple.JSONObject;
import java.util.Collection;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class MyWriter {

    public MyWriter () {}

    public static String toJson(Object src) {
        if (src == null) {
            return null;
        }
        String resultStr = new String();
        Class clazz = src.getClass();
        if (clazz.equals(String.class)||clazz.equals(Character.class)){
            resultStr=putStringToJson(src.toString());
        } else
        if (clazz.equals(Byte.class)
         || clazz.equals(Integer.class)
         || clazz.equals(Long.class)
         || clazz.equals(Float.class)
         || clazz.equals(Double.class)
         || clazz.equals(Short.class)
         || clazz.equals(Boolean.class)) {
            resultStr = putValueToJson(src);
        } else
        if (clazz.isArray()){
            resultStr = putArrayToJson(src);
        } else
        if (src instanceof Collection){
            resultStr = putCollectionToJson(src);
        } else
        if (src instanceof Map){
            resultStr = putMapToJson((Map) src);
        } else
        if (clazz.getSuperclass().equals(Object.class)) {
            resultStr = putObjectToJson(src);
        }
        return resultStr;

    }

    private static String putObjectToJson(Object putObject) {
        JSONObject obj = new JSONObject();
        Field[] arrOffields = putObject.getClass().getDeclaredFields();
        for (Field field : arrOffields) {
            try {
                field.setAccessible(true);
                obj.put(field.getName(),field.get(putObject));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                field.setAccessible(false);
            }
        }
        return obj.toString();
    }

    private static String putArrayToJson(Object putArray) {
        List<Object> objList = new ArrayList<>();
        int length = Array.getLength(putArray);
        for (int i = 0; i < length; i++) {
            Object item = Array.get(putArray, i);
            objList.add(toJson(item));
        }
        return String.valueOf(objList).replace(", ",",");
    }

    private static String putValueToJson(Object putValue) {
        return String.valueOf(putValue);
    }

    private static String putStringToJson(String putString) {
        return String.format("\"%s\"", putString);
    }

    private static String putMapToJson(Object putMap) {
        Map<String, String> stringMap = new LinkedHashMap<>();
        Map map = (Map) putMap;
        for (Object item : map.keySet()) {
            if (item == null || map.get(item) == null) {
                continue;
            }
            String key = String.valueOf(item);
            Object value = map.get(item);
            stringMap.put(key, toJson(value));
        }
        return String.format("{%s}",
                stringMap.entrySet()
                        .stream()
                        .map(item -> String.format("%s:%s", putStringToJson(item.getKey()), item.getValue()))
                        .collect(Collectors.joining(","))
        );
    }

    private static String putCollectionToJson(Object putCollection) {
        List<Object> stringList = new ArrayList<Object>();
        for (Object item : ((Collection) putCollection)) {
            stringList.add(item);
        }
        return putArrayToJson(stringList.toArray());
    }
}

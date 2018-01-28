package ru.otus.hwork10;

import ru.otus.hwork10.DS.DataSet;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReflectionHelper {
    public ReflectionHelper () {
    }

    public <T extends DataSet> String getTableName(Class<T> dataSetClass) {
        return dataSetClass.getAnnotation(Table.class).name();
    }

    public LinkedHashMap<String, Field> getColumnFields(Class<?> clazz) {
        LinkedHashMap<String, Field> fields = new LinkedHashMap<>();
        Class<?> c = clazz;
        while (!c.equals(Object.class)) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    fields.put(field.getAnnotation(Column.class).name(), field);
                }
            }
            c = c.getSuperclass();
        }
        return fields;
    }

    public String getFDescription(LinkedHashMap <String,Field> columns, Class clazz) {
        String result="";
        for (Map.Entry<String, Field> item : columns.entrySet()) {
            Field field = item.getValue();
            field.setAccessible(true);
            if (result!="") {
                result=result+",";
            }
            result = result+String.format(" `%s`",field.getAnnotation(Column.class).name());
            switch (field.getType().toString()) {
                case "int": result=result+" INT"; break;
                case "long": result=result+" BIGINT"; break;
                default: result=result+" VARCHAR"; break;
            }
            result=result+String.format(" (%s)",field.getAnnotation(Column.class).length());
            switch (String.valueOf(field.getAnnotation(Column.class).nullable())) {
                case "false": result=result+" NOT NULL"; break;
                default: result=result+" NULL"; break;
            }
            if (field.isAnnotationPresent(GeneratedValue.class)) {
                switch (field.getAnnotation(GeneratedValue.class).strategy().compareTo(GenerationType.IDENTITY)) {
                    case 0:
                        result = result + " AUTO_INCREMENT";
                        result = result + ", PRIMARY KEY (" + field.getAnnotation(Column.class).name() + ")";
                        break;
                }
            }
        }
        return result;
    }

}

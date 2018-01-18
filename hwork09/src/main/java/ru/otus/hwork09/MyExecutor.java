package ru.otus.hwork09;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("Duplicates")
public class MyExecutor {
    private final Connection connection;

    public MyExecutor() {
        connection = new MySQLConnection().getConnection();
    }

    public <T extends DataSet> void create(Class<T> entityClass) {
        LinkedHashMap<String, Field> columnFields = getColumnFields(entityClass);
        String tableName = entityClass.getAnnotation(Table.class).name();
        String query = "create table if not exists `" + tableName
                + String.format("` (%s)", getFDescriprion(columnFields,entityClass));
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends DataSet> void drop(Class<T> entityClass) {
        String tableName = entityClass.getAnnotation(Table.class).name();
        String query = "drop table " + tableName;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends DataSet> void insert(T entity) {
        LinkedHashMap<String, Field> columnFields = getColumnFields(entity.getClass());
        columnFields.remove("id");
        String statementParams = String.format("(%s)",
                Stream.generate(() -> "?")
                        .limit(columnFields.size())
                        .collect(Collectors.joining(", "))
        );
        String tableName = entity.getClass().getAnnotation(Table.class).name();
        String query = "insert into " + tableName + " "
                + String.format(" (%s) ", String.join(", ", columnFields.keySet()))
                + " values " + statementParams;
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            int i = 1;
            for (Map.Entry<String, Field> item : columnFields.entrySet()) {
                Field field = item.getValue();
                field.setAccessible(true);
                statement.setObject(i, field.get(entity));

                i++;
            }
            statement.executeUpdate();
            statement.close();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends DataSet> T select(long id, Class<T> entityClass) {
        LinkedHashMap<String, Field> columnFields = getColumnFields(entityClass);
        String query;
        PreparedStatement statement;
        String tableName = entityClass.getAnnotation(Table.class).name();
        try {
            query = "select * from " + tableName + " where id = ?";
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            T entity = entityClass.newInstance();
            for (Map.Entry<String, Field> item : columnFields.entrySet()) {
                Field field = item.getValue();
                field.setAccessible(true);
                field.set(entity, result.getObject(item.getKey()));
            }
            result.close();
            statement.close();
            return entity;
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends DataSet> List<Map<String, Object>> selectAll(T entity) {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        String query;
        PreparedStatement statement;
        String tableName = entity.getClass().getAnnotation(Table.class).name();
        try {
            query = "select * from " + tableName;
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            ResultSetMetaData md = result.getMetaData();
            int columns = md.getColumnCount();
            while (result.next()){
                Map<String, Object> row = new HashMap<String, Object>(columns);
                for(int i = 1; i <= columns; ++i){
                    row.put(md.getColumnName(i), result.getObject(i));
                }
                rows.add(row);
            }
            result.close();
            statement.close();
            return rows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private LinkedHashMap<String, Field> getColumnFields(Class<?> clazz) {
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

    private String getFDescriprion(LinkedHashMap <String,Field> columns, Class clazz) {
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
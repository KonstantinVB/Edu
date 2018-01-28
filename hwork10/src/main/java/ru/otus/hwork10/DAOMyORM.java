package ru.otus.hwork10;

import ru.otus.hwork10.DS.DataSet;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DAOMyORM {
    private Connection connection;
    private ReflectionHelper helper = new ReflectionHelper();

    public DAOMyORM (Connection connection) {
        this.connection = connection;
    }

    public <T extends DataSet> void save(T dataSet) {
        LinkedHashMap<String, Field> columnFields = helper.getColumnFields(dataSet.getClass());
        columnFields.remove("id");
        String statementParams = String.format("(%s)",
                Stream.generate(() -> "?")
                        .limit(columnFields.size())
                        .collect(Collectors.joining(", "))
        );
        String query = "insert into " + helper.getTableName(dataSet.getClass()) + " "
                + String.format(" (%s) ", String.join(", ", columnFields.keySet()))
                + " values " + statementParams;
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            int i = 1;
            for (Map.Entry<String, Field> item : columnFields.entrySet()) {
                Field field = item.getValue();
                field.setAccessible(true);
                statement.setObject(i, field.get(dataSet));

                i++;
            }
            statement.executeUpdate();
            statement.close();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public <T extends DataSet> void update(String nameOfFieldSeek, String valOfFieldSeek, String nameOfFieldSet, String valOfFieldSet, Class<T> dataSetClass) {
        LinkedHashMap<String, Field> columnFields = helper.getColumnFields(dataSetClass);
        columnFields.remove("id");
        String query = "update " + helper.getTableName(dataSetClass) + " set " + nameOfFieldSet
                + "='" + valOfFieldSet + "' where " + nameOfFieldSeek + "='" + valOfFieldSeek+"'";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public <T extends DataSet> void delete(String nameOfField, String valOfField, Class<T> dataSetClass) {
        String query = "delete from " + helper.getTableName(dataSetClass) + " where " + nameOfField + "=" + valOfField;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeQuery();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public <T extends DataSet> T read(long id, Class<T> dataSetClass) {
        LinkedHashMap<String, Field> columnFields = helper.getColumnFields(dataSetClass);
        String query;
        PreparedStatement statement;
        try {
            query = "select * from " + helper.getTableName(dataSetClass) + " where id = ?";
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            T entity = dataSetClass.newInstance();
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

    public <T extends DataSet> T readByName(String nameOfField, String valOfField, Class<T> dataSetClass) {
        LinkedHashMap<String, Field> columnFields = helper.getColumnFields(dataSetClass);
        String query;
        PreparedStatement statement;
        try {
            query = "select * from " + helper.getTableName(dataSetClass) + " where " + nameOfField +"= ?";
            statement = connection.prepareStatement(query);
            statement.setObject(1, valOfField);
            ResultSet result = statement.executeQuery();
            result.next();
            T entity = dataSetClass.newInstance();
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

    public <T extends DataSet> List<T> readAll(Class<T> dataSetClass) {
        LinkedHashMap<String, Field> columnFields = helper.getColumnFields(dataSetClass);
        String query;
        PreparedStatement statement;
        try {
            query = "select * from " + helper.getTableName(dataSetClass);
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            List<T> rows = new ArrayList<>();
            while (result.next()){
                T entity = null;
                try {
                    entity = dataSetClass.newInstance();
                    for (Map.Entry<String, Field> item : columnFields.entrySet()) {
                        Field field = item.getValue();
                        field.setAccessible(true);
                        field.set(entity, result.getObject(item.getKey()));
                    }
                    rows.add(entity);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            result.close();
            statement.close();
            return rows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public  <T extends DataSet> void createDS(Class<T> dataSetClass) {
        LinkedHashMap<String, Field> columnFields = helper.getColumnFields(dataSetClass);
        String query = "create table if not exists `" + helper.getTableName(dataSetClass)
                + String.format("` (%s)", helper.getFDescription(columnFields,dataSetClass));
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public    <T extends DataSet> void dropDS(Class<T> dataSetClass) {
        String query = "drop table " + helper.getTableName(dataSetClass);
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

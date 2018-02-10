package ru.otus.hwork12;

import ru.otus.hwork12.DS.DataSet;
import java.util.List;

public interface DBService {
    String getLocalStatus();

    void create();

    void drop();

    <T extends DataSet> void insert(T dataSet);

    <T extends DataSet> void update(String nameOfFieldSeek, String valOfFieldSeek, String nameOfFieldSet, String valOfFieldSet, Class<T> dataSetClass);

    <T extends DataSet> void delete(String nameOfField, String valOfField, Class<T> dataSetClass);

    <T extends DataSet> T select(long id, Class<T> dataSetClass);

    <T extends DataSet> T selectByName(String nameOfField, String valOfField, Class<T> dataSetClass);

    <T extends DataSet> List<T> selectAll(Class<T> dataSetClass);

    void shutdown();
}

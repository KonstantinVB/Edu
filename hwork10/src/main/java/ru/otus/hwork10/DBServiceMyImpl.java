package ru.otus.hwork10;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import ru.otus.hwork10.DS.*;

@SuppressWarnings("Duplicates")
public class DBServiceMyImpl implements DBService {
    private final Connection connection;

    public DBServiceMyImpl() {

        String url = "jdbc:mysql://" +       //db type
                "localhost:" +               //host name
                "3306/" +                    //port
                "otus_java_2017_10?" +              //db name
                "user=KonstantinVB&" +              //login
                "password=14081972&" +          //password
                "useSSL=false";             //do not use Secure Sockets Layer
        connection = this.getConnection(url);
        drop();
        create();

    }

    public DBServiceMyImpl(String configuration) {
        connection = getConnection(configuration);
    }

    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getLocalStatus() {
        String result = "";
        try {
            switch (String.valueOf(connection.isClosed())) {
                case "false": result="ACTIVE"; break;
                case "true": result="NOT ACTIVE"; break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public void drop() {
        DAOMyORM dao = new DAOMyORM(connection);
        dao.dropDS(UserDataSet.class);
    }

    public void create() {
        DAOMyORM dao = new DAOMyORM(connection);
        dao.createDS(UserDataSet.class);
    }

    public <T extends DataSet> void insert(T dataSet) {
        DAOMyORM dao = new DAOMyORM(connection);
        dao.save(dataSet);
    }

    public <T extends DataSet> void update(String nameOfFieldSeek, String valOfFieldSeek, String nameOfFieldSet, String valOfFieldSet, Class<T> dataSetClass) {
        DAOMyORM dao = new DAOMyORM(connection);
        dao.update(nameOfFieldSeek,valOfFieldSeek,nameOfFieldSet,valOfFieldSet,dataSetClass);
    }

    public <T extends DataSet> void delete(String nameOfField, String valOfField, Class<T> dataSetClass) {
        DAOMyORM dao = new DAOMyORM(connection);
        dao.delete(nameOfField,valOfField,dataSetClass);
    }

    public <T extends DataSet> T select(long id, Class<T> dataSetClass) {
        DAOMyORM dao = new DAOMyORM(connection);
        return dao.read(id,dataSetClass);
    }

    public <T extends DataSet> T selectByName(String nameOfField, String valOfField, Class<T> dataSetClass) {
        DAOMyORM dao = new DAOMyORM(connection);
        return dao.readByName(nameOfField,valOfField,dataSetClass);
    }

    public <T extends DataSet> List<T> selectAll(Class<T> dataSetClass) {
        List<T> rows = new ArrayList<>();
        DAOMyORM dao = new DAOMyORM(connection);
        rows=dao.readAll(dataSetClass);
        return rows;
    }

    private static Connection getConnection(String url) {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
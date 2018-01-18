package ru.otus.hwork09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://" +       //db type
                    "localhost:" +               //host name
                    "3306/" +                    //port
                    "otus_java_2017_10?" +              //db name
                    "user=KonstantinVB&" +              //login
                    "password=14081972&" +          //password
                    "useSSL=false";             //do not use Secure Sockets Layer
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.noirix.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.noirix.util.DatabasePropertiesReader.*;
import static com.noirix.util.DatabasePropertiesReader.DATABASE_PASSWORD;

public class DBUtils {

    private static DatabasePropertiesReader reader = DatabasePropertiesReader.getInstance();

    static {
        try {
            Class.forName(reader.getProperty(DATABASE_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection;

        String jdbcURL = reader.getProperty(DATABASE_URL);
        String login = reader.getProperty(DATABASE_LOGIN);
        String password = reader.getProperty(DATABASE_PASSWORD);

        connection = DriverManager.getConnection(jdbcURL, login, password);
        return connection;
    }
}

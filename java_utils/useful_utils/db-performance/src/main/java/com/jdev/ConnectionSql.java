package com.jdev;

import com.jdev.console.ConsoleUtils;
import com.jdev.file.FileResource;
import com.jdev.file.PropertiesFileUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionSql {

    private static Connection connection;

    public static Connection getConnection() {
        Properties properties = PropertiesFileUtils.readFromPropertiesFile(FileResource.findFileOnClassPath(
                "liquibase-db_performance.properties").getPath());
        try {
            if (connection == null || connection.isClosed()) {
                return connection = DriverManager.getConnection(properties.getProperty("url"),
                        properties.getProperty("username"), properties.getProperty("password"));
            } else {
                return connection;
            }
        } catch (SQLException e) {
            ConsoleUtils.logError("connection with db problem", e);
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            ConsoleUtils.logError("connection with db problem", e);
            throw new RuntimeException(e);
        }
    }

}

package com.jdev;

import com.jdev.console.ConsoleUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlHelper {

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            ConsoleUtils.logError("connection with db problem", e);
            throw new RuntimeException(e);
        }
    }

    public static void closeStatement(Statement statement) {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            ConsoleUtils.logError("Close statement!", e);
        }
    }

}

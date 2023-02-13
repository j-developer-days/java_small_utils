package com.jdev;

import com.jdev.console.ConsoleUtils;
import com.jdev.util.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class ConnectionSqlTest {

    @AfterEach
    private void printAllData() {
        ConsoleUtils.printDelimiter();
        ConsoleUtils.printToConsole("Begin print data ... ");
        ConnectionSql connectionSql = ConnectionSql.getInstance();
        try (Connection connection = connectionSql.getConnection();) {
            connection.setReadOnly(true);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM t_users_pk_int;");
            while (resultSet.next()) {
                ConsoleUtils.printToConsole("#" + resultSet.getInt("id") + "\t - " + resultSet.getString("firstname"));
            }
            resultSet.close();
            SqlHelper.closeStatement(statement);
            ConsoleUtils.printToConsole("Printed data from DB");
        } catch (SQLException e) {
            ConsoleUtils.logError("Reading ... postgresql connection exception!", e);
        }
    }

    @AfterEach
    private void forDelete() {
        ConsoleUtils.printDelimiter();
        ConsoleUtils.printToConsole("Begin delete data ... ");
        ConnectionSql connectionSql = ConnectionSql.getInstance();
        try (Connection connection = connectionSql.getConnection();) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ConsoleUtils.printToConsole("deleted - " + statement.executeUpdate("DELETE FROM t_users_pk_int;"));
            connection.commit();
            SqlHelper.closeStatement(statement);
            ConsoleUtils.printToConsole("*********Success Deleted*********");
        } catch (SQLException e) {
            ConsoleUtils.logError("postgresql connection exception!", e);
        }
    }

    @Test
    void test_ConnectionSql() throws SQLException {
        createConnectionToSql(1);
        createConnectionToSql(2);
        createConnectionToSql(3);
        createConnectionToSql(4);
        createConnectionToSql(5);
    }

    private void createConnectionToSql(int number) throws SQLException {
        ConnectionSql connectionSql = ConnectionSql.getInstance();
        ConsoleUtils.printToConsole("connectionSql" + number + ".getUuid() = " + connectionSql.getUuid());
        Connection connection = connectionSql.getConnection();
        ConsoleUtils.printToConsole("connectionSql" + number + " - connection.hashCode() - " + connection.hashCode() + StringUtils.TAB + "alive connection - "
                + !connection.isClosed());
        SqlHelper.closeConnection(connection);
    }
}
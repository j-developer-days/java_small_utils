package com.jdev;

import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * the same from - error_transactions.sql
 */
public class PostgresqlTransactionErrorTest {

    private static ConnectionSql connectionSql = ConnectionSql.getInstanceThreadSafe();

    /**
     * show how to work try with resources
     */
/*
    @BeforeEach
    @AfterEach
    private void forDelete() throws SQLException {
        Connection connectionOuter = null;
        try (Connection connection = ConnectionSql.getConnection()) {
            connectionOuter = connection;
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ConsoleUtils.printToConsole("deleted - " + statement.executeUpdate("DELETE FROM t_users_pk_int;"));

        } catch (SQLException e) {
            ConsoleUtils.logError("postgresql connection exception!", e);
            connectionOuter.rollback();
        }
    }
*/
    @BeforeEach
    @AfterEach
    private void forDelete() throws SQLException {
        Statement statement = null;
        Connection connection = null;
        try {
            connection = connectionSql.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            ConsoleUtils.printToConsole("deleted - " + statement.executeUpdate("DELETE FROM t_users_pk_int;"));
            connection.commit();
        } catch (SQLException e) {
            ConsoleUtils.logError("postgresql connection exception!", e);
            if (e.getMessage().contains("current transaction is aborted")) {
                connection.rollback();
            }
        } finally {
            SqlHelper.closeStatement(statement);
            SqlHelper.closeConnection(connection);
        }
    }

    /**
     * --transaction COMMIT 1
     */
    @Test
    void test_commit_withTheSameId() throws SQLException {
        Connection connection = connectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');");
        SQLException exception = Assertions.assertThrows(SQLException.class, () -> statement.executeUpdate(
                "INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'JAVA');"));
        if (exception == null) {
            connection.commit();
        } else {
            Assertions.assertTrue(exception.getMessage().contains("duplicate key value violates unique constraint"));
            Assertions.assertEquals("23505", exception.getSQLState());
            connection.rollback();
            PostgresqlTransactionAutoCommitTest.getCount(statement, 0);
        }
        statement.close();
        SqlHelper.closeConnection(connection);
    }

    /**
     * --transaction ROLLBACK 3
     */
    @Test
    void test_rollback_withTheSameId() throws SQLException {
        Connection connection = connectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');");
        SQLException exception = Assertions.assertThrows(SQLException.class, () -> statement.executeUpdate(
                "INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'JAVA');"));
        if (exception != null) {
            Assertions.assertTrue(exception.getMessage().contains("duplicate key value violates unique constraint"));
            Assertions.assertEquals("23505", exception.getSQLState());
            connection.rollback();
            PostgresqlTransactionAutoCommitTest.getCount(statement, 0);
        }
        statement.close();
        SqlHelper.closeConnection(connection);
    }

    /**
     * --source - transation_mode_readOnly.sql
     */
    @Test
    void test_readOnly() throws SQLException {
        Connection connection = connectionSql.getConnection();
        connection.setAutoCommit(false);
        connection.setReadOnly(true);
        Statement statement = connection.createStatement();
        SQLException exception = Assertions.assertThrows(SQLException.class, () -> statement.executeUpdate(
                "INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'JAVA');"));
        if (exception != null) {
            Assertions.assertTrue(exception.getMessage().equals("ERROR: cannot execute INSERT in a read-only transaction"));
            Assertions.assertEquals("25006", exception.getSQLState());
            connection.rollback();
            PostgresqlTransactionAutoCommitTest.getCount(statement, 0);
        }
        statement.close();
        SqlHelper.closeConnection(connection);
    }
}

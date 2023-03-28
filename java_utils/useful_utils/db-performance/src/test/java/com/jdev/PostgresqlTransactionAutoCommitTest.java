package com.jdev;

import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresqlTransactionAutoCommitTest {

    private static ConnectionSql connectionSql = ConnectionSql.getInstanceThreadSafe();

    static void getCount(Statement statement, int expected) throws SQLException {
        final ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM t_users_pk_int");
        int count = -1;
        while (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        resultSet.close();
        ConsoleUtils.printToConsole("select count is - " + count);
        if (expected != -1) {
            Assertions.assertEquals(expected, count);
        }
    }

    @AfterEach
    private void afterEach_forDelete() {
        try (Connection connection = connectionSql.getConnection();) {
            Statement statement = connection.createStatement();
            ConsoleUtils.printToConsole("deleted - " + statement.executeUpdate("DELETE FROM t_users_pk_int;"));
        } catch (SQLException e) {
            ConsoleUtils.logError("postgresql connection exception!", e);
        }
    }

    /**
     * the same as - auto_commit/postgresql_auto_commit.sql
     */
    @Test
    void test_autoCommit() throws SQLException {
        Connection connection = connectionSql.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-22100, 'MATLAB');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-20157, 'Go!');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-5860, 'Object Lisp');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-13814, 'LiveScript');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-31599, 'Erlang');");

        getCount(statement, 5);
        statement.close();
        SqlHelper.closeConnection(connection);
    }

    /**
     * the same as - auto_commit/postgresql_auto_commit_with_error_in_the_end.sql
     */
    @Test
    void test_autoCommitWith_error_in_the_end() throws SQLException {
        Connection connection = connectionSql.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-22100, 'MATLAB');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-20157, 'Go!');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-5860, 'Object Lisp');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-13814, 'LiveScript');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-31599, 'Erlang');");
        try {
            statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-31599, " +
                    "'ErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlang');--here is problem");
        } catch (SQLException e) {
            ConsoleUtils.logError("statement update error", e);
        } finally {
            getCount(statement, 5);
            statement.close();
            SqlHelper.closeConnection(connection);
        }
    }

    /**
     * the same as - auto_commit/postgresql_auto_commit_sql_with_error_in_the_middle.sql
     */
    @Test
    void test_autoCommit_sql_with_error_in_the_middle() throws SQLException {
        Connection connection = connectionSql.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-22100, 'MATLAB');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-20157, 'Go!');");
        SQLException exception = Assertions.assertThrows(SQLException.class, () -> statement.executeUpdate("INSERT INTO " +
                "t_users_pk_int (id, " +
                "firstname) VALUES (-31599, 'ErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlang');--here is problem"));
        if (exception != null) {
            getCount(statement, 2);
            statement.close();
            SqlHelper.closeConnection(connection);
            return;
        }
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-5860, 'Object Lisp');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-13814, 'LiveScript');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-31599, 'Erlang');");
    }

    /**
     * the same as - auto_commit/postgresql_auto_commit_sql_with_error_in_the_middle.sql
     */
    @Test
    void test_autoCommit_sql_with_error_in_the_middle2() throws SQLException {
        Connection connection = connectionSql.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-22100, 'MATLAB');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-20157, 'Go!');");
        try {
            statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-31599, 'ErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlangErlang');--here is problem");
        } catch (SQLException e) {
            ConsoleUtils.logError("error statement", e);
        }
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-5860, 'Object Lisp');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-13814, 'LiveScript');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-31599, 'Erlang');");

        getCount(statement, 5);
        statement.close();
        SqlHelper.closeConnection(connection);
    }

}

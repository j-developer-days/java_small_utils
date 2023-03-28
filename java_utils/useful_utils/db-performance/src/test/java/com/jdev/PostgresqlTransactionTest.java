package com.jdev;

import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * the same from - success_transactions.sql
 */
public class PostgresqlTransactionTest {

    private static ConnectionSql connectionSql = ConnectionSql.getInstanceThreadSafe();

    @BeforeEach
    @AfterEach
    private void forDelete() {
        try (Connection connection = connectionSql.getConnection();) {
            Statement statement = connection.createStatement();
            ConsoleUtils.printToConsole("deleted - " + statement.executeUpdate("DELETE FROM t_users_pk_int;"));
        } catch (SQLException e) {
            ConsoleUtils.logError("postgresql connection exception!", e);
        }
    }

    /**
     * --transaction COMMIT 1
     */
    @Test
    void test_commit() throws SQLException {
        Connection connection = connectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');");
        connection.commit();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 2);
        statement.close();
        SqlHelper.closeConnection(connection);
    }

    /**
     * --transaction COMMIT AND CHAIN && COMMIT - 2
     */
    @Test
    void test_commitAndChainCommit() throws SQLException {
        Connection connection = connectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');");
        statement.execute("COMMIT AND CHAIN;");

        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27167, 'PHP');");
        connection.commit();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 3);
        statement.close();
        SqlHelper.closeConnection(connection);
    }

    /**
     * --transaction COMMIT AND CHAIN && ROLLBACK - 3
     */
    @Test
    void test_commitAndChainRollback() throws SQLException {
        Connection connection = connectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');");
        statement.execute("COMMIT AND CHAIN;");

        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27167, 'PHP');");
        connection.rollback();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 2);
        statement.close();
        SqlHelper.closeConnection(connection);
    }

    /**
     * --transaction ROLLBACK AND CHAIN && COMMIT - 4
     */
    @Test
    void test_rollbackAndChainCommit() throws SQLException {
        Connection connection = connectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');");
        statement.execute("ROLLBACK AND CHAIN;");

        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27167, 'PHP');");
        connection.commit();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 1);
        statement.close();
        SqlHelper.closeConnection(connection);
    }

    /**
     * --transaction ROLLBACK AND CHAIN && ROLLBACK - 5
     */
    @Test
    void test_rollbackAndChainRollback() throws SQLException {
        Connection connection = connectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');");
        statement.execute("ROLLBACK AND CHAIN;");

        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27167, 'PHP');");
        connection.rollback();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 0);
        statement.close();
        SqlHelper.closeConnection(connection);
    }

    /**
     * --transaction COMMIT - the same as #2 - 6
     * --transaction COMMIT with warning - there is no transaction in progress - 7
     */
    @Test
    void test_commitAndCommit() throws SQLException {
        Connection connection = connectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');");
        connection.commit();

        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27167, 'PHP');");
        connection.commit();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 3);
        statement.close();
        SqlHelper.closeConnection(connection);
    }

    /**
     * --transaction ROLLBACK - 8
     */
    @Test
    void test_rollback() throws SQLException {
        Connection connection = connectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');");
        connection.rollback();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 0);
        statement.close();
        SqlHelper.closeConnection(connection);
    }

    /**
     * --transaction COMMIT && ROLLBACK - 9
     */
    @Test
    void test_commitAndRollback() throws SQLException {
        Connection connection = connectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM t_users_pk_int;");
        connection.commit();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');");
        connection.rollback();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 0);
        statement.close();
        SqlHelper.closeConnection(connection);
    }

    /**
     * --transaction ROLLBACK && COMMIT - 10
     */
    @Test
    void test_rollbackAndCommit() throws SQLException {
        Connection connection = connectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM t_users_pk_int;");
        connection.rollback();
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');");
        statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');");
        connection.commit();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 2);
        statement.close();
        SqlHelper.closeConnection(connection);
    }

}

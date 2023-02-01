package com.jdev;

import com.jdev.console.ConsoleUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

/**
 * the same from - transactions_with_savepoint.sql
 */
public class PostgresqlTransactionWithSavepointTest {

    @BeforeEach
    @AfterEach
    private void forDelete() throws SQLException {
        Statement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionSql.getConnection();
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
            ConnectionSql.closeConnection();
            ConnectionSql.closeStatement(statement);
        }
    }

    /**
     * --transaction will insert the values Postgresql, MySQL, not insert Oracle.(savepoint and rollback to savepoint) -----1
     */
    @Test
    void test_SavepointAndRollbackToSavepoint() throws SQLException {
        Connection connection = ConnectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');");
        Savepoint savepoint1 = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');");
        connection.rollback(savepoint1);

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');");
        connection.commit();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 2);
        statement.close();
        ConnectionSql.closeConnection();
    }

    /**
     * --transaction will insert the values Postgresql, Oracle, MySQL, not insert MariaDb.(2 savepoints with the same names and one rollback to save point)-----2
     */
    @Test
    void test_2SavepointsWithTheSameNamesAndRollbackToSavepoint() throws SQLException {
        Connection connection = ConnectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');");
        Savepoint savepoint1 = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');");
        Savepoint savepoint1Second = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');");
        connection.rollback(savepoint1Second);

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');");
        connection.commit();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 3);
        statement.close();
        ConnectionSql.closeConnection();
    }

    /**
     * --transaction will insert the values Postgresql, Oracle, MySQL, not insert MariaDb.(2 savepoints with the same names and one rollback to save point and one releasesavepoint)-----3
     */
    @Test
    void test_2SavepointsWithTheSameNamesAndRollbackToSavepointAndReleaseIt() throws SQLException {
        Connection connection = ConnectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');");
        Savepoint savepoint1 = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');");
        Savepoint savepoint1Second = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');");
        connection.rollback(savepoint1Second);

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');");
        connection.releaseSavepoint(savepoint1Second);
        connection.commit();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 3);
        statement.close();
        ConnectionSql.closeConnection();
    }

    /**
     * --transaction will insert the values Postgresql, MySQL, not insert Oracle, MariaDb.(1 savepoint and one rollback to save point and one releasesavepoint)-----4
     */
    @Test
    void test_SavepointAndRollbackToSavepointAndReleaseIt() throws SQLException {
        Connection connection = ConnectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');");
        Savepoint savepoint1 = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');");
        connection.rollback(savepoint1);

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');");
        connection.releaseSavepoint(savepoint1);
        connection.commit();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 2);
        statement.close();
        ConnectionSql.closeConnection();
    }

    /**
     * --transaction with ERROR! (1 savepoint and one rollback to save point and 2 releasesavepoints)-----5
     */
    @Test
    void test_SavepointAndRollbackToSavepointAnd2TimesReleaseIt() throws SQLException {
        Connection connection = ConnectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');");
        Savepoint savepoint1 = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');");
        connection.rollback(savepoint1);
        connection.releaseSavepoint(savepoint1);

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');");
        SQLException exception = Assertions.assertThrows(SQLException.class, () -> connection.releaseSavepoint(savepoint1));
        if (exception == null) {
            connection.commit();
            PostgresqlTransactionAutoCommitTest.getCount(statement, 2);
        } else {
            connection.rollback();
            PostgresqlTransactionAutoCommitTest.getCount(statement, 0);
        }

        statement.close();
        ConnectionSql.closeConnection();
    }

    /**
     * --transaction with ERROR! (1 savepoint and 2 releasesavepoints)-----6
     */
    @Test
    void test_SavepointAnd2TimesReleaseIt() throws SQLException {
        Connection connection = ConnectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');");
        Savepoint savepoint1 = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');");
        connection.releaseSavepoint(savepoint1);

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');");
        SQLException exception = Assertions.assertThrows(SQLException.class, () -> connection.releaseSavepoint(savepoint1));
        if (exception == null) {
            connection.commit();
            PostgresqlTransactionAutoCommitTest.getCount(statement, 2);
        } else {
            connection.rollback();
            PostgresqlTransactionAutoCommitTest.getCount(statement, 0);
        }

        statement.close();
        ConnectionSql.closeConnection();
    }

    /**
     * --transaction inserted all 4 entries! (1 savepoint and 1 releasesavepoints)-----7
     */
    @Test
    void test_SavepointAndReleaseItCommitTransaction() throws SQLException {
        Connection connection = ConnectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');");
        Savepoint savepoint1 = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');");
        connection.releaseSavepoint(savepoint1);

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');");
        connection.commit();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 4);
        statement.close();
        ConnectionSql.closeConnection();
    }

    /**
     * --transaction rollbacked all 4 entries, result is empty table! (1 savepoint and 1 releasesavepoints)-----8
     */
    @Test
    void test_SavepointAndReleaseItAndRollbackTransaction() throws SQLException {
        Connection connection = ConnectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');");
        Savepoint savepoint1 = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');");
        connection.releaseSavepoint(savepoint1);

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');");
        connection.rollback();

        PostgresqlTransactionAutoCommitTest.getCount(statement, 0);
        statement.close();
        ConnectionSql.closeConnection();
    }

    /**
     * --transaction with ERROR! (1 savepoint and 1 releasesavepoints and one ROLLBACK TO SAVEPOINT)-----9
     */
    @Test
    void test_SavepointAndReleaseItAndRollbackToSavepoint() throws SQLException {
        Connection connection = ConnectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');");
        Savepoint savepoint1 = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');");
        connection.releaseSavepoint(savepoint1);
        SQLException exception = Assertions.assertThrows(SQLException.class, () -> connection.rollback(savepoint1));
        if (exception == null) {
            connection.commit();
        } else {
            connection.rollback();
            PostgresqlTransactionAutoCommitTest.getCount(statement, 0);
        }

//        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');");

        statement.close();
        ConnectionSql.closeConnection();
    }

    /**
     * --transaction with ERROR! (2 savepoints with the same names, after ROLLBACK TO SAVEPOINT, RELEASE SAVEPOINT and again ROLLBACK TO SAVEPOINT)-----10
     */
    @Test
    void test_2SavepointsTheSameNamesAndRollbackToSavepointAfterReleaseToSavepointAndAgainRollbackToSavepoint() throws SQLException {
        Connection connection = ConnectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql15');");
        Savepoint savepoint1 = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql14');");
        Savepoint savepoint1Second = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql12');");
        connection.rollback(savepoint1Second);
        connection.releaseSavepoint(savepoint1Second);
        connection.rollback(savepoint1);
        connection.commit();
        PostgresqlTransactionAutoCommitTest.getCount(statement, 1);

        statement.close();
        ConnectionSql.closeConnection();
    }

    /**
     * --inserted both -----11
     */
    @Test
    void test_SavepointWithThreadSleep() throws SQLException, InterruptedException {
        Connection connection = ConnectionSql.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql15');");
        Savepoint savepoint1 = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL8');");
        Thread.sleep(5_000);
        connection.releaseSavepoint(savepoint1);

        connection.commit();
        PostgresqlTransactionAutoCommitTest.getCount(statement, 2);

        statement.close();
        ConnectionSql.closeConnection();
    }

    /**
     * --inserted both -----11 - sql full thru java
     */
    @Test
    void test_SavepointWithThreadSleepThruJava() throws SQLException, InterruptedException {
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("BEGIN;").append(StringUtils.LF);
        sqlStatement.append("SELECT * FROM t_users_pk_int;--1").append(StringUtils.LF);
        sqlStatement.append("DELETE FROM t_users_pk_int;").append(StringUtils.LF);
        sqlStatement.append("COMMIT AND CHAIN;").append(StringUtils.LF);
        sqlStatement.append("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql15');").append(StringUtils.LF);
        sqlStatement.append("SAVEPOINT savepoint1;").append(StringUtils.LF);
        sqlStatement.append("INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL8');").append(StringUtils.LF);
        sqlStatement.append("SELECT pg_sleep(5);").append(StringUtils.LF);
        sqlStatement.append("RELEASE SAVEPOINT savepoint1;").append(StringUtils.LF);
        sqlStatement.append("SELECT * FROM t_users_pk_int;").append(StringUtils.LF);
        sqlStatement.append("COMMIT;").append(StringUtils.LF);

        Connection connection = ConnectionSql.getConnection();
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        PostgresqlTransactionAutoCommitTest.getCount(statement, 0);

        statement.execute(sqlStatement.toString());
        PostgresqlTransactionAutoCommitTest.getCount(statement, 2);

        statement.close();
        ConnectionSql.closeConnection();
    }

    /**
     * --SQL Error [25P01]: ERROR: RELEASE SAVEPOINT can only be used in transaction blocks
     */
    @Test
    void test_Error_25P01() throws SQLException {
        Connection connection = ConnectionSql.getConnection();
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        PostgresqlTransactionAutoCommitTest.getCount(statement, 0);

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql15');");
        Savepoint savepoint1 = connection.setSavepoint("savepoint1");

        statement.executeUpdate("INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL8');");
        connection.commit();
        SQLException exception = Assertions.assertThrows(SQLException.class, () -> connection.releaseSavepoint(savepoint1));
        if (exception != null) {
            Assertions.assertEquals("ERROR: RELEASE SAVEPOINT can only be used in transaction blocks", exception.getMessage());
            Assertions.assertEquals("25P01", exception.getSQLState());
        }
        PostgresqlTransactionAutoCommitTest.getCount(statement, 2);

        statement.close();
        ConnectionSql.closeConnection();
    }
}
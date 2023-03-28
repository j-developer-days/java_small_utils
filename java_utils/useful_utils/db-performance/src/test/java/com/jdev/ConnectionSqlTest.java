package com.jdev;

import com.jdev.console.ConsoleUtils;
import com.jdev.generateSql.GenerateInsertSql;
import com.jdev.thread.ThreadUtils;
import com.jdev.util.RandomUtils;
import com.jdev.util.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ConnectionSqlTest {

    @AfterEach
    private void printAllData() {
        ConsoleUtils.printDelimiter();
        ConsoleUtils.printToConsole("Begin print data ... ");
        ConnectionSql connectionSql = ConnectionSql.getInstanceThreadSafe();
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
        ConnectionSql connectionSql = ConnectionSql.getInstanceThreadSafe();
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

    @Test
    void test_ConnectionSql_MultiThreadEnvironment() {
        Runnable runnable = () -> {
            try {
                createConnectionToSql(-1);
            } catch (SQLException e) {
                ConsoleUtils.logError("exception in Runnable - " + ThreadUtils.getCurrentThreadName(), e);
            }
        };

        List<Thread> threads = List.of(new Thread(runnable, "T1"), new Thread(runnable, "T2"), new Thread(runnable, "T3"),
                new Thread(runnable, "T4"), new Thread(runnable, "T5"));
        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                ConsoleUtils.logError("thread.join();", e);
            }
        }
    }

    @Test
    void test_Runnable() {
        Runnable runnable = () -> {
            ConnectionSql connectionSql = ConnectionSql.getInstanceWithSleepThreadSafe();
            try (Connection connection = connectionSql.getConnection()) {
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();


                int id = RandomUtils.randomFromTo(1, 150);
                String firstName = GenerateInsertSql.FAKER.funnyName().name();
                ConsoleUtils.printToConsole("#" + id + StringUtils.TAB + " firstName = " + firstName + StringUtils.TAB + Thread.currentThread().getName());

                statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES (" + id + ", '"
                        + firstName + "');");
                connection.commit();
            } catch (SQLException e) {
                ConsoleUtils.logError("Connection problem!", e);
            }
        };

        List<Thread> threads = List.of(
                new Thread(runnable, "T1"),
                new Thread(runnable, "T2"),
                new Thread(runnable, "T3"));
        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                ConsoleUtils.logError("thread.join();", e);
            }
        }
    }

    @Test
    void test_ThreadClass() {
        List<Thread> threads = List.of(
                new ThreadInsert("T_1", 10, "Dave"),
                new ThreadInsert("T_2", 20, "Joshua"),
                new ThreadInsert("T_3", 30, "Harold"),
                new ThreadInsert("T_4", 40, "Ricardo"),
                new ThreadInsert("T_5", 50, "Hunter")
        );
        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                ConsoleUtils.logError("thread.join();", e);
            }
        }
    }

    @Test
    void test_ExecutorService() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 50; i++) {
            es.execute(new ThreadInsert("T" + i, i, "Name" + i));
        }
        es.shutdown();
        ConsoleUtils.printToConsole(es.awaitTermination(5, TimeUnit.SECONDS));
    }

    private void createConnectionToSql(int number) throws SQLException {
        ConnectionSql connectionSql = ConnectionSql.getInstanceWithSleepThreadSafe();
        Connection connection = connectionSql.getConnection();
        ConsoleUtils.printToConsole(ThreadUtils.getCurrentThreadName() + "\tconnectionSql" + (number == -1 ?
                StringUtils.EMPTY : number) + ".getUuid() = "
                + connectionSql.getUuid() + " - connection.hashCode() - " + connection.hashCode() + StringUtils.TAB
                + "alive connection - " + !connection.isClosed());
        SqlHelper.closeConnection(connection);
    }

    private static class ThreadInsert extends Thread {

        private final Integer id;
        private final String firstName;

        public ThreadInsert(String name, Integer id, String firstName) {
            super(name);
            this.id = id;
            this.firstName = firstName + "\t" + name;
        }

        @Override
        public void run() {
            ConnectionSql connectionSql = ConnectionSql.getInstanceWithSleepThreadSafe();
            try (Connection connection = connectionSql.getConnection()) {
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO t_users_pk_int (id, firstname) VALUES ("
                        + this.id + ", '" + this.firstName + "');");
                connection.commit();
                SqlHelper.closeStatement(statement);
                ConsoleUtils.printToConsole("Success saved - " + this.id + "[" + ThreadUtils.getCurrentThreadName() + "]");
            } catch (SQLException e) {
                ConsoleUtils.logError("Connection problem inner thread! - ["
                        + ThreadUtils.getCurrentThreadName() + "]", e);
            }
        }
    }
}
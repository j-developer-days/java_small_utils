package com.jdev.generateSql;

import com.jdev.ConnectionSql;
import com.jdev.TestHelper;
import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

class GenerateInsertSqlTest {

    @BeforeEach
    private void beforeEach() {
        try (Connection connection = ConnectionSql.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM t_users_pk_int");
            int count = preparedStatement.executeUpdate();
            ConsoleUtils.printToConsole("deleted - " + count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test_simpleGeneratorInsertIntoSql() throws SQLException {
        Connection connectionInner = null;
        try (Connection connection = ConnectionSql.getConnection();) {
            connection.setAutoCommit(false);

            String queryInsert = GenerateInsertSql.simpleGeneratorInsertIntoSql("t_users_pk_int",
                    new ArrayList<>() {{
                        add(ColumnDetails.builder().columnName("id").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextInt(-32_768, 32_767) +
                                "").build());
                        add(ColumnDetails.builder().columnName("firstname").addQuotationMark(true).generateColumnValue(s -> GenerateInsertSql.FAKER.name().firstName()).build());
                    }}, 100);

            ConsoleUtils.printToConsole(queryInsert);

            PreparedStatement preparedStatement = connection.prepareStatement(queryInsert);
            int count = preparedStatement.executeUpdate();
            connection.commit();

            Assertions.assertEquals(100, count);
            Assertions.assertFalse(connection.isReadOnly());

            connection.setReadOnly(true);
            preparedStatement = connection.prepareStatement("select * from t_users_pk_int");
            ResultSet resultSet = preparedStatement.executeQuery();
            count = 0;
            while (resultSet.next()) {
                TestHelper.printSelectQuery(resultSet,
                        new TestHelper.Pair[]{new TestHelper.Pair<>("id", JDBCType.SMALLINT), new TestHelper.Pair<>(
                                "firstname", JDBCType.VARCHAR)});
                count++;
            }
            if (!resultSet.isClosed()) {
                resultSet.close();
            }
            Assertions.assertTrue(resultSet.isClosed());
            Assertions.assertEquals(100, count);
            Assertions.assertFalse(connection.isClosed());
            Assertions.assertTrue(connection.isReadOnly());
            connectionInner = connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Assertions.assertTrue(connectionInner.isClosed());
    }

    @Test
    void test_AutoCommit() {
        String queryInsert = GenerateInsertSql.simpleGeneratorInsertIntoSql("t_users_pk_int",
                new ArrayList<>() {{
                    add(ColumnDetails.builder().columnName("id").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextInt(-32_768, 32_767) + "").build());
                    add(ColumnDetails.builder().columnName("firstname").addQuotationMark(true).generateColumnValue(s -> GenerateInsertSql.FAKER.name().firstName()).build());
                }}, 100);

        String queryInsertBad = GenerateInsertSql.simpleGeneratorInsertIntoSql("t_users_pk_int",
                new ArrayList<>() {{
                    add(ColumnDetails.builder().columnName("id").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextLong(2147483648L) + 2147483648L + "").build());
                    add(ColumnDetails.builder().columnName("firstname").addQuotationMark(true).generateColumnValue(s -> GenerateInsertSql.FAKER.name().firstName()).build());
                }}, 2);
        ConsoleUtils.printToConsole(queryInsertBad);

        try (Connection connection = ConnectionSql.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(queryInsert);
            int count = preparedStatement.executeUpdate();
            ConsoleUtils.printToConsole("inserted - " + count);

            preparedStatement = connection.prepareStatement(queryInsertBad);
            count = preparedStatement.executeUpdate();
            ConsoleUtils.printToConsole("inserted bad - " + count);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test_AutoCommit_withTryCatch() {
        String queryInsert = GenerateInsertSql.simpleGeneratorInsertIntoSql("t_users_pk_int",
                new ArrayList<>() {{
                    add(ColumnDetails.builder().columnName("id").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextInt(-32_768, 32_767) + "").build());
                    add(ColumnDetails.builder().columnName("firstname").addQuotationMark(true).generateColumnValue(s -> GenerateInsertSql.FAKER.name().firstName()).build());
                }}, 100);

        String queryInsertBad = GenerateInsertSql.simpleGeneratorInsertIntoSql("t_users_pk_int",
                new ArrayList<>() {{
                    add(ColumnDetails.builder().columnName("id").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextLong(2147483648L) + 2147483648L + "").build());
                    add(ColumnDetails.builder().columnName("firstname").addQuotationMark(true).generateColumnValue(s -> GenerateInsertSql.FAKER.name().firstName()).build());
                }}, 2);
        ConsoleUtils.printToConsole(queryInsertBad);


        String queryInsert2 = GenerateInsertSql.simpleGeneratorInsertIntoSql("t_users_pk_int",
                new ArrayList<>() {{
                    add(ColumnDetails.builder().columnName("id").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextInt(-32_768, 32_767) + "").build());
                    add(ColumnDetails.builder().columnName("firstname").addQuotationMark(true).generateColumnValue(s -> GenerateInsertSql.FAKER.name().firstName()).build());
                }}, 20, true);

        try (Connection connection = ConnectionSql.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(queryInsert);
            int count = preparedStatement.executeUpdate();

            ConsoleUtils.printToConsole("inserted - " + count);

            preparedStatement = connection.prepareStatement(queryInsertBad);
            try {
                count = preparedStatement.executeUpdate();
                ConsoleUtils.printToConsole("inserted bad - " + count);
            } catch (SQLException e) {
                ConsoleUtils.logError("Exception with insert", e);
            }

            preparedStatement = connection.prepareStatement(queryInsert2);
            count = preparedStatement.executeUpdate();
            ConsoleUtils.printToConsole("inserted - " + count);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test_AutoCommitFalse() {
        String queryInsert = GenerateInsertSql.simpleGeneratorInsertIntoSql("t_users_pk_int",
                new ArrayList<>() {{
                    add(ColumnDetails.builder().columnName("id").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextInt(-32_768, 32_767) + "").build());
                    add(ColumnDetails.builder().columnName("firstname").addQuotationMark(true).generateColumnValue(s -> GenerateInsertSql.FAKER.name().firstName()).build());
                }}, 100);

        String queryInsertBad = GenerateInsertSql.simpleGeneratorInsertIntoSql("t_users_pk_int",
                new ArrayList<>() {{
                    add(ColumnDetails.builder().columnName("id").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextLong(2147483648L) + 2147483648L + "").build());
                    add(ColumnDetails.builder().columnName("firstname").addQuotationMark(true).generateColumnValue(s -> GenerateInsertSql.FAKER.name().firstName()).build());
                }}, 2);
        ConsoleUtils.printToConsole(queryInsertBad);


        String queryInsert2 = GenerateInsertSql.simpleGeneratorInsertIntoSql("t_users_pk_int",
                new ArrayList<>() {{
                    add(ColumnDetails.builder().columnName("id").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextInt(-32_768, 32_767) + "").build());
                    add(ColumnDetails.builder().columnName("firstname").addQuotationMark(true).generateColumnValue(s -> GenerateInsertSql.FAKER.name().firstName()).build());
                }}, 20, true);

        try (Connection connection = ConnectionSql.getConnection();) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(queryInsert);
            int count = preparedStatement.executeUpdate();

            ConsoleUtils.printToConsole("inserted - " + count);

            preparedStatement = connection.prepareStatement(queryInsert2);
            count = preparedStatement.executeUpdate();
//            connection.commit();

            preparedStatement = connection.prepareStatement(queryInsertBad);
            try {
                count = preparedStatement.executeUpdate();
                ConsoleUtils.printToConsole("inserted bad - " + count);
            } catch (SQLException e) {
                ConsoleUtils.logError("Exception with insert", e);
//                connection.rollback();
            }
            ConsoleUtils.printToConsole("inserted - " + count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test_connectionReadOnly() throws SQLException {
        String queryInsert = GenerateInsertSql.simpleGeneratorInsertIntoSql("t_users_pk_int",
                new ArrayList<>() {{
                    add(ColumnDetails.builder().columnName("id").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextInt(-32_768, 32_767) + "").build());
                    add(ColumnDetails.builder().columnName("firstname").addQuotationMark(true).generateColumnValue(s -> GenerateInsertSql.FAKER.name().firstName()).build());
                }}, 20, true);

        Connection connection = ConnectionSql.getConnection();
        connection.setAutoCommit(false);
        connection.setReadOnly(false);
        PreparedStatement preparedStatement = connection.prepareStatement(queryInsert);
        int count = preparedStatement.executeUpdate();

        ConsoleUtils.printToConsole("inserted - " + count);

        connection.commit();

        connection.setReadOnly(true);
        preparedStatement = connection.prepareStatement("select * from t_users_pk_int");
        ResultSet resultSet = preparedStatement.executeQuery();
        count = 0;
        while (resultSet.next()) {
            TestHelper.printSelectQuery(resultSet,
                    new TestHelper.Pair[]{new TestHelper.Pair<>("id", JDBCType.SMALLINT), new TestHelper.Pair<>(
                            "firstname", JDBCType.VARCHAR)});
            count++;
        }
        if (!resultSet.isClosed()) {
            resultSet.close();
        }

        ConnectionSql.closeConnection();
    }

}
package com.jdev.generateSql;

import com.jdev.ConnectionSql;
import com.jdev.SqlHelper;
import com.jdev.TestHelper;
import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class GenerateInsertSqlTest {

    private static ConnectionSql connectionSql = ConnectionSql.getInstanceThreadSafe();

    @BeforeEach
    private void beforeEach() {
        try (Connection connection = connectionSql.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement;
            int count;
            for (String tableName : List.of("t_users_pk_int", "t_user_numbers", "t_type_money", "t_type_text")) {
                preparedStatement = connection.prepareStatement(GenerateSqlQuery.getDeleteFrom(tableName));
                count = preparedStatement.executeUpdate();
                connection.commit();
                ConsoleUtils.printToConsole(tableName + " - deleted - " + count);
            }
        } catch (SQLException e) {
            ConsoleUtils.logError("db connection", e);
        }
    }

    @Test
    void test_simpleGeneratorInsertIntoSql() throws SQLException {
        Connection connectionInner = null;
        try (Connection connection = connectionSql.getConnection();) {
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

    //https://www.postgresql.org/docs/current/datatype-numeric.html
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void test_simpleGeneratorInsertIntoSql_Number_Data_Types(boolean isDifferentInsertInto) throws SQLException {
        ConsoleUtils.printToConsole("run - test_simpleGeneratorInsertIntoSql_Number_Data_Types - , param = " + isDifferentInsertInto);
        try (Connection connection = connectionSql.getConnection();) {
            connection.setAutoCommit(false);

            final String tableName = "t_user_numbers";
            final int countGenerate = 100;
            String queryInsert = GenerateInsertSql.simpleGeneratorInsertIntoSql(tableName,
                    new ArrayList<>() {{
                        add(ColumnDetails.builder().columnName("age").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextInt(-32_768, 32_767) + "").build());
                        add(ColumnDetails.builder().columnName("age_int").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextInt(-65_768, 65_767) + "").build());
                        add(ColumnDetails.builder().columnName("big_number").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextLong() + "").build());
                        add(ColumnDetails.builder().columnName("price").generateColumnValue(s -> GenerateInsertSql.RANDOM.nextFloat() + "").build());
                        add(ColumnDetails.builder().columnName("total_price").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextDouble() + "").build());
                    }}, countGenerate, isDifferentInsertInto);

            ConsoleUtils.printToConsole(queryInsert);

            PreparedStatement preparedStatement = connection.prepareStatement(queryInsert);
            int count = preparedStatement.executeUpdate();
            connection.commit();

            Assertions.assertEquals(isDifferentInsertInto ? 1 : countGenerate, count);
            Assertions.assertFalse(connection.isReadOnly());

            connection.setReadOnly(true);
            preparedStatement = connection.prepareStatement(GenerateSqlQuery.getSelectAllFrom(tableName));
            count = 0;
            TestHelper.Pair[] pairs = new TestHelper.Pair[]{
                    new TestHelper.Pair<>("id_small", JDBCType.SMALLINT),
                    new TestHelper.Pair<>("id", JDBCType.INTEGER),
                    new TestHelper.Pair<>("id_bigserial", JDBCType.BIGINT),
                    new TestHelper.Pair<>("age", JDBCType.SMALLINT),
                    new TestHelper.Pair<>("age_int", JDBCType.INTEGER),
                    new TestHelper.Pair<>("big_number", JDBCType.BIGINT),
                    new TestHelper.Pair<>("price", JDBCType.REAL),
                    new TestHelper.Pair<>("total_price", JDBCType.DOUBLE)
            };
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    TestHelper.printSelectQuery(resultSet, pairs);
                    count++;
                }
            }

            SqlHelper.closeStatement(preparedStatement);

            Assertions.assertEquals(countGenerate, count);
            Assertions.assertFalse(connection.isClosed());
            Assertions.assertTrue(connection.isReadOnly());
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void test_moneyTypeAsVarchar(boolean isDifferentInsertInto) throws SQLException {
        commonFor_test_moneyType(isDifferentInsertInto, new ArrayList<>() {{
            add(ColumnDetails.builder().addQuotationMark(true).columnName("total_amount")
                    .generateColumnValue(s -> "$" + (GenerateInsertSql.FAKER.random().nextDouble() + Integer.parseInt(s))).build());
        }});
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void test_moneyTypeAsNumber(boolean isDifferentInsertInto) throws SQLException {
        commonFor_test_moneyType(isDifferentInsertInto, List.of(ColumnDetails.builder().columnName("total_amount")
                .generateColumnValue(s -> (GenerateInsertSql.FAKER.random().nextBoolean() ?
                        GenerateInsertSql.FAKER.random().nextDouble() : GenerateInsertSql.FAKER.random().nextInt(0,
                        1_000_000)) + "").build()));
    }

    private void commonFor_test_moneyType(boolean isDifferentInsertInto, List<ColumnDetails> columnDetails) throws SQLException {
        ConsoleUtils.printToConsole("run - test_moneyType - , param = " + isDifferentInsertInto);
        try (Connection connection = connectionSql.getConnection();) {
            connection.setAutoCommit(false);

            final String tableName = "t_type_money";
            final int countGenerate = 150;
            String queryInsert = GenerateInsertSql.simpleGeneratorInsertIntoSql(tableName, columnDetails, countGenerate, isDifferentInsertInto);

            ConsoleUtils.printToConsole(queryInsert);

            PreparedStatement preparedStatement = connection.prepareStatement(queryInsert);
            int count = preparedStatement.executeUpdate();
            connection.commit();

            Assertions.assertEquals(isDifferentInsertInto ? 1 : countGenerate, count);
            Assertions.assertFalse(connection.isReadOnly());

            connection.setReadOnly(true);
            preparedStatement = connection.prepareStatement(GenerateSqlQuery.getSelectAllFrom(tableName));
            count = 0;
            TestHelper.Pair[] pairs = new TestHelper.Pair[]{
                    new TestHelper.Pair<>("id", JDBCType.SMALLINT),
                    new TestHelper.Pair<>("total_amount", JDBCType.VARCHAR)
            };
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    TestHelper.printSelectQuery(resultSet, pairs);
                    count++;
                }
            }

            SqlHelper.closeStatement(preparedStatement);

            Assertions.assertEquals(countGenerate, count);
            Assertions.assertFalse(connection.isClosed());
            Assertions.assertTrue(connection.isReadOnly());
        }

    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void test_textType(boolean isDifferentInsertInto) throws SQLException {
        ConsoleUtils.printToConsole("run - test_textType - , param = " + isDifferentInsertInto);
        try (Connection connection = connectionSql.getConnection();) {
            connection.setAutoCommit(false);

            final String tableName = "t_type_text";
            final int countGenerate = 75;
            String queryInsert = GenerateInsertSql.simpleGeneratorInsertIntoSql(tableName,
                    List.of(ColumnDetails.builder().addQuotationMark(true).columnName("type").size(10)
                                    .isChangeSpecialSign(true).generateColumnValue(s -> GenerateInsertSql.FAKER.address().fullAddress() + s).build(),
                            ColumnDetails.builder().addQuotationMark(true).columnName("gender").size(1)
                                    .isChangeSpecialSign(true).generateColumnValue(s -> (GenerateInsertSql.FAKER.animal().name())).build(),
                            ColumnDetails.builder().addQuotationMark(true).columnName("description")
                                    .isChangeSpecialSign(true).generateColumnValue(s -> (GenerateInsertSql.FAKER.book().author())).build()),
                    countGenerate, isDifferentInsertInto);

            ConsoleUtils.printToConsole(queryInsert);

            PreparedStatement preparedStatement = connection.prepareStatement(queryInsert);
            int count = preparedStatement.executeUpdate();
            connection.commit();

            Assertions.assertEquals(isDifferentInsertInto ? 1 : countGenerate, count);
            Assertions.assertFalse(connection.isReadOnly());

            connection.setReadOnly(true);
            preparedStatement = connection.prepareStatement(GenerateSqlQuery.getSelectAllFrom(tableName));
            count = 0;
            TestHelper.Pair<String, JDBCType>[] pairs = new TestHelper.Pair[]{
                    new TestHelper.Pair<>("id", JDBCType.SMALLINT),
                    new TestHelper.Pair<>("type", JDBCType.VARCHAR),
                    new TestHelper.Pair<>("gender", JDBCType.VARCHAR),
                    new TestHelper.Pair<>("description", JDBCType.LONGVARCHAR)};
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    TestHelper.printSelectQuery(resultSet, pairs);
                    count++;
                }
            }

            SqlHelper.closeStatement(preparedStatement);

            Assertions.assertEquals(countGenerate, count);
            Assertions.assertFalse(connection.isClosed());
            Assertions.assertTrue(connection.isReadOnly());
        }
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

        try (Connection connection = connectionSql.getConnection();) {
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

        try (Connection connection = connectionSql.getConnection();) {
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

        try (Connection connection = connectionSql.getConnection();) {
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

        Connection connection = connectionSql.getConnection();
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

        SqlHelper.closeConnection(connection);
    }

}
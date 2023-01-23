package com.jdev.generateSql;

import com.jdev.ConnectionSql;
import com.jdev.TestHelper;
import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

class GenerateInsertSqlTest {

    @Test
    void test_simpleGeneratorInsertIntoSql() throws SQLException {
        Connection connectionInner = null;
        try (Connection connection = ConnectionSql.getConnection();) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from t_users_pk_int");
            int count = preparedStatement.executeUpdate();
            connection.commit();
            ConsoleUtils.printToConsole("deleted - " + count);
            Assertions.assertTrue(count > 0);

            String queryInsert = GenerateInsertSql.simpleGeneratorInsertIntoSql("t_users_pk_int",
                    new ArrayList<>() {{
                        add(ColumnDetails.builder().columnName("id").generateColumnValue(s -> GenerateInsertSql.FAKER.random().nextInt(-32_768, 32_767) +
                                "").build());
                        add(ColumnDetails.builder().columnName("firstname").addQuotationMark(true).generateColumnValue(s -> GenerateInsertSql.FAKER.name().firstName()).build());
                    }}, 100);

            ConsoleUtils.printToConsole(queryInsert);

            preparedStatement = connection.prepareStatement(queryInsert);
            count = preparedStatement.executeUpdate();
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

}
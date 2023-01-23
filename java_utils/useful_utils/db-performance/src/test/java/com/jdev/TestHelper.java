package com.jdev;

import com.jdev.console.ConsoleUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestHelper {

    public static void printSelectQuery(ResultSet resultSet, Pair<String, JDBCType>[] columnWithTypes) throws
            SQLException {
        StringBuilder result = new StringBuilder();
        for (Pair<String, JDBCType> columnWithType : columnWithTypes) {
            result.append(columnWithType.getKey()).append(" - ");
            switch (columnWithType.getValue()) {
                case VARCHAR: {
                    result.append(resultSet.getString(columnWithType.getKey()));
                    break;
                }
                case SMALLINT: {
                    result.append(resultSet.getShort(columnWithType.getKey()));
                    break;
                }
                case INTEGER: {
                    result.append(resultSet.getInt(columnWithType.getKey()));
                    break;
                }
                case BIGINT: {
                    result.append(resultSet.getLong(columnWithType.getKey()));
                    break;
                }
                case REAL: {
                    result.append(resultSet.getFloat(columnWithType.getKey()));
                    break;
                }
                case DOUBLE: {
                    result.append(resultSet.getDouble(columnWithType.getKey()));
                    break;
                }
                default: {
                    throw new RuntimeException("Not implemented - " + columnWithType.getValue());
                }
            }
            result.append("\t");
        }

        ConsoleUtils.printToConsole(result);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Pair<K, V> {
        private K key;
        private V value;
    }

}

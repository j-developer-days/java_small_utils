package com.jdev.generateSql;

import com.github.javafaker.Faker;
import com.jdev.util.StringUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GenerateInsertSql {

    public static final Faker FAKER = new Faker();
    public static final Random RANDOM = new Random();

    public static String simpleGeneratorInsertIntoSql(String tableName, List<ColumnDetails> columnDetails, int count) {
        return simpleGeneratorInsertIntoSql(tableName, columnDetails, count, false);
    }

    public static String simpleGeneratorInsertIntoSql(String tableName, List<ColumnDetails> columnDetails, int count,
                                                      boolean isDifferentInsertInto) {
        StringBuilder query =
                new StringBuilder(GenerateSqlQuery.INSERT_INTO).append(StringUtils.SPACE).append(tableName).append(" (").append(
                        columnDetails.stream().map(ColumnDetails::getColumnName).collect(
                                Collectors.joining(", "))).append(")").append(StringUtils.SPACE).append(GenerateSqlQuery.VALUES).append(StringUtils.SPACE);
        addValues(query, columnDetails, count, isDifferentInsertInto, isDifferentInsertInto ? query.toString() : null);
        return query.toString();
    }

    private static void addValues(StringBuilder query, List<ColumnDetails> columnDetails, int count,
                                  boolean isDifferentInsertInto, String queryInsertInto) {
        for (var i = 1; i <= count; i++) {
            if (isDifferentInsertInto && i != 1) {
                query.append(queryInsertInto);
            }
            query.append("(");

            final int functionsSize = columnDetails.size();
            final int lastIndex = functionsSize - 1;
            for (var j = 0; j < functionsSize; j++) {
                ColumnDetails columnDetailsCurrent = columnDetails.get(j);
                if (columnDetailsCurrent.isAddQuotationMark()) {
                    query.append("'");
                    addValueForColumn(query, columnDetailsCurrent, i);
                    query.append("'");
                } else {
                    addValueForColumn(query, columnDetailsCurrent, i);
                }

                if (lastIndex != j) {
                    query.append(", ");
                }
            }

            query.append(")");
            if (isDifferentInsertInto) {
                query.append(";").append("\n");
            } else {
                if (i == count) {
                    query.append(";").append("\n");
                } else {
                    query.append(",").append("\n");
                }
            }
        }
    }

    private static void addValueForColumn(StringBuilder query, ColumnDetails columnDetailsCurrent, int i) {
        String value = columnDetailsCurrent.getGenerateColumnValue().apply(Integer.toString(i));
        if (columnDetailsCurrent.isChangeSpecialSign()) {
            value = value.replaceAll("[']", "''");
        }
        if (columnDetailsCurrent.getSize() >= 1 && value.length() > columnDetailsCurrent.getSize()) {
            value = value.substring(0, columnDetailsCurrent.getSize());
        }
        query.append(value);
    }

}

package com.jdev.generateSql;

import lombok.*;

import java.sql.JDBCType;
import java.util.function.Function;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ColumnDetails {
    private String columnName;
    private JDBCType jdbcType;
    private boolean addQuotationMark;
    private int size;
    private boolean isChangeSpecialSign;
    private Function<String, String> generateColumnValue;

    private ColumnDetails(String columnName, Function<String, String> generateColumnValue) {
        this.columnName = columnName;
        this.generateColumnValue = generateColumnValue;
    }

    public static ColumnDetails createColumnDetails(String columnName, Function<String, String> generateColumnValue) {
        return new ColumnDetails(columnName, generateColumnValue);
    }
}


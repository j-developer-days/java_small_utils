package com.jdev.generateSql;

import com.jdev.util.StringUtils;

public class GenerateSqlQuery {

    public static final String INSERT_INTO = "INSERT INTO";
    public static final String DELETE_FROM = "DELETE FROM";
    public static final String SELECT_ALL_FROM = "SELECT * FROM";
    public static final String VALUES = "VALUES";

    public static String getDeleteFrom(String tableName) {
        return DELETE_FROM + StringUtils.SPACE + tableName;
    }

    public static String getSelectAllFrom(String tableName) {
        return SELECT_ALL_FROM + StringUtils.SPACE + tableName;
    }

}

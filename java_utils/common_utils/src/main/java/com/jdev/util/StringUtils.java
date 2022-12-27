package com.jdev.util;

public class StringUtils {

    public static final String APOSTROPHE = "'";
    public static final String TAB = "\t";
    public static final String NEW_LINE = "\n";
    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String COLON = ":";

    private static final int DEFAULT_COUNT = 150;

    public static String multipleCharByCount(char c, int count) {
        if (count <= 0) {
            count = DEFAULT_COUNT;
        }
        StringBuilder result = new StringBuilder(count);
        for (var i = 0; i < count; i++) {
            result.append(c);
        }
        return result.toString();
    }

    public static String multipleCharByCount(String text, int count) {
        if (count <= 0) {
            count = DEFAULT_COUNT;
        }
        return String.valueOf(text).repeat(count);
    }

}

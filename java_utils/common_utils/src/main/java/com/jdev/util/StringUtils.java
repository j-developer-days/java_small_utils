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
        int innerCount = checkCount(count);
        StringBuilder result = new StringBuilder(innerCount);
        for (var i = 0; i < innerCount; i++) {
            result.append(c);
        }
        return result.toString();
    }

    public static String multipleStringByCount(String text, int count) {
        return text.repeat(checkCount(count));
    }

    private static int checkCount(int count) {
        return count <= 0 ? DEFAULT_COUNT : count;
    }

}

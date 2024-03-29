package com.jdev.console;

import com.jdev.util.StringUtils;

public class ConsoleUtils {

    public static void printDelimiter(char c, int count) {
        printToConsole(StringUtils.multipleCharByCount(c, count));
    }

    public static void printDelimiter(char c) {
        printDelimiter(c, -1);
    }

    public static void printDelimiter() {
        printDelimiter('*', -1);
    }

    public static void logError(String message, Exception e) {
        printToConsole("ERROR: " + message + "\t exception info - " + e.getClass().getCanonicalName() + "\tmessage - " + e.getMessage());
    }

    public static void printToConsole(Object o) {
        System.out.println(o);
    }

    public static void printToConsole() {
        System.out.println();
    }

    public static void printDelimiter(String text, int count) {
        printToConsole(StringUtils.multipleStringByCount(text, count));
    }

    public static void printDelimiterWithString(String text) {
        printDelimiter(text, -1);
    }

    public static void printDelimiterWithString(String text, int count) {
        printDelimiter(text, count);
    }

}

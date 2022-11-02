package com.jdev.utils;

public class ConsoleUtil {

    private static final int DEFAULT_COUNT = 150;

    public static void printDelimiter(char c, int count){
        if(count <= 0){
            count = DEFAULT_COUNT;
        }
        StringBuilder result = new StringBuilder(count);
        for (var i = 0; i < count; i++){
            result.append(c);
        }
        System.out.println(result);
    }

    public static void printDelimiter(char c){
        printDelimiter(c, DEFAULT_COUNT);
    }

}

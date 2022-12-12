package com.jdev.console;

import com.jdev.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFromConsole {

    private static final String CONSOLE_STOP = "/89";

    public static String getStringFromConsole() {
        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            String current;
            while (!(current = reader.readLine()).equals(CONSOLE_STOP)) {
                result.append(current).append(StringUtils.NEW_LINE);
            }
            return result.toString();
        } catch (IOException e) {
            ConsoleUtils.logError("Reading from console", e);
        }
        return StringUtils.EMPTY;
    }

    public static void main(String[] args) {
        ConsoleUtils.printToConsole("Please write text:(for close add - " + CONSOLE_STOP + ")");
        ConsoleUtils.printToConsole(getStringFromConsole());
    }

}

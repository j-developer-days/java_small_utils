package com.jdev.arrays;

import com.jdev.console.ConsoleUtils;
import com.jdev.util.StringUtils;

public interface ArrayWorkingI {

    /**
     * Factory Method Pattern
     */
    int fillArray();

    static void printArray(int[] array) {
        for (var i = 0; i < array.length; i++) {
            ConsoleUtils.printToConsole("#" + i + StringUtils.TAB + " - " + array[i]);
        }
    }

}

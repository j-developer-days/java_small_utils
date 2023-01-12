package com.jdev.arrays;

import com.jdev.console.ConsoleUtils;
import com.jdev.util.StringUtils;

public interface ArrayWorkingI {

    /**
     * Factory Method Pattern
     */
    int fillArray();

    static int sum(int[] array) {
        int sum = 0;
        for (var i : array) {
            sum += i;
        }
        return sum;
    }

    static double average(int[] array) {
        int sum = 0;
        final int arrayLength = array.length;
        for (int i = arrayLength - 1; i >= 0; i--) {
            sum += array[i];
        }
        return sum / arrayLength;
    }

    default void printArray(int[] array) {
        for (var i = 0; i < array.length; i++) {
            ConsoleUtils.printToConsole("#" + i + StringUtils.TAB + " - " + array[i]);
        }
    }

}

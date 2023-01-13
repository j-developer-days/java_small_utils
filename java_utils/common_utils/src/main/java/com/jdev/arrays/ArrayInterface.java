package com.jdev.arrays;

import com.jdev.console.ConsoleUtils;

public interface ArrayInterface {

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

    /**
     * Factory Method Pattern
     * */
    int fillArray();

    default void printArray(int[] array) {
        ArrayPrint.printArray(array);
    }

}

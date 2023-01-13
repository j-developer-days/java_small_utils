package com.jdev.arrays;

import com.jdev.console.ConsoleUtils;
import com.jdev.util.StringUtils;

public class ArrayPrint {

    public static void printArray(int[] array) {
        printArray(array, true);
    }

    public static void printArray(int[] array, boolean isPrintSeparatelyInNewLine) {
        final int arrayLength = array.length;
        StringBuilder forPrint = new StringBuilder(arrayLength * 2);
        for (int i = 0; i < arrayLength; i++) {
            forPrint.append("#").append(i).append(" - ").append(array[i]).append(isPrintSeparatelyInNewLine ?
                    StringUtils.NEW_LINE : StringUtils.TAB);
        }
        ConsoleUtils.printToConsole(forPrint);
    }

}

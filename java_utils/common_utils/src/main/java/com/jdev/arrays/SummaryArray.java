package com.jdev.arrays;

import com.jdev.console.ConsoleUtils;

public abstract class SummaryArray implements ArrayWorkingI{

    /**
     * Template pattern
     * https://www.tutorialspoint.com/design_pattern/template_pattern.htm
     * https://www.javatpoint.com/template-pattern
     */
    public final int[] createAndFillArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = fillArray();
        }
        return array;
    }

    /**
     * Template pattern
     * https://www.tutorialspoint.com/design_pattern/template_pattern.htm
     * https://www.javatpoint.com/template-pattern
     *
     * template method calls:
     *  createAndFillArray
     *      fillArray
     *  printArray
     */
    public final void summary(int size) {
        int[] arrayInts = createAndFillArray(size);
        printArray(arrayInts);
        ConsoleUtils.printToConsole("Summary");
        ConsoleUtils.printDelimiter('*');
        ConsoleUtils.printToConsole("Length = " + arrayInts.length);
        ConsoleUtils.printToConsole("Sum = " + ArrayWorkingI.sum(arrayInts));
        ConsoleUtils.printToConsole("Average = " + ArrayWorkingI.average(arrayInts));
    }

}

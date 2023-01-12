package com.jdev.arrays.fill;

import com.jdev.arrays.SummaryArray;
import com.jdev.console.ConsoleUtils;
import com.jdev.util.RandomUtils;
import com.jdev.util.StringUtils;
import lombok.Setter;

@Setter
public class RandomArray extends SummaryArray {

    private int startValue = 0;
    private int endValue = 1_000;

    /**
     * Factory Method Pattern
     * */
    @Override
    public int fillArray() {
        return RandomUtils.randomFromTo(startValue, endValue);
    }

    @Override
    public void printArray(int[] array) {
        final int arrayLength = array.length;

        for (int i = 0, j = arrayLength - 1; i < j; i++, j--) {
            ConsoleUtils.printToConsole("#" + i + " - " + array[i] + StringUtils.TAB + "#" + j + " - " + array[j]);
        }

        if (arrayLength % 2 == 1) {
            final int indexOdd = arrayLength / 2;
            ConsoleUtils.printToConsole("#" + indexOdd + " - " + array[indexOdd]);
        }

    }
}

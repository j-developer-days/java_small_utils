package com.jdev.algorithms.sort;

import com.jdev.arrays.ArrayPrint;
import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Test;

class BubbleSortTest {

    @Test
    void test_selectionSort() {
        int[] array = new int[]{38, 27, 43, 3, 9, 82, 10};

        ConsoleUtils.printToConsole("before");
        ArrayPrint.printArray(array);

        BubbleSort.bubbleSort(array);

        ConsoleUtils.printToConsole("after");
        ArrayPrint.printArray(array);
    }

}
package com.jdev.algorithms.sort;

import com.jdev.arrays.ArrayInterface;
import com.jdev.arrays.ArrayPrint;
import com.jdev.arrays.fill.OrderedArray;
import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void test_mergeSort(){
        int [] array = new int[]{38, 27, 43, 3, 9, 82, 10};
        ConsoleUtils.printToConsole("before");
        new OrderedArray().printArray(array);
        MergeSort.mergeSort(array, 0, array.length - 1);

        ConsoleUtils.printToConsole("after");
        ArrayPrint.printArray(array);

    }

}
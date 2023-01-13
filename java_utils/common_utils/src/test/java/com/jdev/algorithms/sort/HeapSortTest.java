package com.jdev.algorithms.sort;

import com.jdev.arrays.ArrayPrint;
import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    @Test
    void test_heapSort(){
        int[] array = new int[]{38, 27, 43, 3, 9, 82, 10};

//        int[] clone = array.clone();

        ConsoleUtils.printToConsole("before");
        ArrayPrint.printArray(array);

//        ConsoleUtils.printToConsole("before - clone");
//        ArrayPrint.printArray(clone);

        HeapSort.heapSort(array);

        ConsoleUtils.printToConsole("after");
        ArrayPrint.printArray(array);
//        ConsoleUtils.printToConsole("after - clone");
//        ArrayPrint.printArray(clone);
    }

}
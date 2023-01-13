package com.jdev.algorithms.sort;

import com.jdev.console.ConsoleUtils;

public class HeapSort {

    private static int countHeapMethod = 0;
    private static int countHeapifyMethod = 0;

    public static void heapSort(int[] array) {
        ConsoleUtils.printToConsole("countHeapMethod - " + ++countHeapMethod);
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            heapify(array, array.length, i);
        }

        int temp;
        for (int i = array.length - 1; i > 0; i--) {
            temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heapify(array, i, 0);
        }
    }

    private static void heapify(int[] array, int n, int i) {
        ConsoleUtils.printToConsole("countHeapifyMethod - " + ++countHeapifyMethod);
        int max = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[left] > array[max]) {
            max = left;
        }

        if (right < n && array[right] > array[max]) {
            max = right;
        }


        if (max != i) {
            int temp = array[i];
            array[i] = array[max];
            array[max] = temp;
            heapify(array, n, max);
        }
    }

}

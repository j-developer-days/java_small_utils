package com.jdev.algorithms.sort;

public class BubbleSort {

    public static void bubbleSort(int[] array) {
        bubbleSort(array, array.length);
    }

    public static void bubbleSort(int[] array, int n) {
        if (n == 1) {
            return;
        }

        final int nMinus1 = n - 1;

        for (var i = 0; i < nMinus1; i++) {
            if (array[i] > array[i + 1]) {
                int temp = array[i];
                array[i] = array[i + 1];
                array[i + 1] = temp;
            }
        }

        bubbleSort(array, nMinus1);
    }

}

package com.jdev.algorithms.sort;

public class InsertionSort {

    public static void insertionSort(int[] array, int length) {
        if (length <= 1) {
            return;
        }

        insertionSort(array, length - 1);

        int last = array[length - 1];
        int j = length - 2;

        while (j >= 0 && array[j] > last) {
            array[j + 1] = array[j];
            j--;
        }
        array[j + 1] = last;

    }

}

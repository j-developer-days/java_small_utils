package com.jdev.algorithms.sort;

public class SelectionSort {

    public static void selectionSort(int[] array) {
        int pos;
        int temp;
        for (int i = 0; i < array.length; i++) {
            pos = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[pos]) {
                    pos = j;
                }
            }
            temp = array[pos];
            array[pos] = array[i];
            array[i] = temp;
        }
    }

}

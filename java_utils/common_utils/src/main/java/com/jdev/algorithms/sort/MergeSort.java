package com.jdev.algorithms.sort;

import com.jdev.arrays.ArrayPrint;
import com.jdev.console.ConsoleUtils;

public class MergeSort {

    private static int countMergeMethod = 0;
    private static int countMergeSortMethod = 0;

    public static void mergeSort(int[] array, int leftIndex, int rightIndex) {
        ConsoleUtils.printToConsole("countMergeSortMethod - " + ++countMergeSortMethod);
        int middleIndex;
        if (leftIndex < rightIndex) {
            middleIndex = (leftIndex + rightIndex) / 2;
            mergeSort(array, leftIndex, middleIndex);
            mergeSort(array, middleIndex + 1, rightIndex);

            merge(array, leftIndex, middleIndex, rightIndex);
        }
    }

    private static void merge(int[] array, int leftIndex, int middleIndex, int rightIndex) {
        int low = middleIndex - leftIndex + 1;
        int high = rightIndex - middleIndex;

        int[] left = new int[low];
        int[] right = new int[high];

        int i, j;

        for (i = 0; i < low; i++) {
            left[i] = array[leftIndex + i];
        }

        for (j = 0; j < high; j++) {
            right[j] = array[middleIndex + 1 + j];
        }

        int k = leftIndex;

        i = j = 0;

        while (i < low && j < high) {
            if (left[i] <= right[j]) {
                array[k] = left[i];
                i++;
            } else {
                array[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < low) {
            array[k] = left[i];
            i++;
            k++;
        }

        while (j < high) {
            array[k] = right[j];
            j++;
            k++;
        }

        //for debug
        ConsoleUtils.printToConsole("---------" + ++countMergeMethod);
        ArrayPrint.printArray(array, false);
    }

}

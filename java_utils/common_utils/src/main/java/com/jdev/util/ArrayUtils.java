package com.jdev.util;

public class ArrayUtils {

    public static int getCount(int array[], int elementToFind, boolean inversion) {
        int count = 0;
        for (int j : array) {
            if (inversion) {
                if (elementToFind != j) {
                    count++;
                }
            } else {
                if (elementToFind == j) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int[] sliceArray(int array[], int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex < 0) {
            throw new RuntimeException("From and/or to indexes should be great or equal 0, but now, from - " + fromIndex + ", to - " + toIndex);
        }
        if (fromIndex > toIndex) {
            throw new RuntimeException("From index should be great than to, from index is - " + fromIndex + ", to " +
                    "index = " + toIndex);
        }
        final int arrayLastIndex = array.length - 1;
        if (fromIndex > arrayLastIndex || toIndex > arrayLastIndex) {
            throw new RuntimeException("From and/or to indexes can't be great than last index of array - " + arrayLastIndex + ", " +
                    "but now, from" + " - " + fromIndex + ", to - " + toIndex);
        }
        int[] result = new int[toIndex - fromIndex + 1];
        for (int i = fromIndex, j = 0; i <= toIndex; i++, j++) {
            result[j] = array[i];
        }
        return result;
    }

}

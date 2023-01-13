package com.jdev.algorithms.search;

import com.jdev.util.ArrayUtils;

public class LinearSearch {

    public static int linearSearch(int[] array, int elementToSearch) {
        final int length = array.length;
        for (int i = 0; i < length; i++) {
            if (array[i] == elementToSearch) {
                return i;
            }
        }
        return -1;
    }

    public static int[] linearSearchAll(int[] array, int elementToSearch) {
        final int length = array.length;
        int result[] = new int[length];
        int y = -1;
        for (int i = 0; i < length; i++) {
            if (array[i] == elementToSearch) {
                result[++y] = i;
            }
        }

        if (y == -1) {
            return new int[0];
        } else {
            int count = ArrayUtils.getCount(result, 0, true);
            result = ArrayUtils.sliceArray(result, 0, count - 1);
        }

        return result;
    }

}

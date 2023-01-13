package com.jdev.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilsTest {

    private int[] array = new int[]{10, 52, 96, 35, 0, 5, 8, 0, 0, 7, 0};

    private static Stream<Arguments> methodSourceFor_test_sliceArray_throwException() {
        return Stream.of(Arguments.of(-1, 10, "From and/or to indexes should be great or equal 0, but now, from - -1, to - 10"),
                Arguments.of(-1, -10, "From and/or to indexes should be great or equal 0, but now, from - -1, to - -10"),
                Arguments.of(1, -10, "From and/or to indexes should be great or equal 0, but now, from - 1, to - -10"));
    }

    private static Stream<Arguments> methodSourceFor_test_sliceArray_throwException_FromAndOrToCannotBeOutOfRange() {
        return Stream.of(Arguments.of(9, 11, "From and/or to indexes can't be great than last index of array - 10, " +
                        "but now, from - 9, to - 11"),
                Arguments.of(12, 13, "From and/or to indexes can't be great than last index of array - 10, but now, " +
                        "from - 12, to - 13"));
    }

    private static Stream<Arguments> methodSourceFor_test_sliceArray() {
        return Stream.of(Arguments.of(0, 3, new int[]{10, 52, 96, 35}),
                Arguments.of(2, 3, new int[]{96, 35}),
                Arguments.of(3, 3, new int[]{35}));
    }

    @Test
    void test_getCount() {
        int count = ArrayUtils.getCount(array, 0, false);
        assertEquals(4, count);
        count = ArrayUtils.getCount(array, 0, true);
        assertEquals(7, count);
    }

    @MethodSource("methodSourceFor_test_sliceArray")
    @ParameterizedTest
    void test_sliceArray(int from, int to, int [] expectedArray) {
        assertArrayEquals(expectedArray, ArrayUtils.sliceArray(array, from, to));
    }

    @MethodSource("methodSourceFor_test_sliceArray_throwException")
    @ParameterizedTest
    void test_sliceArray_throwException_FromOrAndToIsLessThan0(int from, int to, String expectedMessage) {
        String message = assertThrows(RuntimeException.class, () -> ArrayUtils.sliceArray(array, from, to)).getMessage();
        assertEquals(expectedMessage, message);
    }

    @Test
    void test_sliceArray_throwException_FromIsGreatIndexThanTo() {
        String message = assertThrows(RuntimeException.class, () -> ArrayUtils.sliceArray(array, 2, 1)).getMessage();
        assertEquals("From index should be great than to, from index is - 2, to index = 1", message);
    }

    @MethodSource("methodSourceFor_test_sliceArray_throwException_FromAndOrToCannotBeOutOfRange")
    @ParameterizedTest
    void test_sliceArray_throwException_FromAndOrToCannotBeOutOfRange(int from, int to, String expectedMessage) {
        String message = assertThrows(RuntimeException.class, () -> ArrayUtils.sliceArray(array, from, to)).getMessage();
        assertEquals(expectedMessage, message);
    }

}
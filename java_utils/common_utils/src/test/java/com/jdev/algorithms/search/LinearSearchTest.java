package com.jdev.algorithms.search;

import com.jdev.arrays.fill.FillType;
import com.jdev.arrays.fill.OrderedArray;
import com.jdev.arrays.fill.RandomArray;
import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinearSearchTest {

    private static Stream<Arguments> methodSourceFor_test_linearSearchAll() {
        return Stream.of(Arguments.of(25, 500, 0),
                Arguments.of(50, 5, 1));
    }

    @Test
    void test_linearSearch() {
        OrderedArray orderedArray = (OrderedArray) FillType.ORDERED.getArrayInterface();
        int[] array = orderedArray.createAndFillArray(10);
        orderedArray.printArray(array);

        int index = LinearSearch.linearSearch(array, 5);
        ConsoleUtils.printToConsole("5 - is in index - " + index);
        assertEquals(4, index);
    }

    @MethodSource("methodSourceFor_test_linearSearchAll")
    @ParameterizedTest
    void test_linearSearchAll(int lengthArray, int searchedElement, int countOfFoundIndexes) {
        RandomArray randomArray = (RandomArray) FillType.RANDOM.getArrayInterface();
        randomArray.setStartValue(1);
        randomArray.setEndValue(10);
        int[] array = randomArray.createAndFillArray(lengthArray);
        randomArray.printArray(array);

        int[] indexes = LinearSearch.linearSearchAll(array, searchedElement);
        ConsoleUtils.printToConsole(searchedElement + " - found, count is - " + indexes.length);
        assertTrue(indexes.length >= countOfFoundIndexes);
        for (int index : indexes) {
            assertEquals(searchedElement, array[index]);
        }
    }

}
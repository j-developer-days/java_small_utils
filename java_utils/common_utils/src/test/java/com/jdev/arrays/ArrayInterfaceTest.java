package com.jdev.arrays;

import com.jdev.arrays.fill.CustomFillArray;
import com.jdev.arrays.fill.FillType;
import com.jdev.arrays.fill.OrderedArray;
import com.jdev.arrays.fill.RandomArray;
import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayInterfaceTest {

    @Test
    void test_createAndFillArray_ORDERED() {
        ArrayInterface arrayInterface = FillType.ORDERED.getArrayInterface();
        assertTrue(arrayInterface instanceof OrderedArray);
        OrderedArray orderedArray = (OrderedArray) arrayInterface;

        int size = 25;
        int[] array = orderedArray.createAndFillArray(size);
        assertEquals(size, array.length);
        orderedArray.printArray(array);

        ConsoleUtils.printToConsole();
        ConsoleUtils.printDelimiter('-');
        ConsoleUtils.printToConsole();

        size += 50;//75
        orderedArray.setStartValue(150);
        array = orderedArray.createAndFillArray(size);
        assertEquals(size, array.length);
        orderedArray.printArray(array);

        ConsoleUtils.printToConsole("Summary");
        ConsoleUtils.printDelimiter('-');
        orderedArray.summary(size += 20);//95

        ConsoleUtils.printToConsole("Summary 2 ----------------");
        ConsoleUtils.printDelimiter('-');
        orderedArray.setStartValue(1_000);
        orderedArray.summary(size += 5);//100

        assertEquals(100, size);
    }

    @Test
    void test_createAndFillArray_RANDOM() {
        ArrayInterface arrayInterface = FillType.RANDOM.getArrayInterface();
        assertTrue(arrayInterface instanceof RandomArray);
        RandomArray randomArray = (RandomArray) arrayInterface;

        int size = 20;
        int[] array = randomArray.createAndFillArray(size);
        assertEquals(size, array.length);
        randomArray.printArray(array);

        int sum = ArrayInterface.sum(array);
        ConsoleUtils.printToConsole("sum = " + sum);
        assertTrue(sum > 30);

        double average = ArrayInterface.average(array);
        ConsoleUtils.printToConsole("average = " + average);
        assertTrue(average > 25);

        ConsoleUtils.printDelimiter("*-", 50);

        randomArray.summary(size += 1);
        assertEquals(21, size);
    }

    @Test
    void test_createAndFillArray_CUSTOM() {
        ArrayInterface arrayInterface = FillType.CUSTOM.getArrayInterface();
        assertTrue(arrayInterface instanceof CustomFillArray);
        CustomFillArray customFillArray = (CustomFillArray) arrayInterface;
        customFillArray.setStartValue(0);
        customFillArray.setFunction(integer -> integer + 11);

        customFillArray.summary(10);
    }

    @Test
    void test_createAndFillArray_CUSTOM_WithStartBefore() {
        ArrayInterface arrayInterface = FillType.CUSTOM.getArrayInterface();
        assertTrue(arrayInterface instanceof CustomFillArray);
        CustomFillArray customFillArray = (CustomFillArray) arrayInterface;
        customFillArray.setStartValue(8);
        customFillArray.setStartBefore(true);
        customFillArray.setFunction(integer -> integer + 5);

        customFillArray.summary(4);
    }

}
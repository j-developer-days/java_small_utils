package com.jdev.arrays;

import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayWorkingITest {

    @Test
    void test_createAndFillArray_ORDERED() {
        ArrayWorkingI arrayWorkingI = FillType.ORDERED.getArrayWorkingI();
        assertTrue(arrayWorkingI instanceof OrderedArray);
        OrderedArray orderedArray = (OrderedArray) arrayWorkingI;

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
        ArrayWorkingI arrayWorkingI = FillType.RANDOM.getArrayWorkingI();
        assertTrue(arrayWorkingI instanceof RandomArray);
        RandomArray randomArray = (RandomArray) arrayWorkingI;

        int size = 20;
        int[] array = randomArray.createAndFillArray(size);
        assertEquals(size, array.length);
        randomArray.printArray(array);

        int sum = ArrayWorkingI.sum(array);
        ConsoleUtils.printToConsole("sum = " + sum);
        assertTrue(sum > 30);

        double average = ArrayWorkingI.average(array);
        ConsoleUtils.printToConsole("average = " + average);
        assertTrue(average > 25);

        ConsoleUtils.printDelimiter('*', 50);

        randomArray.summary(size += 1);
        assertEquals(21, size);
    }

    @Test
    void test_createAndFillArray_CUSTOM() {
        ArrayWorkingI arrayWorkingI = FillType.CUSTOM.getArrayWorkingI();
        assertTrue(arrayWorkingI instanceof CustomFillArray);
        CustomFillArray customFillArray = (CustomFillArray) arrayWorkingI;
        customFillArray.setStartValue(0);
        customFillArray.setFunction(integer -> integer + 11);

        customFillArray.summary(10);
    }
}
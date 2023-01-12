package com.jdev.arrays;

import org.junit.jupiter.api.Test;

class ArrayFillFactoryTest {

    @Test
    void test_createAndFillArray() {
        ArrayWorkingI arrayWorking = FillType.RANDOM.getArrayWorkingI();
//        Assertions.assertTrue(arrayWorking instanceof OrderedArray);
//        OrderedArray orderedArray = (OrderedArray) arrayWorking;
//        CustomFillArray customFillArray = (CustomFillArray) arrayWorking;
//        customFillArray.setStartValue(10);
//        customFillArray.setFunction(integer -> integer + 11);

        final int arraySize = 10;
        int[] array = new int[arraySize];
        for (var i = 0; i < arraySize; i++) {
            array[i] = arrayWorking.fillArray();
        }

        ArrayWorkingI.printArray(array);

    }

}
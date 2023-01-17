package com.jdev.algorithms;

import com.jdev.console.ConsoleUtils;
import com.jdev.util.MapUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

class RecursiveAlgorithmsTest {

    @ValueSource(ints = {0, 1, 2, 5, 19, 25, 50, 99, 189})
    @ParameterizedTest
    void test_sumOfDigitsRecursive(int number) {
        ConsoleUtils.printToConsole(RecursiveAlgorithms.sumOfDigitsRecursive(number));
    }

    @Disabled
    @ValueSource(ints = {0, 1, 2, 5, 19, 25, 50, 99, 189})
    @ParameterizedTest
    void test_decimalToBinary(int number) {
        RecursiveAlgorithms.decimalToBinary(number);
        ConsoleUtils.printToConsole();
    }

    @Test
    void test_power() {
        List<MapUtils.PairForMap<Integer, Integer>> pairForMaps = new ArrayList<>();
        pairForMaps.add(MapUtils.PairForMap.of(10, 3));
        pairForMaps.add(MapUtils.PairForMap.of(2, 3));
        pairForMaps.add(MapUtils.PairForMap.of(5, 5));
        pairForMaps.add(MapUtils.PairForMap.of(10, 9));

        pairForMaps.forEach(pairForMap -> ConsoleUtils.printToConsole(RecursiveAlgorithms.power(pairForMap.getKey(), pairForMap.getValue())));

    }

}
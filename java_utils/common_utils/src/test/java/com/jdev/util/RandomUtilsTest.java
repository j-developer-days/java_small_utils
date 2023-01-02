package com.jdev.util;

import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class RandomUtilsTest {

    @Test
    void test_randomNumber() {
        for (var i = 100; i >= 0; i--) {
            ConsoleUtils.printToConsole("#" + i + " - " + RandomUtils.randomNumber());
        }
    }

    @Test
    void test_randomFromTo() {
        final int count = 1_000;
        Map<Integer, Integer> integerIntegerMap = new HashMap<>();
        for (var i = 0; i < count; i++) {
            int randomNumber = RandomUtils.randomFromTo(0, 5);
            integerIntegerMap.compute(randomNumber,
                    (key, value) -> {
                        if (value == null) {
                            return 1;
                        } else {
                            return value + 1;
                        }
                    });
        }
        Assertions.assertTrue(integerIntegerMap.size() <= 6);
        Assertions.assertEquals(Set.of(0, 1, 2, 3, 4, 5), integerIntegerMap.keySet());
        integerIntegerMap.forEach((integer, integer2) -> ConsoleUtils.printToConsole(integer + " = " + integer2));
        Assertions.assertEquals(count,
                integerIntegerMap.values().stream().reduce(Integer::sum).get());
    }

}
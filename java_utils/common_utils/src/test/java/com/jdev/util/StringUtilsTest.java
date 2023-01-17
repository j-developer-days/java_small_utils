package com.jdev.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    private static Stream<Arguments> methodSourceFor_test_multipleCharByCount_withString() {
        return Stream.of(Arguments.of("|-|", 50, 150, 2, Map.of("-", 50, "|", 100)),
                Arguments.of("*+/-", 10, 40, 4, Map.of("-", 10, "/", 10, "+", 10, "*", 10)));
    }

    @Test
    void test_multipleCharByCount() {
        String s = StringUtils.multipleCharByCount('*', 50);
        assertEquals(50, s.length());

        Map<String, Integer> mapCount = new HashMap<>();
        for (var i = 0; i < s.length(); i++) {
            mapCount.computeIfPresent(Character.toString(s.charAt(i)), (s1, integer) -> integer + 1);
            mapCount.putIfAbsent(Character.toString(s.charAt(i)), 1);
        }
        assertEquals(1, mapCount.size());
        assertEquals(Map.of("*", 50), mapCount);
    }

    @Test
    void test_multipleCharByCount_CountIsMinus50() {
        String s = StringUtils.multipleCharByCount('*', -50);
        assertEquals(150, s.length());

        Map<String, Integer> mapCount = new HashMap<>();
        for (var i = 0; i < s.length(); i++) {
            mapCount.computeIfPresent(Character.toString(s.charAt(i)), (s1, integer) -> integer + 1);
            mapCount.putIfAbsent(Character.toString(s.charAt(i)), 1);
        }
        assertEquals(1, mapCount.size());
        assertEquals(Map.of("*", 150), mapCount);
    }

    @MethodSource("methodSourceFor_test_multipleCharByCount_withString")
    @ParameterizedTest
    void test_multipleStringByCount_withString(String text, int count, int expectedLength, int expectedMapSize,
                                             Map<String, Integer> expectedMap) {
        String s = StringUtils.multipleStringByCount(text, count);
        assertEquals(expectedLength, s.length());

        Map<String, Integer> mapCount = new HashMap<>();
        for (var i = 0; i < s.length(); i++) {
            mapCount.computeIfPresent(Character.toString(s.charAt(i)), (s1, integer) -> integer + 1);
            mapCount.putIfAbsent(Character.toString(s.charAt(i)), 1);
        }
        assertEquals(expectedMapSize, mapCount.size());
        assertEquals(expectedMap, mapCount);
    }

}
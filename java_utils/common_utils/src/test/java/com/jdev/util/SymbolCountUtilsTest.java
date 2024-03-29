package com.jdev.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SymbolCountUtilsTest {

    private static Stream<Arguments> methodSource_test_checkLetterCount() {
        return Stream.of(
                Arguments.of("KDIHjf", true, true, MapUtils.createAndFillMap(new MapUtils.PairForMap[]{
                        new MapUtils.PairForMap('K', 1), new MapUtils.PairForMap('D', 1),
                        new MapUtils.PairForMap('I', 1), new MapUtils.PairForMap('H', 1),
                        new MapUtils.PairForMap('j', 1), new MapUtils.PairForMap('f', 1)
                })),
                Arguments.of("KDfKIHjfIIfIKf", true, true, MapUtils.createAndFillMap(new MapUtils.PairForMap[]{
                        new MapUtils.PairForMap('K', 3), new MapUtils.PairForMap('D', 1),
                        new MapUtils.PairForMap('I', 4), new MapUtils.PairForMap('H', 1),
                        new MapUtils.PairForMap('j', 1), new MapUtils.PairForMap('f', 4)
                })),
                Arguments.of("55#G%g*5GJhgG55", true, true, MapUtils.createAndFillMap(new MapUtils.PairForMap[]{
                        new MapUtils.PairForMap('5', 5), new MapUtils.PairForMap('#', 1),
                        new MapUtils.PairForMap('G', 3), new MapUtils.PairForMap('%', 1),
                        new MapUtils.PairForMap('g', 2), new MapUtils.PairForMap('*', 1),
                        new MapUtils.PairForMap('J', 1), new MapUtils.PairForMap('h', 1)
                })),
                Arguments.of("55#G%g*5GJhgG55", false, true, MapUtils.createAndFillMap(new MapUtils.PairForMap[]{
                        new MapUtils.PairForMap('5', 5), new MapUtils.PairForMap('#', 1),
                        new MapUtils.PairForMap('g', 5), new MapUtils.PairForMap('%', 1),
                        new MapUtils.PairForMap('*', 1),
                        new MapUtils.PairForMap('j', 1), new MapUtils.PairForMap('h', 1)
                }))
        );
    }

    private static Stream<Arguments> methodSource_test_getOnlyWhichConditionPassSymbolCount() {
        final String sourceText = "John_Jonson__02.09.1957_m";
        return Stream.of(
                Arguments.of(sourceText, true, 3, SymbolCountUtils.Sign.EQ,
                        MapUtils.createAndFillMap(new MapUtils.PairForMap[]{
                                MapUtils.PairForMap.of('o', 3),
                                MapUtils.PairForMap.of('n', 3)
                        })),
                Arguments.of(sourceText, true, 1, SymbolCountUtils.Sign.NEQ,
                        MapUtils.createAndFillMap(new MapUtils.PairForMap[]{
                                MapUtils.PairForMap.of('J', 2),
                                MapUtils.PairForMap.of('n', 3), MapUtils.PairForMap.of('.', 2),
                                MapUtils.PairForMap.of('o', 3), MapUtils.PairForMap.of('0', 2),
                                MapUtils.PairForMap.of('9', 2), MapUtils.PairForMap.of('_', 4)
                        })),
                Arguments.of(sourceText, true, 3, SymbolCountUtils.Sign.GRE,
                        MapUtils.createAndFillMap(new MapUtils.PairForMap[]{
                                MapUtils.PairForMap.of('o', 3),
                                MapUtils.PairForMap.of('n', 3),
                                MapUtils.PairForMap.of('_', 4)
                        })),
                Arguments.of(sourceText, true, 3, SymbolCountUtils.Sign.GR,
                        MapUtils.createAndFillMap(new MapUtils.PairForMap[]{
                                MapUtils.PairForMap.of('_', 4)
                        })),
                Arguments.of(sourceText, true, 2, SymbolCountUtils.Sign.LSE,
                        MapUtils.createAndFillMap(new MapUtils.PairForMap[]{
                                MapUtils.PairForMap.of('s', 1), MapUtils.PairForMap.of('1', 1),
                                MapUtils.PairForMap.of('2', 1), MapUtils.PairForMap.of('5', 1),
                                MapUtils.PairForMap.of('J', 2), MapUtils.PairForMap.of('.', 2),
                                MapUtils.PairForMap.of('h', 1), MapUtils.PairForMap.of('m', 1),
                                MapUtils.PairForMap.of('0', 2),
                                MapUtils.PairForMap.of('9', 2), MapUtils.PairForMap.of('7', 1)
                        })),
                Arguments.of(sourceText, true, 2, SymbolCountUtils.Sign.LS,
                        MapUtils.createAndFillMap(new MapUtils.PairForMap[]{
                                MapUtils.PairForMap.of('h', 1), MapUtils.PairForMap.of('1', 1),
                                MapUtils.PairForMap.of('2', 1), MapUtils.PairForMap.of('5', 1),
                                MapUtils.PairForMap.of('m', 1), MapUtils.PairForMap.of('s', 1),
                                MapUtils.PairForMap.of('7', 1)
                        }))
        );
    }

    @ParameterizedTest
    @MethodSource("methodSource_test_checkLetterCount")
    void test_checkSymbolCount(String str, boolean isCaseSensitive, boolean needToPrintResult, Map<Character, Integer> expected) {
        assertEquals(expected, SymbolCountUtils.checkSymbolCount(str, isCaseSensitive, needToPrintResult));
    }

    @ParameterizedTest
    @MethodSource("methodSource_test_getOnlyWhichConditionPassSymbolCount")
    void test_getOnlyWhichConditionPassSymbolCountSymbolCount(String sourceText, boolean isCaseSensitive,
                                        int maxCount, SymbolCountUtils.Sign sign,
                                        Map<Character, Integer> expected) {
        Map<Character, Integer> onlyWhichConditionPass =
                SymbolCountUtils.getOnlyWhichConditionPassSymbolCount(sourceText, isCaseSensitive, true, maxCount, sign);

        assertEquals(expected, onlyWhichConditionPass);
    }

}
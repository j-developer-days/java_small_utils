package com.jdev.algorithms;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckBracketsTest {

    @CsvSource({"{", "{]", "[]{}(", "[)", "[}", "((", "pP", "("})
    @ParameterizedTest
    void test_checkBrackets_falseResult(String input) {
        assertFalse(CheckBrackets.checkBracketWithStack(input));
        assertFalse(CheckBrackets.checkBracket(input));
    }

    @CsvSource({"{}", "{}[]", "[]{}()"})
    @ParameterizedTest
    void test_checkBrackets_trueResult(String input) {
        assertTrue(CheckBrackets.checkBracketWithStack(input));
        assertTrue(CheckBrackets.checkBracket(input));
    }

}
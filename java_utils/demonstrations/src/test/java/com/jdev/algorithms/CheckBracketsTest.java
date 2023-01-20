package com.jdev.algorithms;

import com.jdev.console.ConsoleUtils;
import com.jdev.tester.MeasureWorkingTime;
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
        MeasureWorkingTime measureWorkingTime = new MeasureWorkingTime();

        measureWorkingTime.beginNanoSeconds();
        boolean b = CheckBrackets.checkBracketWithStack(input);
        measureWorkingTime.endNanoSeconds();
        ConsoleUtils.printToConsole("checkBracketWithStack - " + measureWorkingTime.differenceNanoSeconds());

        assertTrue(b);

        measureWorkingTime.beginNanoSeconds();
        b = CheckBrackets.checkBracket(input);
        measureWorkingTime.endNanoSeconds();
        ConsoleUtils.printToConsole("checkBracket - " + measureWorkingTime.differenceNanoSeconds());
        assertTrue(b);
    }

}
package com.jdev.algorithms;

import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FactorialTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 5, 10, 15})
    void test_factorial(int number) {
        ConsoleUtils.printToConsole(Factorial.recursive(number));
    }

    @ParameterizedTest
    @ValueSource(longs = {19, 20})
    void test_factorial(long number) {
        ConsoleUtils.printToConsole(Factorial.recursive(number));//2432902008176640000
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 5, 10, 15})
    void test_loop(int number) {
        ConsoleUtils.printToConsole(Factorial.loop(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 5, 10, 15})
    void test_loopWhile(int number) {
        ConsoleUtils.printToConsole(Factorial.loopWhile(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 5, 10, 15})
    void test_loopDoWhile(int number) {
        ConsoleUtils.printToConsole(Factorial.loopDoWhile(number));
    }

}
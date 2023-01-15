package com.jdev.algorithms;

import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

class FibonacciTest {

    @Test
    void test_iterative() {
        List<Integer> iterative = Fibonacci.iterative(10);
        iterative.forEach(ConsoleUtils::printToConsole);
    }

    @Test
    void test_recursive() {
        ConsoleUtils.printToConsole(Fibonacci.recursive(10));
    }

    @Test
    void test_dynamic() {
        ConsoleUtils.printToConsole(Fibonacci.dynamic(10));
    }

}
package com.jdev.algorithms;

import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {

    @Test
    void test_factorial(){
        ConsoleUtils.printToConsole(Factorial.recursive(0));
        ConsoleUtils.printToConsole(Factorial.recursive(1));
        ConsoleUtils.printToConsole(Factorial.recursive(2));
        ConsoleUtils.printToConsole(Factorial.recursive(5));
        ConsoleUtils.printToConsole(Factorial.recursive(10));
        ConsoleUtils.printToConsole(Factorial.recursive(20));
        ConsoleUtils.printToConsole(Factorial.recursive(20L));
    }

    @Test
    void test_loop(){
        ConsoleUtils.printToConsole(Factorial.loop(0));
        ConsoleUtils.printToConsole(Factorial.loop(1));
        ConsoleUtils.printToConsole(Factorial.loop(2));
        ConsoleUtils.printToConsole(Factorial.loop(5));
        ConsoleUtils.printToConsole(Factorial.loop(10));
        ConsoleUtils.printToConsole(Factorial.loop(20));
    }

}
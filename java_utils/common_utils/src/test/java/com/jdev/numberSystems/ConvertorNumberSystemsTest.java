package com.jdev.numberSystems;

import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class ConvertorNumberSystemsTest {

    @Test
    void test_convertFromBinaryToDecimal() {
        int i = ConvertorNumberSystems.convertFromBinaryToDecimal("101010");
        ConsoleUtils.printToConsole(i);
        Assertions.assertEquals(42, i);
    }

    @Test
    void test_convertorFromToBinary() {
        Stream.iterate(1, integer -> integer + 1).limit(100).forEach(integer -> {
            String convertedFromDecimalToBinary = ConvertorNumberSystems.convertFromDecimalToBinary(integer);
            int i = ConvertorNumberSystems.convertFromBinaryToDecimal(convertedFromDecimalToBinary);
            ConsoleUtils.printToConsole(integer + " - " + convertedFromDecimalToBinary);
            Assertions.assertEquals(integer, i);
        });
    }

    @Test
    void test_convertorFromToOctal() {
        Stream.iterate(1, integer -> integer + 1).limit(100).forEach(integer -> {
            String convertedFromDecimalToOctal = ConvertorNumberSystems.convertFromDecimalToOctal(integer);
            int i = ConvertorNumberSystems.convertFromOctalToDecimal(convertedFromDecimalToOctal);
            ConsoleUtils.printToConsole(integer + " - " + convertedFromDecimalToOctal);
            Assertions.assertEquals(integer, i);
        });
    }

    @Test
    void test_convertorFromToHex() {
        Stream.iterate(1, integer -> integer + 1).limit(100).forEach(integer -> {
            String convertedFromDecimalToHex = ConvertorNumberSystems.convertFromDecimalToHex(integer);
            int i = ConvertorNumberSystems.convertFromHexToDecimal(convertedFromDecimalToHex);
            ConsoleUtils.printToConsole(integer + " - " + convertedFromDecimalToHex);
            Assertions.assertEquals(integer, i);
        });
    }

}
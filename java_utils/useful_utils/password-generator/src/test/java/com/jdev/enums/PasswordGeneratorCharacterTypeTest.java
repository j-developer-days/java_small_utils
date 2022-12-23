package com.jdev.enums;

import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordGeneratorCharacterTypeTest {

    @Test
    void test_generateShortDescription() {
        final String actual = PasswordGeneratorCharacterType.generateShortDescription();
        ConsoleUtils.printToConsole(actual);
        assertEquals(new StringBuilder()
                        .append("\t\t").append("n").append(" - ").append("numbers").append("\n")
                        .append("\t\t").append("eng").append(" - ").append("all english letters(lower+upper case)").append("\n")
                        .append("\t\t").append("eng_l").append(" - ").append("english letters lower case").append("\n")
                        .append("\t\t").append("eng_u").append(" - ").append("english letters upper case").append("\n")
                        .append("\t\t").append("sp").append(" - ").append("special characters").append("\n")
                        .toString(),
                actual);
    }

}
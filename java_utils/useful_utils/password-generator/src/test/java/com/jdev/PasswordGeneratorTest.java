package com.jdev;

import com.jdev.console.ConsoleUtils;
import com.jdev.enums.PasswordGeneratorCharacterType;
import com.jdev.enums.PasswordGeneratorLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordGeneratorTest {

    @Test
    void test_generateRandomPassword_throwException() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String message = assertThrows(RuntimeException.class, passwordGenerator::generateRandomPassword).getMessage();
        assertEquals("We need to add characters!", message);
    }

    @Test
    void test_generateRandomPassword_throwException_withoutCharacters() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        passwordGenerator.setSize(10);
        String message = assertThrows(RuntimeException.class, passwordGenerator::generateRandomPassword).getMessage();
        assertEquals("We need to add characters!", message);
    }

    @Test
    void test_generateRandomPassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        final int size = 10;
        passwordGenerator.setSize(size);
        passwordGenerator.getCharacters().addAll(PasswordGeneratorCharacterType.NUMBERS.getCharacters());

        String password = passwordGenerator.generateRandomPassword();
        ConsoleUtils.printToConsole(password);
        assertEquals(size, password.length());
    }

    @EnumSource(value = PasswordGeneratorLevel.class, mode = EnumSource.Mode.EXCLUDE, names = {"CUSTOM"})
    @ParameterizedTest
    void test_generateRandomPassword_ExcludeCustom(PasswordGeneratorLevel passwordGeneratorLevel) {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        passwordGenerator.setPasswordGeneratorLevel(passwordGeneratorLevel);

        String password = passwordGenerator.generateRandomPassword();
        ConsoleUtils.printToConsole(password);
        assertEquals(passwordGeneratorLevel.getSize(), password.length());
    }


}
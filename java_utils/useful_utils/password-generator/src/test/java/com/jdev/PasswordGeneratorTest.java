package com.jdev;

import com.jdev.console.ConsoleUtils;
import com.jdev.enums.PasswordGeneratorCharacterType;
import com.jdev.enums.PasswordGeneratorLevel;
import com.jdev.util.StringUtils;
import com.jdev.util.SymbolCountUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {

    @Test
    void test_generateRandomPassword_throwException() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String message = assertThrows(RuntimeException.class, passwordGenerator::generateRandomPassword).getMessage();
        assertEquals("You need to add characters!", message);
    }

    @Test
    void test_generateRandomPassword_throwException_withoutCharacters() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        passwordGenerator.setSize(10);
        String message = assertThrows(RuntimeException.class, passwordGenerator::generateRandomPassword).getMessage();
        assertEquals("You need to add characters!", message);
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

    @Test
    void test_generateRandomPassword_throwException_generatePasswordUniqueSymbols_withoutSpecifySize() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        passwordGenerator.setGeneratePasswordUniqueSymbols(true);
        String message = assertThrows(RuntimeException.class, passwordGenerator::generateRandomPassword).getMessage();
        assertEquals("You need to specify size!", message);
    }

    @Test
    void test_generateRandomPassword_throwException_generatePasswordUniqueSymbols_sizeMoreThanCountOfAccessibleSymbols() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        passwordGenerator.setGeneratePasswordUniqueSymbols(true);
        passwordGenerator.setSize(11);
        passwordGenerator.getCharacters().addAll(PasswordGeneratorCharacterType.NUMBERS.getCharacters());
        String message = assertThrows(RuntimeException.class, passwordGenerator::generateRandomPassword).getMessage();
        assertEquals("You set size - 11, but max you can - 10!", message);
    }

    @Test
    void test_generateRandomPassword_throwException_generatePasswordUniqueSymbols_WithPasswordGeneratorLevel() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        passwordGenerator.setGeneratePasswordUniqueSymbols(true);
        passwordGenerator.setPasswordGeneratorLevel(PasswordGeneratorLevel.HARD_LEVEL1);

        String message = assertThrows(RuntimeException.class, passwordGenerator::generateRandomPassword).getMessage();
        assertEquals("You set size - 75, but max you can - 62!", message);
    }

    @RepeatedTest(5)
    void test_generateRandomPassword_generatePasswordUniqueSymbols_customSize() {
        final int size = 26;
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        passwordGenerator.setGeneratePasswordUniqueSymbols(true);
        passwordGenerator.setSize(size);
        passwordGenerator.getCharacters().addAll(PasswordGeneratorCharacterType.ENGLISH_LETTERS_LOWER_CASE_ONLY.getCharacters());

        String password = passwordGenerator.generateRandomPassword();
        ConsoleUtils.printToConsole(password);

        assertNotNull(password);
        assertEquals(size, password.length());
        Map<Character, Integer> characterIntegerMap = SymbolCountUtils.checkSymbolCount(password, true);
        assertEquals(size, characterIntegerMap.size());
        characterIntegerMap.forEach((character, integer) -> {
            ConsoleUtils.printToConsole(character + StringUtils.TAB + integer);
            assertEquals(1, integer);
        });
    }

    @Test
    void test_generateRandomPassword_generatePasswordUniqueSymbols_WithCustomSizeAndPasswordGeneratorLevel() {
        final int size = 26;
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        passwordGenerator.setGeneratePasswordUniqueSymbols(true);
        passwordGenerator.setPasswordGeneratorLevel(PasswordGeneratorLevel.HARD_LEVEL1);
        passwordGenerator.setSize(size);

        String password = passwordGenerator.generateRandomPassword();
        ConsoleUtils.printToConsole(password);

        assertNotNull(password);
        assertEquals(size, password.length());
        Map<Character, Integer> characterIntegerMap = SymbolCountUtils.checkSymbolCount(password, true);
        assertEquals(size, characterIntegerMap.size());
        characterIntegerMap.forEach((character, integer) -> {
            ConsoleUtils.printToConsole(character + StringUtils.TAB + integer);
            assertEquals(1, integer);
        });
    }

    @Test
    void test_generateRandomPassword_UUIDType() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        passwordGenerator.setPasswordGeneratorLevel(PasswordGeneratorLevel.UUID);

        String password = passwordGenerator.generateRandomPassword();
        ConsoleUtils.printToConsole(password);
        assertEquals(36, password.length());
        int countOfDelimiters = 0;
        for (int i = password.length() - 1; i >= 0; i--) {
            char c = password.charAt(i);
            if (Character.isAlphabetic(c)) {
                assertTrue(Character.isLowerCase(c));
            }
            if(!Character.isLetterOrDigit(c) && passwordGenerator.getCharacters().contains(c)){
                countOfDelimiters++;
            }
        }
        assertEquals(4, countOfDelimiters);
    }

    @Test
    void test_generateRandomPassword_UUIDTypeUuidTurnOnAndRandomlyChangeLowerToUpperCase() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        passwordGenerator.setPasswordGeneratorLevel(PasswordGeneratorLevel.UUID);
        passwordGenerator.setUuidTurnOnAndRandomlyChangeLowerToUpperCase(true);

        String password = passwordGenerator.generateRandomPassword();
        ConsoleUtils.printToConsole(password);
        assertEquals(36, password.length());
        int count = 0;
        for (int i = password.length() - 1; i >= 0; i--) {
            char c = password.charAt(i);
            if (Character.isAlphabetic(c) && Character.isUpperCase(c)) {
                count++;
            }
        }
        ConsoleUtils.printToConsole(count);
        assertTrue(count > 0);
    }

}
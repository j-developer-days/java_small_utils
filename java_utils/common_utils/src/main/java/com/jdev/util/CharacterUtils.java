package com.jdev.util;

import java.util.ArrayList;
import java.util.List;

public class CharacterUtils {

    private static List<Character> specialCharacters;
    private static List<Character> numbers;
    private static List<Character> englishAlphabetLower;
    private static List<Character> englishAlphabetUpper;

    public static List<Character> getListEnglishAlphabet() {
        List<Character> characters = getListEnglishAlphabetUpper();
        characters.addAll(getListEnglishAlphabetLower());
        return characters;
    }

    public static List<Character> getListEnglishAlphabetUpper() {
        if (englishAlphabetUpper == null) {
            englishAlphabetUpper = getCharactersFromTo(65, 90);
        }
        return englishAlphabetUpper;
    }

    public static List<Character> getListEnglishAlphabetLower() {
        if (englishAlphabetLower == null) {
            englishAlphabetLower = getCharactersFromTo(97, 122);
        }
        return englishAlphabetLower;
    }

    public static List<Character> getListNumbers() {
        if (numbers == null) {
            numbers = getCharactersFromTo(48, 57);
        }
        return numbers;
    }

    public static List<Character> getListSpecialCharacters() {
        if (specialCharacters == null) {
            specialCharacters = new ArrayList<>(25);
            specialCharacters.addAll(getCharactersFromTo(33));
            specialCharacters.addAll(getCharactersFromTo(35, 38));
            specialCharacters.addAll(getCharactersFromTo(42, 43));
            specialCharacters.addAll(getCharactersFromTo(45, 47));
            specialCharacters.addAll(getCharactersFromTo(58, 64));
            specialCharacters.addAll(getCharactersFromTo(91, 95));
            specialCharacters.addAll(getCharactersFromTo(124));
            specialCharacters.addAll(getCharactersFromTo(126));
        }
        return specialCharacters;
    }

    private static List<Character> getCharactersFromTo(int fromTo) {
        return getCharactersFromTo(fromTo, fromTo);
    }

    private static List<Character> getCharactersFromTo(int from, int to) {
        List<Character> characters = new ArrayList<>(to - from + (from == to ? 1 : 0));
        for (int i = from, count = 1; i <= to; i++, count++) {
            characters.add((char) i);
        }
        return characters;
    }

}

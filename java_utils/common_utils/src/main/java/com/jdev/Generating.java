package com.jdev;

import com.jdev.console.ConsoleUtils;

import java.util.ArrayList;
import java.util.List;

public class Generating {

    public static void main(String[] args) {
        getCharactersFromTo(65, 90).forEach(ConsoleUtils::printToConsole);
        System.out.println(getCharactersFromTo(65, 90).size());
    }

    public static List<Character> getUpperCaseEnglishLetters() {
        return getCharactersFromTo(65, 90);
    }

    private static List<Character> getCharactersFromTo(int from, int to) {
        List<Character> characters = new ArrayList<>();
        for (var i = from; i <= to; i++) {
//            ConsoleUtils.printToConsole(i + " = " + ((char)i));
            characters.add((char) i);
        }
        return characters;
    }

}

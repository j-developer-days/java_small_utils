package com.jdev.util;

import com.jdev.console.ConsoleUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LetterCountUtils {

    public static Map<Character, Integer> checkLetterCount(String str, boolean isCaseSensitive, boolean needToPrintResult) {
        Map<Character, Integer> characterCount = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            Character ch = str.charAt(i);
            if (!isCaseSensitive) {
                ch = Character.toLowerCase(ch);
            }
            if (characterCount.get(ch) == null) {
                characterCount.put(ch, 1);
            } else {
                characterCount.put(ch, characterCount.get(ch) + 1);
            }
        }

        if (needToPrintResult) {
            characterCount.forEach((character, integer) -> ConsoleUtils.printToConsole(character + " = " + integer));
            ConsoleUtils.printToConsole("----------------------------");
            ConsoleUtils.printToConsole("count - " + characterCount.size() + "\n");

            ConsoleUtils.printToConsole("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        }
        return characterCount;
    }

    public static Map<Character, Integer> getOnlyWhichConditionPass(Map<Character, Integer> characterCount, int count, Sign sign) {
        Set<Character> keys = characterCount.keySet();
        Map<Character, Integer> result = new HashMap<>();
        for (Character currentChar : keys) {
            Integer integer = characterCount.get(currentChar);
            switch (sign) {
                case EQ: {
                    if (characterCount.get(currentChar) == count) {
                        result.put(currentChar, integer);
                    }
                }
                break;
                case NEQ: {
                    if (characterCount.get(currentChar) != count) {
                        result.put(currentChar, integer);
                    }
                }
                break;
                case GR: {
                    if (characterCount.get(currentChar) > count) {
                        result.put(currentChar, integer);
                    }
                }
                break;
                case LS: {
                    if (characterCount.get(currentChar) < count) {
                        result.put(currentChar, integer);
                    }
                }
                break;
                case GRE: {
                    if (characterCount.get(currentChar) >= count) {
                        result.put(currentChar, integer);
                    }
                }
                break;
                case LSE: {
                    if (characterCount.get(currentChar) <= count) {
                        result.put(currentChar, integer);
                    }
                }
                break;
            }
        }
        return result;
    }

    public enum Sign {
        EQ,
        NEQ,
        GR,
        LS,
        GRE,
        LSE
    }

}
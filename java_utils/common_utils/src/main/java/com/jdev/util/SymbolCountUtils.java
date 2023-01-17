package com.jdev.util;

import com.jdev.console.ConsoleUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SymbolCountUtils {

    public static Map<Character, Integer> checkSymbolCount(String str, boolean isCaseSensitive) {
        return checkSymbolCount(str, isCaseSensitive, false);
    }

    public static Map<Character, Integer> checkSymbolCount(String str, boolean isCaseSensitive, boolean needToPrintResult) {
        Map<Character, Integer> characterCount = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!isCaseSensitive) {
                ch = Character.toLowerCase(ch);
            }

            characterCount.computeIfPresent(ch, (character, integer) -> integer + 1);
            characterCount.putIfAbsent(ch, 1);
        }

        if (needToPrintResult) {
            characterCount.forEach((character, integer) -> ConsoleUtils.printToConsole(character + " = " + integer));
            ConsoleUtils.printDelimiter('-', 50);
            ConsoleUtils.printToConsole("count - " + characterCount.size() + "\n");

            ConsoleUtils.printDelimiterWithString("*-", 50);
        }
        return characterCount;
    }

    public static Map<Character, Integer> getOnlyWhichConditionPassSymbolCount(String str, boolean isCaseSensitive,
                                                                               boolean needToPrintResult, int count, Sign sign) {
        Map<Character, Integer> characterCount = checkSymbolCount(str, isCaseSensitive, needToPrintResult);
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

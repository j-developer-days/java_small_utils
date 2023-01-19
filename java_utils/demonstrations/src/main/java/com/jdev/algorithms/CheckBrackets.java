package com.jdev.algorithms;

import java.util.Stack;

public class CheckBrackets {

    public static boolean checkBracketWithStack(String input) {
        int lastIndex = input.length() - 1;
        Stack<Character> characters = new Stack<>();
        for (int i = 0; i <= lastIndex; i++) {
            char c = input.charAt(i);
            if (c == ')') {
                if (characters.peek() == '(') {
                    characters.pop();
                    continue;
                } else {
                    return false;
                }
            } else if (c == ']') {
                if (characters.peek() == '[') {
                    characters.pop();
                    continue;
                } else {
                    return false;
                }
            }
            if (c == '}') {
                if (characters.peek() == '{') {
                    characters.pop();
                    continue;
                } else {
                    return false;
                }
            } else {
                characters.push(c);
            }
        }
        return characters.isEmpty();
    }

    public static boolean checkBracket(String input) {
        final int inputLength = input.length();
        if (inputLength % 2 == 1) {
            return false;
        }
        int lastIndex = inputLength - 1;
        for (int i = 0; i <= lastIndex; i += 2) {
            final char c1 = input.charAt(i);
            char c2;
            final int nextIndex = i + 1;
            if (nextIndex <= lastIndex) {
                c2 = input.charAt(nextIndex);
                if (c1 == c2) {
                    return false;
                } else {
                    if (Character.isMirrored(c1) && Character.isMirrored(c2)) {
                        if ((c1 == '{' && c2 == '}') || (c1 == '(' && c2 == ')') || c1 == '[' && c2 == ']') {
                            continue;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}

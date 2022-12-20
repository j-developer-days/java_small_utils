package com.jdev.numberSystems;

import com.jdev.Generating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertorNumberSystems {

    private static final Map<String, Integer> HEX_TO_DECIMAL = new HashMap<>(6);
    private static final Map<Integer, String> DECIMAL_TO_HEX = new HashMap<>(6);

    private static final Map<String, Integer> M26_TO_DECIMAL = new HashMap<>(16);
    private static final Map<Integer, String> DECIMAL_TO_M26 = new HashMap<>(16);

    static {
        List<Character> upperCaseEnglishLetters = Generating.getUpperCaseEnglishLetters();
        for (int i = 0; i < 16; i++) {
            M26_TO_DECIMAL.put(upperCaseEnglishLetters.get(i).toString(), i + 10);
            DECIMAL_TO_M26.put(i + 10, upperCaseEnglishLetters.get(i).toString());
            if (i < 6) {
                HEX_TO_DECIMAL.put(upperCaseEnglishLetters.get(i).toString(), i + 10);
                DECIMAL_TO_HEX.put(i + 10, upperCaseEnglishLetters.get(i).toString());
            }
        }
    }

    public static int convertFromM26ToDecimal(String input) {
        return convertFromByTypeToDecimal(input, 26);
    }

    public static int convertFromHexToDecimal(String input) {
        return convertFromByTypeToDecimal(input, 16);
    }

    /**
     * binary to decimal
     * 100 = 4
     * 111 = 7
     */
    public static int convertFromBinaryToDecimal(String input) {
        return convertFromByTypeToDecimal(input, 2);
    }

    /**
     * octal to decimal
     * 144 = 100
     * 24 = 20
     */
    public static int convertFromOctalToDecimal(String input) {
        return convertFromByTypeToDecimal(input, 8);
    }

    private static int convertFromByTypeToDecimal(String input, int type) {
        if (type == 2 || type == 8 || type == 16 || type == 26) {
            final char[] chars = input.toCharArray();
            int result = 0;

            for (int i = chars.length - 1, y = 0; i >= 0; i--) {
                char aChar = chars[i];
                if (Character.isDigit(aChar) && (type == 2 || type == 8)) {
                    result = result + (int) Math.pow(type, y++) * Character.getNumericValue(aChar);
                } else if (type == 16) {
                    result = result + (int) Math.pow(type, y++) * (Character.isDigit(aChar) ?
                            Character.getNumericValue(aChar) :
                            HEX_TO_DECIMAL.get(Character.toString(Character.toUpperCase(aChar))));
                } else if (type == 26) {
                    result = result + (int) Math.pow(type, y++) * (Character.isDigit(aChar) ?
                            Character.getNumericValue(aChar) :
                            M26_TO_DECIMAL.get(Character.toString(Character.toUpperCase(aChar))));
                } else {
                    throw new RuntimeException("Not a number - " + aChar);
                }
            }
            return result;
        }
        throw new RuntimeException("Type is not right - " + type);
    }

    public static String convertFromDecimalToBinary(int input) {
        return convertFromDecimalToByType(input, 2);
    }

    public static String convertFromDecimalToOctal(int input) {
        return convertFromDecimalToByType(input, 8);
    }

    public static String convertFromDecimalToHex(int input) {
        return convertFromDecimalToByType(input, 16);
    }

    public static String convertFromDecimalToM26(int input) {
        return convertFromDecimalToByType(input, 26);
    }

    private static String convertFromDecimalToByType(int input, int type) {
        if (type == 2 || type == 8 || type == 16 || type == 26) {
            StringBuilder result = new StringBuilder();

            int divide = input;
            do {
                var quotient = divide / type;
                if (type == 16) {
                    int i = divide % type;
                    if (i >= 0 && i <= 9) {
                        result.append(i);
                    } else {
                        result.append(DECIMAL_TO_HEX.get(i));
                    }
                } else if (type == 26) {
                    int i = divide % type;
                    if (i >= 0 && i <= 9) {
                        result.append(i);
                    } else {
                        result.append(DECIMAL_TO_M26.get(i));
                    }
                } else {
                    result.append(divide % type);
                }
                divide = quotient;
            } while (divide != 0);

            return result.reverse().toString();
        }
        throw new RuntimeException("Type is not right - " + type);
    }

}

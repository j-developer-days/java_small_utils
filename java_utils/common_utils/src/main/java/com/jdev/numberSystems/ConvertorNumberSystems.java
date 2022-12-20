package com.jdev.numberSystems;

import java.util.HashMap;
import java.util.Map;

public class ConvertorNumberSystems {

    private static final Map<String, Integer> HEX_TO_DECIMAL = new HashMap<>(6);
    private static final Map<Integer, String> DECIMAL_TO_HEX = new HashMap<>(6);

    static {
        HEX_TO_DECIMAL.put("A", 10);
        HEX_TO_DECIMAL.put("B", 11);
        HEX_TO_DECIMAL.put("C", 12);
        HEX_TO_DECIMAL.put("D", 13);
        HEX_TO_DECIMAL.put("E", 14);
        HEX_TO_DECIMAL.put("F", 15);

        DECIMAL_TO_HEX.put(10, "A");
        DECIMAL_TO_HEX.put(11, "B");
        DECIMAL_TO_HEX.put(12, "C");
        DECIMAL_TO_HEX.put(13, "D");
        DECIMAL_TO_HEX.put(14, "E");
        DECIMAL_TO_HEX.put(15, "F");
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
        if (type == 2 || type == 8 || type == 16) {
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

    private static String convertFromDecimalToByType(int input, int type) {
        if (type == 2 || type == 8 || type == 16) {
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

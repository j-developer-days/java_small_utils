package com.jdev.algorithms;

public class Factorial {

    public static int recursive(int number) {
        if (number == 0 || number == 1) {
            return 1;
        }
        return number * recursive(number - 1);
    }

    public static long recursive(long number) {
        if (number == 0 || number == 1) {
            return 1;
        }
        return number * recursive(number - 1);
    }

    public static int loop(int number) {
        int result = 1;
        for (int i = 1; i <= number; i++) {
            result *= i;
        }
        return result;
    }

}

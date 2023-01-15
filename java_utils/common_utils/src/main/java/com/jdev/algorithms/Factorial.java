package com.jdev.algorithms;

/*
 * https://en.wikipedia.org/wiki/Factorial
 * */
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

    public static int loopWhile(int number) {
        int result = 1;
        while (number > 0) {
            result *= number--;
        }
        return result;
    }

    public static int loopDoWhile(int number) {
        int result = 1;
        do {
            result *= number--;
        }
        while (number > 0);
        return result;
    }

}

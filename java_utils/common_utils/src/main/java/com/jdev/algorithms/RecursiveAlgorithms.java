package com.jdev.algorithms;

/**
 * https://www.topjavatutorial.com/java/java-programs/top-programming-interview-questions-using-recursion-in-java/
 * */
public class RecursiveAlgorithms {

    public static int sumOfDigitsRecursive(int number) {
        return number == 0 ? 0 : number % 10 + sumOfDigitsRecursive(number / 10);
    }

    public static void decimalToBinary(int number) {
        if (number > 0) {
            decimalToBinary(number / 2);
            System.out.println(number);
        }
    }

    public static long power(int x, int n) {
        long y;
        if (n == 0) {
            return 1;
        } else if (n == 1) {
            return x;
        } else {
            y = power(x, n / 2);
            y *= y;
            if (n % 2 == 0) {
                return y;
            }
        }
        return x * y;
    }

}

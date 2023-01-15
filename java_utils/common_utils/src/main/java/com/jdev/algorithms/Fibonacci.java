package com.jdev.algorithms;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci {

    public static List<Integer> iterative(int n) {
        List<Integer> integers = new ArrayList<>(n);
        int num1 = 0, num2 = 1;

        int counter = 0;

        while (counter <= n) {
            integers.add(num1);

            int num3 = num2 + num1;
            num1 = num2;
            num2 = num3;
            counter++;
        }

        return integers;
    }

    public static int recursive(int n) {
        if (n <= 1) {
            return n;
        }
        return recursive(n - 1) + recursive(n - 2);
    }

    public static int dynamic(int n) {
        int[] fibonacci = new int[n + 2];
        fibonacci[0] = 0;
        fibonacci[1] = 1;

        for (int i = 2; i <= n; i++) {
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
        }
        return fibonacci[n];
    }

}

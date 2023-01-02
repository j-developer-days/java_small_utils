package com.jdev.util;

import java.util.Random;

public class RandomUtils {

    private static final Random RANDOM = new Random();

    public static Random getRandom() {
        return RANDOM;
    }

    public static int randomNumber() {
        return RANDOM.nextInt();
    }

    public static int randomFromTo(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

}

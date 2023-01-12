package com.jdev.arrays;

import com.jdev.util.RandomUtils;
import lombok.Setter;

@Setter
public class RandomArray implements ArrayWorkingI {

    private int startValue = 0;
    private int endValue = 1_000;

    /**
     * Factory Method Pattern
     * */
    @Override
    public int fillArray() {
        return RandomUtils.randomFromTo(startValue, endValue);
    }

}

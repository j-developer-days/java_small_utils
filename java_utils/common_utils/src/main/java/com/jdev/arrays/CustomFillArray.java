package com.jdev.arrays;

import lombok.Setter;

import java.util.function.Function;

@Setter
public class CustomFillArray extends SummaryArray {

    private int startValue = 1;
    private Function<Integer, Integer> function;

    /**
     * Factory Method Pattern
     */
    @Override
    public int fillArray() {
        this.startValue = function.apply(this.startValue);
        return this.startValue;
    }
}

package com.jdev.arrays.fill;

import com.jdev.arrays.SummaryArray;
import lombok.Setter;

import java.util.function.Function;

@Setter
public class CustomFillArray extends SummaryArray {

    private int startValue = 1;
    private Function<Integer, Integer> function;
    private boolean startBefore;

    /**
     * Factory Method Pattern
     */
    @Override
    public int fillArray() {
        if (startBefore) {
            int tempForReturn = this.startValue;
            this.startValue = function.apply(this.startValue);
            return tempForReturn;
        } else {
            this.startValue = function.apply(this.startValue);
            return this.startValue;
        }
    }

}

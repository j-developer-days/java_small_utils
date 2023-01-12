package com.jdev.arrays;

public class OrderedArray implements ArrayWorkingI {

    private int startValue = 1;
    private int currentValue = 1;

    public OrderedArray(int startValue) {
        setStartValue(startValue);
    }

    public OrderedArray() {

    }

    /**
     * Factory Method Pattern
     */
    @Override
    public int fillArray() {
        return currentValue++;
    }

    public void setStartValue(int startValue) {
        this.startValue = startValue;
        this.currentValue = this.startValue;
    }
}

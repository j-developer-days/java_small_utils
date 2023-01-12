package com.jdev.arrays;

public class ArrayFillFactory {

    public ArrayWorkingI getArrayWorking(FillType fillType) {
        switch (fillType) {
            case RANDOM: {
                return new RandomArray();
            }
            case CUSTOM: {
                return new CustomFillArray();
            }
            case ORDERED: {
                return new OrderedArray();
            }
            default: {
                throw new RuntimeException("Not right fill type - " + fillType);
            }
        }
    }

}

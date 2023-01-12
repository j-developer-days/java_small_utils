package com.jdev.arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FillType {

    RANDOM(new RandomArray()), ORDERED(new OrderedArray()), CUSTOM(new CustomFillArray());

    private ArrayWorkingI arrayWorkingI;

}
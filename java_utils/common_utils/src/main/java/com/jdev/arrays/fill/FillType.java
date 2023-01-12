package com.jdev.arrays.fill;

import com.jdev.arrays.ArrayInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FillType {

    RANDOM(new RandomArray()), ORDERED(new OrderedArray()), CUSTOM(new CustomFillArray());

    private ArrayInterface arrayInterface;
}

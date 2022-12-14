package com.jdev.screen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorType {

    CHOOSE_ONE_OF_3_OPTIONS("We need to choose one of three options: by count, by date time, by period"),
    NOT_RIGHT_OPTION_FOR_PERIOD_PREFIX("Not right period prefix");

    private String description;

}

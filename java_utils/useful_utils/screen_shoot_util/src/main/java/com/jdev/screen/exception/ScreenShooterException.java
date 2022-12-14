package com.jdev.screen.exception;

import com.jdev.screen.enums.ErrorType;
import lombok.Getter;

public class ScreenShooterException extends RuntimeException {

    @Getter
    private ErrorType errorType;

    public ScreenShooterException() {
        super();
    }

    public ScreenShooterException(String message) {
        super(message);
    }

    public ScreenShooterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScreenShooterException(Throwable cause) {
        super(cause);
    }

    private ScreenShooterException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    protected ScreenShooterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static ScreenShooterException of(ErrorType errorType, String message) {
        return new ScreenShooterException(errorType, message);
    }
}

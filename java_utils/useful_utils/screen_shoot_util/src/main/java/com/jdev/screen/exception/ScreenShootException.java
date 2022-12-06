package com.jdev.screen.exception;

import com.jdev.screen.enums.ErrorType;
import lombok.Getter;

public class ScreenShootException extends RuntimeException {

    @Getter
    private ErrorType errorType;

    public ScreenShootException() {
    }

    private ScreenShootException(ErrorType errorType) {
        this.errorType = errorType;
    }

    private ScreenShootException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public ScreenShootException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

    public ScreenShootException(String message) {
        super(message);
    }

    public ScreenShootException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScreenShootException(Throwable cause) {
        super(cause);
    }

    public ScreenShootException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static ScreenShootException of(ErrorType errorType) {
        return new ScreenShootException(errorType);
    }

    public static ScreenShootException of(ErrorType errorType, String message) {
        return new ScreenShootException(errorType, message);
    }

}

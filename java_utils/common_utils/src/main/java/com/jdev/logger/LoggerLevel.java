package com.jdev.logger;

public enum LoggerLevel {

    OFF, ERROR, WARN, INFO, DEBUG, TRACE;

    public boolean canLog(LoggerLevel loggerLevel) {
        return loggerLevel.ordinal() >= this.ordinal();
    }

}

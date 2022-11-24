package com.jdev.logger;

import com.jdev.file.PropertiesFileUtils;
import com.jdev.util.DateUtils;
import lombok.NoArgsConstructor;

import java.util.Properties;

@NoArgsConstructor
public class ConsoleLogger {

    private static final String DEFAULT_CONFIG_FILE_NAME = "logger.properties";
    private static final String BEGIN_AND_END = "*****";

    private static Properties properties;

    static {
        setPropertiesFilePath();
    }

    private String canonicalClassName;

    public ConsoleLogger(Class aClass) {
        this.canonicalClassName = aClass.getCanonicalName();
    }

    private static void setPropertiesFilePath() {
        setPropertiesFilePath(null);
    }

    private static void setPropertiesFilePath(String path) {
        if (path == null) {
            properties = PropertiesFileUtils.readFromPropertiesFile(ConsoleLogger.class.getClassLoader().getResource("./" + DEFAULT_CONFIG_FILE_NAME).getFile());
        } else {
            properties = PropertiesFileUtils.readFromPropertiesFile(path);
        }
    }

    public void error(String message) {
        createLog(LoggerLevel.ERROR, message);
    }

    public void error(String message, Exception e) {
        createLog(LoggerLevel.ERROR, errorLog(message, e));
    }

    public void warn(String message) {
        createLog(LoggerLevel.WARN, message);
    }

    public void info(String message) {
        createLog(LoggerLevel.INFO, message);
    }

    public void debug(String message) {
        createLog(LoggerLevel.DEBUG, message);
    }

    public void trace(String message) {
        createLog(LoggerLevel.TRACE, message);
    }

    private void createLog(LoggerLevel loggerLevel, String message) {
        String loggerLevelAsString = properties.getProperty(canonicalClassName);
        if (loggerLevelAsString == null) {
            loggerLevelAsString = LoggerLevel.OFF.name();
        }
        LoggerLevel loggerLevelFromPropertiesFile;
        try {
            loggerLevelFromPropertiesFile = LoggerLevel.valueOf(loggerLevelAsString);
        } catch (IllegalArgumentException e) {
            loggerLevelFromPropertiesFile = LoggerLevel.OFF;
        }

        if (loggerLevel.canLog(loggerLevelFromPropertiesFile)) {
            System.out.println(new StringBuilder(BEGIN_AND_END).append(" ").append("DATE TIME - ")
                    .append(DateUtils.getLocalDateTimeNowAsText())
                    .append("\t").append("THREAD [").append(Thread.currentThread().getName()).append("]")
                    .append("\t").append(loggerLevel.name()).append(": ").append(message).append(" ")
                    .append(BEGIN_AND_END));
        }

    }

    private String errorLog(String message, Exception e) {
        return new StringBuilder(message).append("EXCEPTION INFO - ").append("'").append(e.getClass().getCanonicalName())
                .append("\tMESSAGE - ").append(e.getMessage()).append("'").toString();
    }
}

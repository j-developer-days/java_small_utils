package com.jdev.logger;

import com.jdev.file.PropertiesFileUtils;
import com.jdev.util.ConsoleUtils;
import com.jdev.util.DateUtils;
import com.jdev.util.StringUtils;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@NoArgsConstructor
public class ConsoleLogger {

    private static final String DEFAULT_CONFIG_FILE_NAME = "logger.properties";
    private static final String BEGIN_AND_END = StringUtils.multipleCharByCount('*', 9);
    private static Map<String, LoggerLevel> definedLogger;

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

    public static void setPropertiesFilePath(String path) {
        if (path == null) {
            properties = PropertiesFileUtils.readFromPropertiesFile(ConsoleLogger.class.getClassLoader().getResource("./" + DEFAULT_CONFIG_FILE_NAME).getFile());
        } else {
            properties = PropertiesFileUtils.readFromPropertiesFile(path);
        }
        if (properties != null && !properties.isEmpty()) {
            definedLogger = new HashMap<>(properties.size());
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
        LoggerLevel loggerLevelAlreadyDefined = definedLogger.get(canonicalClassName);
        if (loggerLevelAlreadyDefined == null) {
            String loggerLevelAsString = properties.getProperty(canonicalClassName);
            if (loggerLevelAsString == null) {
                Optional<String> firstFind = properties.keySet().stream().map(Object::toString).filter(key -> canonicalClassName.startsWith(key)
                        || canonicalClassName.contains(key) || canonicalClassName.endsWith(key)).findFirst();
                if (firstFind.isEmpty()) {
                    String[] split = canonicalClassName.split("[.]");
                    StringBuilder result = new StringBuilder(canonicalClassName.length() / 2);
                    for (var i = 0; i < split.length - 1; i++) {
                        result.append(split[i].charAt(0)).append(".");
                    }
                    result.append(split[split.length - 1]);

                    loggerLevelAsString = properties.getProperty(result.toString());
                } else {
                    loggerLevelAsString = properties.getProperty(firstFind.get());
                }
            }
            try {
                loggerLevelAlreadyDefined = loggerLevelAsString == null ? LoggerLevel.OFF : LoggerLevel.valueOf(loggerLevelAsString.toUpperCase());
            } catch (IllegalArgumentException e) {
                loggerLevelAlreadyDefined = LoggerLevel.OFF;
                ConsoleUtils.logError("Not right logger level value from properties file, current is - '" + loggerLevelAsString + "'", e);
            }
            definedLogger.put(canonicalClassName, loggerLevelAlreadyDefined);
        }


        if (loggerLevel.canLog(loggerLevelAlreadyDefined)) {
            System.out.println(new StringBuilder(BEGIN_AND_END).append(StringUtils.SPACE).append("DATE TIME - ")
                    .append(DateUtils.getLocalDateTimeNowAsText())
                    .append(StringUtils.TAB).append("THREAD [").append(Thread.currentThread().getName()).append("]")
                    .append(StringUtils.TAB).append(loggerLevel.name()).append(StringUtils.COLON).append(StringUtils.SPACE)
                    .append(message).append(StringUtils.SPACE)
                    .append(BEGIN_AND_END));
        }

    }

    private String errorLog(String message, Exception e) {
        return new StringBuilder(message).append("EXCEPTION INFO - ").append(StringUtils.APOSTROPHE).append(e.getClass().getCanonicalName())
                .append(StringUtils.TAB).append("MESSAGE - ").append(e.getMessage()).append(StringUtils.APOSTROPHE).toString();
    }
}

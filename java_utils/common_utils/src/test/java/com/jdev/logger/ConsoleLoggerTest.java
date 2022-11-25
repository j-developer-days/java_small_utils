package com.jdev.logger;

import com.jdev.util.ConsoleUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleLoggerTest {

    private static final ConsoleLogger CONSOLE_LOGGER = new ConsoleLogger(ConsoleLoggerTest.class);

    @AfterEach
    void addDelimiterAfterEachTest(){
        ConsoleUtils.printDelimiter('#');
    }

    private void log(String methodName){
        CONSOLE_LOGGER.error(methodName + " - this is error log");
        CONSOLE_LOGGER.error(methodName + " - this is error log", new RuntimeException("this is exception!"));
        CONSOLE_LOGGER.warn(methodName + " - this is error log");
        CONSOLE_LOGGER.info(methodName + " - this is error log");
        CONSOLE_LOGGER.debug(methodName + " - this is error log");
        CONSOLE_LOGGER.trace(methodName + " - this is error log");
    }

    @Test
    void test_error(){
        log("test_error");
    }

}
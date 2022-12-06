package com.jdev.logger;

import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class ConsoleLoggerTest {

    private static final ConsoleLogger CONSOLE_LOGGER = new ConsoleLogger(ConsoleLoggerTest.class);

    @AfterEach
    void addDelimiterAfterEachTest() {
        ConsoleUtils.printDelimiter('#');
    }

    private void log(String methodName) {
        CONSOLE_LOGGER.error(methodName + " - this is error log");
        CONSOLE_LOGGER.error(methodName + " - this is error log", new RuntimeException("this is exception!"));
        CONSOLE_LOGGER.warn(methodName + " - this is error log");
        CONSOLE_LOGGER.info(methodName + " - this is error log");
        CONSOLE_LOGGER.debug(methodName + " - this is error log");
        CONSOLE_LOGGER.trace(methodName + " - this is error log");
    }

    @Order(2)
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class ConsoleLoggerTestAllLevels{
        @Order(2)
        @Test
        void test_error() {
            setAnotherFilePath("logger_error.properties");
            log("test_error");
        }

        @Order(3)
        @Test
        void test_warn() {
            setAnotherFilePath("logger_warn.properties");
            log("test_warn");
        }

        @Order(4)
        @Test
        void test_info() {
            setAnotherFilePath("logger_info.properties");
            log("test_info");
        }

        @Order(5)
        @Test
        void test_debug() {
            setAnotherFilePath("logger_debug.properties");
            log("test_debug");
        }

        @Order(6)
        @Test
        void test_trace() {
            setAnotherFilePath("logger_trace.properties");
            log("test_trace");
        }

        @Order(7)
        @Test
        void test_trace_withParams() {
            setAnotherFilePath("logger_trace.properties");
            CONSOLE_LOGGER.trace("This is error message, param count = {} and second placeholder is {}", 10, "STRING");
        }

        @Order(8)
        @Test
        void test_trace_withParamsButWithoutParams() {
            setAnotherFilePath("logger_trace.properties");
            CONSOLE_LOGGER.trace("This is error message, param count = {} and second placeholder is {}");
        }

        @Order(9)
        @Test
        void test_trace_withParamsButLessPlaceholders() {
            setAnotherFilePath("logger_trace.properties");
            CONSOLE_LOGGER.trace("This is error message, param count = {}", 10, "STRING");
        }
    }

    @Order(4)
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class ConsoleLoggerTestAll{

        @Order(4)
        @Test
        void test_info_withLoggerPath() {
            setAnotherFilePath("logger_info.properties");
            log("test_info_withLoggerPath");
        }

        @Order(5)
        @Test
        void test_error_withConsoleLoggerPathProperty() {
            System.setProperty("console.logger.path", "src/test/resources/logger_error.properties");
            ConsoleLogger.setPropertiesFilePath(null);
            log("test_error_withConsoleLoggerPathProperty");
        }

        @Order(5)
        @Test
        void test_error_withConsoleLoggerPath() {
            ConsoleLogger.setPropertiesFilePath(null);
            log("test_error_withConsoleLoggerPath");
        }

    }

    @Order(1)
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
   class ConsoleLoggerTestLoggerOff{
       @Order(2)
       @Test
       void test_off() {
           setAnotherFilePath("logger_off.properties");
           log("test_off");
       }
       @Order(1)
       @Test
       void test_off_logger_not_right_name() {
           setAnotherFilePath("logger_not_right_name.properties");
           log("test_off_logger_not_right_name");
       }
       @Order(3)
       @Test
       void test_off_logger_without_entry() {
           setAnotherFilePath("logger_without_entry.properties");
           log("test_off_logger_without_entry");
       }
   }

    @Order(3)
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class ConsoleLoggerTestLoggerStartWithContainsEndWithAndSpringStyle{
        @Order(1)
        @Test
        void test_logger_start_with() {
            setAnotherFilePath("logger_start_with.properties");
            log("test_logger_start_with");
        }
        @Order(2)
        @Test
        void test_logger_end_with() {
            setAnotherFilePath("logger_end_with.properties");
            log("test_logger_end_with");
        }
        @Order(3)
        @Test
        void test_logger_contains() {
            setAnotherFilePath("logger_contains.properties");
            log("test_logger_contains");
        }
        @Order(4)
        @Test
        void test_logger_spring_style() {
            setAnotherFilePath("logger_spring_style.properties");
            log("test_logger_spring_style");
        }
    }

    private void setAnotherFilePath(String file) {
        ConsoleLogger.setPropertiesFilePath(ConsoleLogger.class.getClassLoader().getResource("./" + file).getFile());
    }
}
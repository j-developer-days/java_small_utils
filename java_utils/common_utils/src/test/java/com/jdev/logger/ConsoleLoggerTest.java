package com.jdev.logger;

import com.jdev.util.ConsoleUtils;
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




    private void setAnotherFilePath(String file) {
        ConsoleLogger.setPropertiesFilePath(ConsoleLogger.class.getClassLoader().getResource("./" + file).getFile());
    }
}
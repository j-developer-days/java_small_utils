package com.jdev.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilsTest {

    private static Stream<Arguments> methodSource_for_createLocalDateTimeManual() {
        return Stream.of(
                //day
//                Arguments.arguments(".", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)),
//                Arguments.arguments("1", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1)),
//                Arguments.arguments("9", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(9)),
//                Arguments.arguments("10", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(10)),
//                Arguments.arguments("10.", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(10)),//5

                //month
                Arguments.arguments(".9", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withMonth(9)),
                Arguments.arguments("1.9", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(9)),
                Arguments.arguments("10.9", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(10).withMonth(9)),
                Arguments.arguments("10.09", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(10).withMonth(9)),
                Arguments.arguments("1.09", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(9)),//10

                //year
                Arguments.arguments("..2022", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withYear(2022)),
                Arguments.arguments("..22", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withYear(2022)),
                Arguments.arguments("..22", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withYear(2022)),
                Arguments.arguments("1..2022", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withYear(2022)),
                Arguments.arguments("1..22", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withYear(2022)),
                Arguments.arguments("1..22", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withYear(2022)),
                Arguments.arguments("11..2022", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withYear(2022)),
                Arguments.arguments("11..22", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withYear(2022)),
                Arguments.arguments("11..22", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withYear(2022)),
                Arguments.arguments("11.2.2022", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(2).withYear(2022)),
                Arguments.arguments("11.2.22", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(2).withYear(2022)),
                Arguments.arguments("11.9.22", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(9).withYear(2022)),
                Arguments.arguments("11.12.2022", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(12).withYear(2022)),
                Arguments.arguments("11.02.22", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(2).withYear(2022)),
                Arguments.arguments("11.10.22", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(10).withYear(2022)),
                Arguments.arguments("11.12.2022", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(12).withYear(2022)),
                Arguments.arguments("11.02.22", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(2).withYear(2022)),
                Arguments.arguments("11.10.22", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(10).withYear(2022)),
                Arguments.arguments("1.1.2022", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(1).withYear(2022)),
                Arguments.arguments("11.2.22", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(2).withYear(2022)),
                Arguments.arguments("1.10.22", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(10).withYear(2022)),
                Arguments.arguments("1.1.2022 ", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(1).withYear(2022)),
                Arguments.arguments("11.2.22 ", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(2).withYear(2022)),
                Arguments.arguments("1.10.22 ", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(10).withYear(2022)),

                //hours
                Arguments.arguments("1.1.2022 1", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(1).withYear(2022).withHour(1)),
                Arguments.arguments("11.2.22 1", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(2).withYear(2022).withHour(1)),
                Arguments.arguments("1.10.22 1", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(10).withYear(2022).withHour(1)),
                Arguments.arguments("1.1.2022 23", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(1).withYear(2022).withHour(23)),
                Arguments.arguments("11.2.22 0", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(2).withYear(2022).withHour(0)),
                Arguments.arguments("1.10.22 0", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(10).withYear(2022).withHour(0)),
                Arguments.arguments("1.1.2022 23:", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(1).withYear(2022).withHour(23)),
                Arguments.arguments("11.2.22 0:", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(2).withYear(2022).withHour(0)),
                Arguments.arguments("1.10.22 0:", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(10).withYear(2022).withHour(0)),

                //minutes
                Arguments.arguments("1.1.2022 23:1", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(1).withYear(2022).withHour(23).withMinute(1)),
                Arguments.arguments("11.2.22 0:15", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(2).withYear(2022).withHour(0).withMinute(15)),
                Arguments.arguments("1.10.22 0:59", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(10).withYear(2022).withHour(0).withMinute(59)),

                //seconds
                Arguments.arguments("1.1.2022 23:1:59", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(1).withYear(2022).withHour(23).withMinute(1).withSecond(59)),
                Arguments.arguments("11.2.22 0:15:7", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(11).withMonth(2).withYear(2022).withHour(0).withMinute(15).withSecond(7)),
                Arguments.arguments("1.10.22 0:59:0", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).withDayOfMonth(1).withMonth(10).withYear(2022).withHour(0).withMinute(59).withSecond(0))
        );
    }

    @Test
    void test_createLocalDateTime() {
        assertEquals(LocalDateTime.of(2022, 10, 15, 19, 10, 9), DateUtils.createLocalDateTime("15.10.2022 19:10:09"));
    }

    //TODO WIP
    @MethodSource("methodSource_for_createLocalDateTimeManual")
    @ParameterizedTest
    void test_createLocalDateTimeManual(String inputDateTimeAsString, LocalDateTime localDateTime) {
        assertEquals(localDateTime, DateUtils.createLocalDateTimeManual(inputDateTimeAsString));
    }

    //TODO WIP
    @MethodSource("methodSource_for_createLocalDateTimeManual")
    @ParameterizedTest
    void test_createLocalDateTimeManual3(String inputDateTimeAsString, LocalDateTime localDateTime) {
        assertEquals(localDateTime, DateUtils.createLocalDateTimeManual3(inputDateTimeAsString));
    }

}
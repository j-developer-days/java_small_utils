package com.jdev.tester;

import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MeasureWorkingTimeTest {

    @RepeatedTest(5)
    void test_nanoSeconds() {
        MeasureWorkingTime measureWorkingTime = new MeasureWorkingTime();
        measureWorkingTime.beginNanoSeconds();
        measureWorkingTime.endNanoSeconds();
        long l = measureWorkingTime.differenceNanoSeconds();
        ConsoleUtils.printToConsole("differenceNanoSeconds - " + l);
        assertTrue(l > 100);
    }

    @ValueSource(ints = {1, 2, 3})
    @ParameterizedTest
    void test_millisSeconds(int seconds) throws InterruptedException {
        MeasureWorkingTime measureWorkingTime = new MeasureWorkingTime();
        measureWorkingTime.beginMillisSeconds();
        final long milliSeconds = TimeUnit.SECONDS.toMillis(seconds);
        Thread.sleep(milliSeconds);
        measureWorkingTime.endMillisSeconds();
        long l = measureWorkingTime.differenceMillisSeconds();
        ConsoleUtils.printToConsole("differenceMillisSeconds - " + l);
        assertTrue(l >= milliSeconds);
    }

    @DisplayName("test check how to work local time measure working time")
    @ValueSource(ints = {1, 2, 3})
    @ParameterizedTest
    void test_localTime(int timeOut) throws InterruptedException {
        MeasureWorkingTime measureWorkingTime = new MeasureWorkingTime();
        measureWorkingTime.beginLocalTime();
        final long milliSeconds = TimeUnit.SECONDS.toMillis(timeOut);
        Thread.sleep(milliSeconds);
        measureWorkingTime.endLocalTime();
        Duration duration = measureWorkingTime.differenceLocalTime();
        ConsoleUtils.printToConsole("differenceLocalTime - " + duration);
        assertTrue(duration.toSeconds() >= timeOut);
        assertTrue(duration.toNanos() >= 100);
    }

}
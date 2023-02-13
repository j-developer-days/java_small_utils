package com.jdev.thread;

import com.jdev.console.ConsoleUtils;

import java.util.concurrent.TimeUnit;

public class ThreadUtils {

    public static void sleepThread(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            ConsoleUtils.logError("sleeping thread ... " + millis + "millis", e);
        }
    }

    public static void sleepThreadInSeconds(long seconds) {
        sleepThread(TimeUnit.SECONDS.toMillis(seconds));
    }

    public static String getCurrentThreadName(){
        return "Current Thread name - " + Thread.currentThread().getName();
    }

}

package com.jdev.tester;

import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;

@NoArgsConstructor
public class MeasureWorkingTime {

    private long beginNanoSeconds;
    private long endNanoSeconds;

    private long beginMillisSeconds;
    private long endMillisSeconds;

    private LocalTime beginLocalTime;
    private LocalTime endLocalTime;

    public void beginNanoSeconds() {
        this.beginNanoSeconds = System.nanoTime();
    }

    public void endNanoSeconds() {
        this.endNanoSeconds = System.nanoTime();
    }

    public long differenceNanoSeconds() {
        return endNanoSeconds - beginNanoSeconds;
    }

    public void beginMillisSeconds() {
        this.beginMillisSeconds = System.currentTimeMillis();
    }

    public void endMillisSeconds() {
        this.endMillisSeconds = System.currentTimeMillis();
    }

    public long differenceMillisSeconds() {
        return endMillisSeconds - beginMillisSeconds;
    }

    public void beginLocalTime() {
        this.beginLocalTime = LocalTime.now();
    }

    public void endLocalTime() {
        this.endLocalTime = LocalTime.now();
    }

    public Duration differenceLocalTime() {
        return Duration.between(beginLocalTime, endLocalTime);
    }

}

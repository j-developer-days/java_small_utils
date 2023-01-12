package com.jdev.tester;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MeasureWorkingTime {

    private long beginTime;
    private long endTime;

    public long calculate() {
        return this.endTime - this.beginTime;
    }

    public void clear() {
        this.beginTime = -1;
        this.endTime = -1;
    }

    public void setBeginTime() {
        this.beginTime = System.currentTimeMillis();
    }

    public void setEndTime() {
        this.endTime = System.currentTimeMillis();
    }

}

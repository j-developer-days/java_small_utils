package com.jdev.screen.enums;

import com.jdev.screen.ScreenShooter;

public enum WorkType {
    WORK_TYPE_BY_COUNT {
        @Override
        public void leaveLastWorkType(ScreenShooter screenShooter) {
            screenShooter.setDoScreenShootBefore(null);
            screenShooter.setPeriod(null);
        }
    },
    WORK_TYPE_BY_DATE {
        @Override
        public void leaveLastWorkType(ScreenShooter screenShooter) {
            screenShooter.setCount(0);
            screenShooter.setPeriod(null);
        }
    },
    WORK_TYPE_BY_PERIOD {
        @Override
        public void leaveLastWorkType(ScreenShooter screenShooter) {
            screenShooter.setCount(0);
            screenShooter.setDoScreenShootBefore(null);
        }
    };

    public abstract void leaveLastWorkType(ScreenShooter screenShooter);
}

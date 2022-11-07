package com.jdev.screen;

import java.awt.*;

public class ScreenShooterStarter {

    public static void main(String[] args) {
        ScreenShooter screenShooter = new ScreenShooter();
        screenShooter.setCount(5);
        screenShooter.setSize(new Rectangle(0, 0, 1_000, 500));
        screenShooter.setDelayOnStartUp(2);
        screenShooter.setTimeOutBetweenScreenShoots(2);

        screenShooter.doScreenShoot();
    }

}

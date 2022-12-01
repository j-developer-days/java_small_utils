package com.jdev.screen;

import com.jdev.logger.ConsoleLogger;

import java.awt.*;
import java.time.LocalDateTime;

public class ScreenShooterStarter {

    private static final ConsoleLogger CONSOLE_LOGGER = new ConsoleLogger(ScreenShooterStarter.class);

    public static void main(String[] args) {
        ScreenShooter screenShooter = new ScreenShooter();
        screenShooter.setCount(1);
//        screenShooter.setSize(new Rectangle(0, 0, 1_000, 500));
//        screenShooter.setDelayOnStartUp(0);
//        screenShooter.setTimeOutBetweenScreenShoots(0);
//        screenShooter.setFileFormat("jpg");
//        screenShooter.setFileName("Image");
////        screenShooter.setStoreFolder("./images");
////        screenShooter.setStoreFolder("./useful_utils/screen_shoot_util/images");
////        screenShooter.setPeriod("ss3");
////        screenShooter.setDoScreenShootBefore(LocalDateTime.now().plusSeconds(2));
//        screenShooter.setDoScreenShootIfMousePositionChange(false);

        screenShooter.doScreenShoot();
        CONSOLE_LOGGER.info("SIMPLE");
    }

}

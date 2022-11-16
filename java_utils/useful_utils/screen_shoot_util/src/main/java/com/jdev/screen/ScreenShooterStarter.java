package com.jdev.screen;

import java.awt.*;
import java.time.LocalDateTime;

public class ScreenShooterStarter {

    public static void main(String[] args) {
        ScreenShooter screenShooter = new ScreenShooter();
//        screenShooter.setCount(2);
        screenShooter.setSize(new Rectangle(0, 0, 1_000, 500));
        screenShooter.setDelayOnStartUp(0);
        screenShooter.setTimeOutBetweenScreenShoots(0);
        screenShooter.setFileFormat("jpg");
        screenShooter.setFileName("Image");
//        screenShooter.setStoreFolder("./images");
        screenShooter.setStoreFolder("./useful_utils/screen_shoot_util/images");
//        screenShooter.setPeriod("ss3");
        screenShooter.setDoScreenShootBefore(LocalDateTime.now().plusSeconds(5));
        screenShooter.setDoScreenShootIfMousePositionChange(false);

        screenShooter.doScreenShoot();
    }

}

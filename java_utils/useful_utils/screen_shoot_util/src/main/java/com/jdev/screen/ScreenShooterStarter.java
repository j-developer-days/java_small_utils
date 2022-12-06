package com.jdev.screen;

import com.jdev.logger.ConsoleLogger;

import java.awt.*;
import java.time.LocalDateTime;

public class ScreenShooterStarter {

    private static final ConsoleLogger CONSOLE_LOGGER = new ConsoleLogger(ScreenShooterStarter.class);

    public static void main(String[] args) {
        ScreenShooter screenShooter = new ScreenShooter();

        screenShooter.doScreenShoot();
        CONSOLE_LOGGER.info("SIMPLE");
    }

}

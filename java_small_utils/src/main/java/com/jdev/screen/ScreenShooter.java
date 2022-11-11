package com.jdev.screen;

import com.jdev.utils.ConsoleUtil;
import com.jdev.utils.DateUtil;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
public class ScreenShooter {

    private static final String DEFAULT_FILE_FORMAT = "jpg";
    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            ConsoleUtil.logError("Init robot class", e);
        }
    }

    @Setter
    private int count;
    @Setter
    private Rectangle size;
    /**
     * <p>file formats may be: jpg, jpeg, png</p>
     * <p>by default is
     *
     * @see ScreenShooter#DEFAULT_FILE_FORMAT </p>
     */
    @Setter
    private String fileFormat = DEFAULT_FILE_FORMAT;
    @Setter
    private long delayOnStartUp;
    @Setter
    private long timeOutBetweenScreenShoots;

    private int innerCount;
    private long innerDelayOnStartUpMs;
    private long innerTimeOutBetweenScreenShootsMs;

    public void doScreenShoot() {
        System.out.println("begin...");

        validation();
        convert();
        timeOut(innerDelayOnStartUpMs);

        for (var i = 1; i <= innerCount; i++) {
            createAndSaveScreenShoot(i);
        }

        System.out.println("end...");
    }

    private void validation() {
        innerCount = count <= 0 ? 1 : count;
        if (size == null) {
            size = MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration().getBounds();
        }
    }

    private void convert() {
        innerDelayOnStartUpMs = TimeUnit.SECONDS.toMillis(delayOnStartUp);
        innerTimeOutBetweenScreenShootsMs = TimeUnit.SECONDS.toMillis(timeOutBetweenScreenShoots);
    }

    private void timeOut(long ms) {
        if (ms <= 0) {
            return;
        }
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            ConsoleUtil.logError("Thread sleep", e);
        }
    }

    private void createAndSaveScreenShoot(int imageNumber) {
        BufferedImage imageForSave = robot.createScreenCapture(size);
        File file = new File("Image-" + DateUtil.getLocalDateTimeAsText() + "_" + imageNumber + "." + fileFormat);

        try {
            ImageIO.write(imageForSave, fileFormat, file);
        } catch (IOException e) {
            ConsoleUtil.logError("Write image", e);
        }
        System.out.println("#" + imageNumber + "\tfileName - " + file.getAbsolutePath());
        timeOut(innerTimeOutBetweenScreenShootsMs);
    }

}

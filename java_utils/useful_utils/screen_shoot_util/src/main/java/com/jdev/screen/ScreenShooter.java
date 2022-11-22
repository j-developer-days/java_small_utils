package com.jdev.screen;

import com.jdev.util.ConsoleUtils;
import com.jdev.util.DateUtils;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@NoArgsConstructor
public class ScreenShooter {

    private static final String DEFAULT_FILE_FORMAT = "jpg";
    private static final String DEFAULT_FILE_NAME = "Image";

    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            ConsoleUtils.logError("Init robot class", e);
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
    /**
     * <p>file name by default should be, by example - <b>Image_09.11.2022 15:25:40_1.jpg</b></p>
     */
    @Setter
    private String fileName = DEFAULT_FILE_NAME;
    @Setter
    private long delayOnStartUp;
    @Setter
    private long timeOutBetweenScreenShoots;
    /**
     * <p>store folder path</p>
     */
    @Setter
    private String storeFolder;
    /**
     * <p>do screen shoot before indicated date time</p>
     */
    @Setter
    private LocalDateTime doScreenShootBefore;
    /**
     * <p>do screen shoot current time + indicated period</p>
     * <p>ss = seconds = ss100 = 100 seconds
     * mm = minutes = mm10 = 10 minutes
     * HH = hours
     * dd = days
     * MM = months
     * yy = years = yy1 = 1 year
     * </p>
     * {@link ScreenShooter#finishForPeriod}
     */
    @Setter
    private String period;
    @Setter
    private boolean doScreenShootIfMousePositionChange;

    private long innerDelayOnStartUpMs;
    private long innerTimeOutBetweenScreenShootsMs;
    private LocalDateTime finishForPeriod;

    public void doScreenShoot() {
        System.out.println("begin...");

        validation();
        convert();
        timeOut(innerDelayOnStartUpMs);

        if (finishForPeriod != null) {
            loopCreateAndSaveScreenShoots(localDateTime -> finishForPeriod.isAfter(localDateTime));
        } else if (doScreenShootBefore != null) {
            loopCreateAndSaveScreenShoots(localDateTime -> doScreenShootBefore.isAfter(localDateTime));
        } else {
            loopCreateAndSaveScreenShoots(null);
        }

        System.out.println("end...");
    }

    private void loopCreateAndSaveScreenShoots(Predicate<LocalDateTime> localDateTimePredicate){
        var countImages = 1;
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        while ((localDateTimePredicate != null && localDateTimePredicate.test(LocalDateTime.now())) || (countImages <= count)) {
            if (doScreenShootIfMousePositionChange) {
                Point currentMousePosition = MouseInfo.getPointerInfo().getLocation();
                if (mousePosition.equals(currentMousePosition)) {
                    continue;
                } else {
                    createScreenShootAndSaveIt(countImages);
                    countImages++;
                    mousePosition = currentMousePosition;
                }
            } else {
                createScreenShootAndSaveIt(countImages);
                countImages++;
            }
        }
    }

    private void validation() {
        if ((count > 0 && doScreenShootBefore != null && period != null) ||
                !(count > 0 ^ doScreenShootBefore != null ^ period != null))
        {
            throw new RuntimeException(
                    "Should be chose only one of option: \n1)count = '" + count + "'" + "\n2)doScreenShootBefore = '" +
                            doScreenShootBefore + "'\n3)period = '" + period + "'");
        }

        if (size == null) {
            size = MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration().getBounds();
        }
    }

    private void convert() {
        innerDelayOnStartUpMs = TimeUnit.SECONDS.toMillis(delayOnStartUp);
        innerTimeOutBetweenScreenShootsMs = TimeUnit.SECONDS.toMillis(timeOutBetweenScreenShoots);
        convertPeriod();
    }

    private void convertPeriod() {
        if (period != null && !period.isBlank()) {
            LocalDateTime now = LocalDateTime.now();
            final String periodDefinition = period.substring(0, 2);
            final long count = Long.valueOf(period.substring(2));
            switch (periodDefinition) {
                case "ss": {
                    finishForPeriod = now.plusSeconds(count);
                    break;
                }
                case "mm": {
                    finishForPeriod = now.plusMinutes(count);
                    break;
                }
                case "HH": {
                    finishForPeriod = now.plusHours(count);
                    break;
                }
                case "dd": {
                    finishForPeriod = now.plusDays(count);
                    break;
                }
                case "MM": {
                    finishForPeriod = now.plusMonths(count);
                    break;
                }
                case "yy": {
                    finishForPeriod = now.plusYears(count);
                    break;
                }
                default: {
                    throw new RuntimeException("Not right parameter - [" + periodDefinition + "]");
                }
            }
        }
    }

    private void timeOut(long ms) {
        if (ms <= 0) {
            return;
        }
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            ConsoleUtils.logError("Thread sleep", e);
        }
    }

    private void createScreenShootAndSaveIt(int countImages) {
        BufferedImage screenCapture = robot.createScreenCapture(size);
        String finalFileName = fileName + "_";
        if (DEFAULT_FILE_NAME.equals(this.fileName)) {
            finalFileName = finalFileName + DateUtils.getLocalDateTimeNowAsText(DateUtils.DEFAULT_DATE_TIME_FORMAT_AS_TEXT) + "_";
        }

        finalFileName += countImages;

        File outputFile;
        if (storeFolder == null) {
            outputFile = new File(finalFileName + "." + fileFormat);
        } else {
            outputFile = new File(storeFolder, finalFileName + "." + fileFormat);
        }

        try {
            ImageIO.write(screenCapture, fileFormat, outputFile);
        } catch (IOException e) {
            ConsoleUtils.logError("Write image", e);
        }
        System.out.println("# " + countImages + "\tfilename - " + outputFile.getAbsolutePath());
        if ((count > 0 && countImages != count) || count <= 0) {
            timeOut(innerTimeOutBetweenScreenShootsMs);
        }
    }

}

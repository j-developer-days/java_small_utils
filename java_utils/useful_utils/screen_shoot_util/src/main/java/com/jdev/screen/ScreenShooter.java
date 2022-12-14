package com.jdev.screen;

import com.jdev.logger.ConsoleLogger;
import com.jdev.screen.enums.ErrorType;
import com.jdev.screen.exception.ScreenShooterException;
import com.jdev.util.DateUtils;
import com.jdev.util.StringUtils;
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

    private static final ConsoleLogger CONSOLE_LOGGER = new ConsoleLogger(ScreenShooter.class);

    private static final String DEFAULT_FILE_FORMAT = "jpg";
    private static final String DEFAULT_FILE_NAME = "Image";

    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            CONSOLE_LOGGER.error("Init robot class", e);
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
        CONSOLE_LOGGER.info("begin...");

        validation();
        showAllEnabledOptions();
        convert();
        timeOut(innerDelayOnStartUpMs);

        if (finishForPeriod != null) {
            loopCreateAndSaveScreenShoots(localDateTime -> finishForPeriod.isAfter(localDateTime));
        } else if (doScreenShootBefore != null) {
            loopCreateAndSaveScreenShoots(localDateTime -> doScreenShootBefore.isAfter(localDateTime));
        } else {
            loopCreateAndSaveScreenShoots(null);
        }

        CONSOLE_LOGGER.info("end...");
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
            throw ScreenShooterException.of(ErrorType.CHOOSE_ONE_OF_3_OPTIONS, "Should be chose only one of option: \n1)count = '" + count + "'" + "\n2)doScreenShootBefore = '" +
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
                    throw ScreenShooterException.of(ErrorType.NOT_RIGHT_OPTION_FOR_PERIOD_PREFIX, "Not right parameter - [" + periodDefinition + "]");
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
            CONSOLE_LOGGER.error("Thread sleep", e);
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
            CONSOLE_LOGGER.error("Write image, output file - {}", e, outputFile.getAbsolutePath());
        }
        CONSOLE_LOGGER.info("# {}\tfilename - {}", countImages, outputFile.getAbsolutePath());
        if ((count > 0 && countImages != count) || count <= 0) {
            timeOut(innerTimeOutBetweenScreenShootsMs);
        }
    }

    private void showAllEnabledOptions() {
        CONSOLE_LOGGER.info(StringUtils.multipleCharByCount('=', 100));
        CONSOLE_LOGGER.info("Options:");
        CONSOLE_LOGGER.info("count = {}", count);

        CONSOLE_LOGGER.info("rectangleSize: ");
        CONSOLE_LOGGER.info("\tX = {}", size.getX());
        CONSOLE_LOGGER.info("\tY = {}", size.getY());
        CONSOLE_LOGGER.info("\twidth = {}", size.getWidth());
        CONSOLE_LOGGER.info("\theight = {}", size.getHeight());

        CONSOLE_LOGGER.info("fileFormat = {}", fileFormat);
        CONSOLE_LOGGER.info("fileName = {}", fileName);
        CONSOLE_LOGGER.info("delayOnStartUp = {} second(s)", delayOnStartUp);
        CONSOLE_LOGGER.info("timeOutBetweenScreenShoots = {} second(s)", timeOutBetweenScreenShoots);
        CONSOLE_LOGGER.info("storeFolder = {}", storeFolder);
        CONSOLE_LOGGER.info("doScreenShootBefore = {}",
                (doScreenShootBefore == null ? null : DateUtils.getLocalDateTimeAsText(doScreenShootBefore)));
        CONSOLE_LOGGER.info("period = {}", period);
        CONSOLE_LOGGER.info("doScreenShootIfMousePositionChange = {}", doScreenShootIfMousePositionChange);
        CONSOLE_LOGGER.info(StringUtils.multipleCharByCount('=', 100));
    }

}

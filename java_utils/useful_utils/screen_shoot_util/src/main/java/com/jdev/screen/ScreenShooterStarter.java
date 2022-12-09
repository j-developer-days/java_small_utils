package com.jdev.screen;

import com.jdev.console.ConsoleUtils;
import com.jdev.file.PropertiesFileUtils;
import com.jdev.logger.ConsoleLogger;
import com.jdev.screen.enums.MenuItem;
import com.jdev.screen.enums.WorkType;
import com.jdev.screen.exception.ScreenShootException;
import com.jdev.util.DateUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import static com.jdev.screen.enums.MenuItem.*;

public class ScreenShooterStarter {

    private static final ConsoleLogger CONSOLE_LOGGER = new ConsoleLogger(ScreenShooterStarter.class);
    private static final ScreenShooterStarter SCREEN_SHOOTER_STARTER = new ScreenShooterStarter();
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static ScreenShooter SCREEN_SHOOTER = new ScreenShooter();
    private WorkType lastWorkType;

    public static void main(String[] args) {
        SCREEN_SHOOTER_STARTER.showMenuParameters();
        SCREEN_SHOOTER_STARTER.mainMenu();
    }

    private static void exit() {
        closeConsole();
        ConsoleUtils.printToConsole("Close, Thank you!");
        System.exit(0);
    }

    private static final void closeConsole() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                ConsoleUtils.logError("Exception when close reader from console", e);
            }
        }
    }

    private static int consoleInputConvertToInt() {
        try {
            return Integer.valueOf(reader.readLine());
        } catch (IOException e) {
            ConsoleUtils.logError("problem when reading from console", e);
            throw new RuntimeException(e);
        }
    }

    private static boolean consoleInputConvertToBoolean() {
        try {
            return Boolean.valueOf(reader.readLine());
        } catch (IOException e) {
            ConsoleUtils.logError("problem when reading from console", e);
            throw new RuntimeException(e);
        }
    }

    private static String getConsoleInput() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            ConsoleUtils.logError("problem when reading from console", e);
            throw new RuntimeException(e);
        }
    }

    private void mainMenu() {
        ConsoleUtils.printToConsole("Please set next parameter: ");
        SCREEN_SHOOTER_STARTER.menuRightItem(createMenuItem(consoleInputConvertToInt()), null);
    }

    private void menuRightItem(MenuItem menuItem, String value) {
        menuRightItem(menuItem, value, false);
    }

    private void menuRightItem(MenuItem menuItem, String value, boolean isImport) {
        switch (menuItem) {
            case TIME_OUT: {
                ConsoleUtils.printToConsole("please insert time out in seconds, second should be great or equal 0");
                SCREEN_SHOOTER.setTimeOutBetweenScreenShoots(Integer.valueOf(value == null ? getConsoleInput() : value));
                break;
            }
            case DELAY_ON_START_UP: {
                ConsoleUtils.printToConsole("please insert delay in seconds between screen shoots, second should be great or equal 0");
                SCREEN_SHOOTER.setDelayOnStartUp(Integer.valueOf(value == null ? getConsoleInput() : value));
                break;
            }
            case WINDOW_SIZE: {
                ConsoleUtils.printToConsole("please insert " + WINDOW_SIZE.getMenuDescription());
                String[] split = (value == null ? getConsoleInput() : value).split(",");

                if (split.length < 4) {
                    ConsoleUtils.printToConsole("not right added, example: 0,150,1920,1080(x,y,width,height)");
                    break;
                }

                SCREEN_SHOOTER.setSize(new Rectangle(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]), Integer.valueOf(split[3])));
                break;
            }
            case FILE_FORMAT: {
                ConsoleUtils.printToConsole("please add file format: ");
                SCREEN_SHOOTER.setFileFormat(value == null ? getConsoleInput() : value);
                break;
            }
            case FILE_NAME_PREFIX: {
                ConsoleUtils.printToConsole("please set file name prefix: ");
                SCREEN_SHOOTER.setFileName(value == null ? getConsoleInput() : value);
                break;
            }
            case STORE_FOLDER: {
                ConsoleUtils.printToConsole("please set folder for save images: ");
                SCREEN_SHOOTER.setStoreFolder(value == null ? getConsoleInput() : value);
                break;
            }
            case DO_SCREEN_SHOOT_IF_MOUSE_MOVE: {
                SCREEN_SHOOTER.setDoScreenShootIfMousePositionChange(Boolean.valueOf(value == null ? getConsoleInput() : value));
                break;
            }
            case WORK_TYPE_BY_COUNT: {
                ConsoleUtils.printToConsole("Run by count, count should be great than 0");
                SCREEN_SHOOTER.setCount(Integer.valueOf(value == null ? getConsoleInput() : value));
                lastWorkType = WorkType.WORK_TYPE_BY_COUNT;
                minimalDemand();
                break;
            }
            case WORK_TYPE_BY_DATE: {
                ConsoleUtils.printToConsole("pattern should be - day.month.year hour:minute:second, example - " + DateUtils.getLocalDateTimeNowAsText());
                SCREEN_SHOOTER.setDoScreenShootBefore(DateUtils.createLocalDateTime(value == null ? getConsoleInput() : value));
                lastWorkType = WorkType.WORK_TYPE_BY_DATE;
                minimalDemand();
                break;
            }
            case WORK_TYPE_BY_PERIOD: {
                ConsoleUtils.printToConsole("set as period: you may use these prefixes: ss/mm/HH/dd/MM/yy - (/ means OR) and positive number, example - ss10 - 10 seconds, yy1 - 1 year");
                SCREEN_SHOOTER.setPeriod(value == null ? getConsoleInput() : value);
                lastWorkType = WorkType.WORK_TYPE_BY_PERIOD;
                minimalDemand();
                break;
            }
            case LEAVE_ONLY_LAST_WORK_TYPE: {
                lastWorkType.leaveLastWorkType(SCREEN_SHOOTER);
                minimalDemand();
                break;
            }
            case RUN: {
                try {
                    SCREEN_SHOOTER.doScreenShoot();
                } catch (ScreenShootException e) {
                    CONSOLE_LOGGER.error("please check exception type and add right value, or reset previous settings, error type - {}", e, e.getErrorType().getDescription());
                }
                break;
            }
            case RESET: {
                SCREEN_SHOOTER = new ScreenShooter();
                break;
            }
            case IMPORT_SETTINGS: {
                ConsoleUtils.printToConsole("Insert please file path with settings:");
                Properties properties = PropertiesFileUtils.readFromPropertiesFile(new File(getConsoleInput()));
                isImport = true;
                menuRightItem(RESET, null, true);
                properties.forEach((key, propertyValue) -> menuRightItem(MenuItem.valueOf(key.toString().toUpperCase()), propertyValue.toString(), true));
                menuRightItem(RUN, null, true);
                break;
            }
            case SHOW_MENU: {
                showMenuParameters();
                break;
            }
            case EXIT: {
                exit();
            }
            default: {
                ConsoleUtils.printToConsole("NOT RIGHT INPUT - " + menuItem);
                closeConsole();
            }
        }
        if (!menuItem.equals(RUN)) {
            menuItem.result();
        }

        if (!isImport) {
            SCREEN_SHOOTER_STARTER.mainMenu();
        }
    }

    private void minimalDemand() {
        ConsoleUtils.printToConsole("We achieved minimal demanding for run application, do you want to run? (y - for run)");
        if (getConsoleInput().equalsIgnoreCase("y")) {
            menuRightItem(RUN, null);
        }
    }

    private void showMenuParameters() {
        ConsoleUtils.printDelimiter('=', 100);
        ConsoleUtils.printToConsole("Please choose option (in parentheses examples)");
        com.jdev.screen.enums.MenuItem[] values = values();
        for (MenuItem menuItem : values) {
            writeToConsole(menuItem);
        }
        ConsoleUtils.printDelimiter('=', 100);
    }

}

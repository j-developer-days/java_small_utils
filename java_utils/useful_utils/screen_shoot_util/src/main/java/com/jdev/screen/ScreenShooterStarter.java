package com.jdev.screen;

import com.jdev.console.ConsoleUtils;
import com.jdev.file.PropertiesFileUtils;
import com.jdev.logger.ConsoleLogger;
import com.jdev.screen.enums.MenuItem;
import com.jdev.screen.enums.WorkType;
import com.jdev.screen.exception.ScreenShooterException;
import com.jdev.util.DateUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ScreenShooterStarter {

    private static final ConsoleLogger CONSOLE_LOGGER = new ConsoleLogger(ScreenShooterStarter.class);
    private static final ScreenShooterStarter SCREEN_SHOOTER_STARTER = new ScreenShooterStarter();
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static ScreenShooter SCREEN_SHOOTER = new ScreenShooter();
    private WorkType lastWorkType;

    public static void main(String[] args) {
        SCREEN_SHOOTER_STARTER.showMenuParameters();
        SCREEN_SHOOTER_STARTER.mainMenu();
        exit();
    }

    private static int getConsoleInputConvertToInt() {
        return getConsoleInputConvertToInt(null);
    }

    private static int getConsoleInputConvertToInt(String value) {
        String consoleInput = value;
        try {
            if (value == null) {
                consoleInput = getConsoleInput();
            }
            return Integer.valueOf(consoleInput);
        } catch (NumberFormatException e) {
            CONSOLE_LOGGER.error("Can't convert to int - {}", e, consoleInput);
            return getConsoleInputConvertToInt(value);
        }
    }

    private static String getConsoleInput() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            CONSOLE_LOGGER.error("problem when reading from console", e);
            return getConsoleInput();
        }
    }

    private static void exit() {
        closeConsole();
        ConsoleUtils.printToConsole("Close, Thank you!");
        System.exit(0);
    }

    private static void closeConsole() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                CONSOLE_LOGGER.error("Exception when close reader from console", e);
            }
        }
    }

    private void mainMenu() {
        ConsoleUtils.printToConsole("Please set next parameter: ");
        SCREEN_SHOOTER_STARTER.menuRightItem(MenuItem.createMenuItem(getConsoleInputConvertToInt()));
    }

    private void menuRightItem(MenuItem menuItem) {
        menuRightItem(menuItem, null);
    }

    private void menuRightItem(MenuItem menuItem, String value) {
        menuRightItem(menuItem, value, false);
    }

    private void menuRightItem(MenuItem menuItem, String value, boolean isImport) {
        switch (menuItem) {
            case TIME_OUT: {
                showBeforeSetParam(isImport, "please insert time out in seconds, second should be great or equal 0");
                SCREEN_SHOOTER.setTimeOutBetweenScreenShoots(getConsoleInputConvertToInt(value));
                break;
            }
            case DELAY_ON_START_UP: {
                showBeforeSetParam(isImport, "please insert delay in seconds between screen shoots, second should be great or equal 0");
                SCREEN_SHOOTER.setDelayOnStartUp(getConsoleInputConvertToInt(value));
                break;
            }
            case WINDOW_SIZE: {
                showBeforeSetParam(isImport, "please insert " + menuItem.getMenuDescription());
                String[] split = (value == null ? getConsoleInput() : value).split(",");

                if (split.length < 4) {
                    ConsoleUtils.printToConsole("not right added, example: 0,150,1920,1080(x,y,width,height)");
                    break;
                }

                SCREEN_SHOOTER.setSize(new Rectangle(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]), Integer.valueOf(split[3])));
                break;
            }
            case FILE_FORMAT: {
                showBeforeSetParam(isImport, "please add file format: ");
                SCREEN_SHOOTER.setFileFormat(value == null ? getConsoleInput() : value);
                break;
            }
            case FILE_NAME_PREFIX: {
                showBeforeSetParam(isImport, "please set file name prefix: ");
                SCREEN_SHOOTER.setFileName(value == null ? getConsoleInput() : value);
                break;
            }
            case STORE_FOLDER: {
                showBeforeSetParam(isImport, "please set folder for save images: ");
                SCREEN_SHOOTER.setStoreFolder(value == null ? getConsoleInput() : value);
                break;
            }
            case DO_SCREEN_SHOOT_IF_MOUSE_MOVE: {
                showBeforeSetParam(isImport, "please set true/false do screen shoot if mouse move");
                SCREEN_SHOOTER.setDoScreenShootIfMousePositionChange(Boolean.valueOf(value == null ? getConsoleInput() : value));
                break;
            }
            case WORK_TYPE_BY_COUNT: {
                showBeforeSetParam(isImport, "Run by count, count should be great than 0");
                SCREEN_SHOOTER.setCount(getConsoleInputConvertToInt(value));
                lastWorkType = WorkType.WORK_TYPE_BY_COUNT;
                minimalDemand();
                break;
            }
            case WORK_TYPE_BY_DATE: {
                showBeforeSetParam(isImport, "pattern should be - day.month.year hour:minute:second, example - " + DateUtils.getLocalDateTimeNowAsText());
                SCREEN_SHOOTER.setDoScreenShootBefore(DateUtils.createLocalDateTime(value == null ? getConsoleInput() : value));
                lastWorkType = WorkType.WORK_TYPE_BY_DATE;
                minimalDemand();
                break;
            }
            case WORK_TYPE_BY_PERIOD: {
                showBeforeSetParam(isImport, "set as period: you may use these prefixes: ss/mm/HH/dd/MM/yy - (/ means OR) and positive number, example - ss10 - 10 seconds, yy1 - 1 year");
                SCREEN_SHOOTER.setPeriod(value == null ? getConsoleInput() : value);
                lastWorkType = WorkType.WORK_TYPE_BY_PERIOD;
                minimalDemand();
                break;
            }
            case LEAVE_ONLY_LAST_WORK_TYPE: {
                leaveLastWorkType(true);
                break;
            }
            case RUN: {
                try {
                    SCREEN_SHOOTER.doScreenShoot();
                } catch (ScreenShooterException e) {
                    CONSOLE_LOGGER.error("please check exception type and add right value, or reset previous settings, error type - {}", e, e.getErrorType().getDescription());
                }
                break;
            }
            case RESET: {
                SCREEN_SHOOTER.doScreenShoot();
                break;
            }
            case IMPORT_SETTINGS: {
                ConsoleUtils.printToConsole("Insert please file path with settings:");
                Properties properties = PropertiesFileUtils.readFromPropertiesFile(new File(getConsoleInput()));
                isImport = true;
                menuRightItem(MenuItem.RESET, null, isImport);
                boolean finalIsImport = isImport;
                properties.forEach((key, propertyValue) -> menuRightItem(MenuItem.valueOf(key.toString().toUpperCase()), propertyValue.toString(), finalIsImport));
                menuRightItem(MenuItem.RUN, null, isImport);
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
        if (!(isImport || menuItem.equals(MenuItem.RUN) || menuItem.equals(MenuItem.IMPORT_SETTINGS))) {
            menuItem.result();
        }

        if (!isImport) {
            SCREEN_SHOOTER_STARTER.mainMenu();
        }
    }

    private void showBeforeSetParam(boolean isImport, String text) {
        if (!isImport) {
            ConsoleUtils.printToConsole(text);
        }
    }

    private void leaveLastWorkType(boolean isShowMinimalDemand) {
        lastWorkType.leaveLastWorkType(SCREEN_SHOOTER);
        if (isShowMinimalDemand) {
            minimalDemand();
        }
    }

    private void minimalDemand() {
        ConsoleUtils.printToConsole("We achieved minimal demanding for run application, do you want to run? (y - for run)");
        if (getConsoleInput().equalsIgnoreCase("y")) {
            leaveLastWorkType(false);
            menuRightItem(MenuItem.RUN);
        }
    }

    private void showMenuParameters() {
        ConsoleUtils.printDelimiter('=', 100);
        ConsoleUtils.printToConsole("Please choose option (in parentheses examples)");
        for (MenuItem menuItem : MenuItem.values()) {
            menuItem.writeToConsole();
        }
        ConsoleUtils.printDelimiter('=', 100);
    }

}

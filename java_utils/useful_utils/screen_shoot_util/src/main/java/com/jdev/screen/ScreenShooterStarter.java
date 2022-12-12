package com.jdev.screen;

import com.jdev.console.ConsoleUtils;
import com.jdev.logger.ConsoleLogger;
import com.jdev.screen.enums.MenuItem;
import com.jdev.util.DateUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScreenShooterStarter {

    private static final ConsoleLogger CONSOLE_LOGGER = new ConsoleLogger(ScreenShooterStarter.class);
    private static final ScreenShooterStarter SCREEN_SHOOTER_STARTER = new ScreenShooterStarter();
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static ScreenShooter SCREEN_SHOOTER = new ScreenShooter();

    public static void main(String[] args) {
        SCREEN_SHOOTER_STARTER.showMenuParameters();
        SCREEN_SHOOTER_STARTER.mainMenu();
        exit();
    }

    private static int getConsoleInputConvertToInt() {
        String consoleInput = null;
        try {
            consoleInput = getConsoleInput();
            return Integer.valueOf(consoleInput);
        } catch (NumberFormatException e) {
            CONSOLE_LOGGER.error("Can't convert to int - {}", e, consoleInput);
            return getConsoleInputConvertToInt();
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
        switch (menuItem) {
            case TIME_OUT: {
                ConsoleUtils.printToConsole("please insert time out in seconds, second should be great or equal 0");
                SCREEN_SHOOTER.setTimeOutBetweenScreenShoots(getConsoleInputConvertToInt());
                break;
            }
            case DELAY_ON_START_UP: {
                ConsoleUtils.printToConsole("please insert delay in seconds between screen shoots, second should be great or equal 0");
                SCREEN_SHOOTER.setDelayOnStartUp(getConsoleInputConvertToInt());
                break;
            }
            case WINDOW_SIZE: {
                ConsoleUtils.printToConsole("please insert " + menuItem.getMenuDescription());
                String[] split = getConsoleInput().split(",");

                if (split.length < 4) {
                    ConsoleUtils.printToConsole("not right added, example: 0,150,1920,1080(x,y,width,height)");
                    break;
                }

                SCREEN_SHOOTER.setSize(new Rectangle(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]), Integer.valueOf(split[3])));
                break;
            }
            case FILE_FORMAT: {
                ConsoleUtils.printToConsole("please add file format: ");
                SCREEN_SHOOTER.setFileFormat(getConsoleInput());
                break;
            }
            case FILE_NAME_PREFIX: {
                ConsoleUtils.printToConsole("please set file name prefix: ");
                SCREEN_SHOOTER.setFileName(getConsoleInput());
                break;
            }
            case STORE_FOLDER: {
                ConsoleUtils.printToConsole("please set folder for save images: ");
                SCREEN_SHOOTER.setStoreFolder(getConsoleInput());
                break;
            }
            case DO_SCREEN_SHOOT_IF_MOUSE_MOVE: {
                SCREEN_SHOOTER.setDoScreenShootIfMousePositionChange(Boolean.valueOf(getConsoleInput()));
                break;
            }
            case WORK_TYPE_BY_COUNT: {
                ConsoleUtils.printToConsole("Run by count, count should be great than 0");
                SCREEN_SHOOTER.setCount(getConsoleInputConvertToInt());
                break;
            }
            case WORK_TYPE_BY_DATE: {
                ConsoleUtils.printToConsole("pattern should be - day.month.year hour:minute:second, example - " + DateUtils.getLocalDateTimeNowAsText());
                SCREEN_SHOOTER.setDoScreenShootBefore(DateUtils.createLocalDateTime(getConsoleInput()));
                break;
            }
            case WORK_TYPE_BY_PERIOD: {
                ConsoleUtils.printToConsole("set as period: you may use these prefixes: ss/mm/HH/dd/MM/yy - (/ means OR) and positive number, example - ss10 - 10 seconds, yy1 - 1 year");
                SCREEN_SHOOTER.setPeriod(getConsoleInput());
                break;
            }
            case RUN: {
                SCREEN_SHOOTER.doScreenShoot();
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
        if (!menuItem.equals(MenuItem.RUN)) {
            menuItem.result();
        }

        SCREEN_SHOOTER_STARTER.mainMenu();
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

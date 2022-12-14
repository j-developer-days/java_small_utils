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
    private Properties propertiesForExport = new Properties();

    public static void main(String[] args) {
        SCREEN_SHOOTER_STARTER.showMenuParameters();
        SCREEN_SHOOTER_STARTER.mainMenu();
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
        SCREEN_SHOOTER_STARTER.menuRightItem(createMenuItem(consoleInputConvertToInt()));
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
                ConsoleUtils.printToConsole("please insert time out in seconds, second should be great or equal 0");
                final String finalValue = value == null ? getConsoleInput() : value;
                SCREEN_SHOOTER.setTimeOutBetweenScreenShoots(Integer.valueOf(finalValue));
                propertiesForExport.put(menuItem.name(), finalValue);
                break;
            }
            case DELAY_ON_START_UP: {
                ConsoleUtils.printToConsole("please insert delay in seconds between screen shoots, second should be great or equal 0");
                final String finalValue = value == null ? getConsoleInput() : value;
                SCREEN_SHOOTER.setDelayOnStartUp(Integer.valueOf(finalValue));
                propertiesForExport.put(menuItem.name(), finalValue);
                break;
            }
            case WINDOW_SIZE: {
                ConsoleUtils.printToConsole("please insert " + WINDOW_SIZE.getMenuDescription());
                final String finalValue = value == null ? getConsoleInput() : value;
                String[] split = finalValue.split(",");

                if (split.length < 4) {
                    ConsoleUtils.printToConsole("not right added, example: 0,150,1920,1080(x,y,width,height)");
                    break;
                }

                propertiesForExport.put(menuItem.name(), finalValue);
                SCREEN_SHOOTER.setSize(new Rectangle(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]), Integer.valueOf(split[3])));
                break;
            }
            case FILE_FORMAT: {
                ConsoleUtils.printToConsole("please add file format: ");
                final String finalValue = value == null ? getConsoleInput() : value;
                SCREEN_SHOOTER.setFileFormat(finalValue);
                propertiesForExport.put(menuItem.name(), finalValue);
                break;
            }
            case FILE_NAME_PREFIX: {
                ConsoleUtils.printToConsole("please set file name prefix: ");
                final String finalValue = value == null ? getConsoleInput() : value;
                SCREEN_SHOOTER.setFileName(finalValue);
                propertiesForExport.put(menuItem.name(), finalValue);
                break;
            }
            case STORE_FOLDER: {
                ConsoleUtils.printToConsole("please set folder for save images: ");
                final String finalValue = value == null ? getConsoleInput() : value;
                SCREEN_SHOOTER.setStoreFolder(finalValue);
                propertiesForExport.put(menuItem.name(), finalValue);
                break;
            }
            case DO_SCREEN_SHOOT_IF_MOUSE_MOVE: {
                final String finalValue = value == null ? getConsoleInput() : value;
                SCREEN_SHOOTER.setDoScreenShootIfMousePositionChange(Boolean.valueOf(finalValue));
                propertiesForExport.put(menuItem.name(), finalValue);
                break;
            }
            case WORK_TYPE: {
                ConsoleUtils.printToConsole("Please choose of of 3 options: 81,82,83");
                break;
            }
            case WORK_TYPE_BY_COUNT: {
                ConsoleUtils.printToConsole("Run by count, count should be great than 0");
                final String finalValue = value == null ? getConsoleInput() : value;
                SCREEN_SHOOTER.setCount(Integer.valueOf(finalValue));
                propertiesForExport.put(menuItem.name(), finalValue);
                lastWorkType = WorkType.WORK_TYPE_BY_COUNT;
                minimalDemand();
                break;
            }
            case WORK_TYPE_BY_DATE: {
                ConsoleUtils.printToConsole("pattern should be - day.month.year hour:minute:second, example - " + DateUtils.getLocalDateTimeNowAsText());
                final String finalValue = value == null ? getConsoleInput() : value;
                SCREEN_SHOOTER.setDoScreenShootBefore(DateUtils.createLocalDateTime(finalValue));
                propertiesForExport.put(menuItem.name(), finalValue);
                lastWorkType = WorkType.WORK_TYPE_BY_DATE;
                minimalDemand();
                break;
            }
            case WORK_TYPE_BY_PERIOD: {
                ConsoleUtils.printToConsole("set as period: you may use these prefixes: ss/mm/HH/dd/MM/yy - (/ means OR) and positive number, example - ss10 - 10 seconds, yy1 - 1 year");
                final String finalValue = value == null ? getConsoleInput() : value;
                SCREEN_SHOOTER.setPeriod(finalValue);
                propertiesForExport.put(menuItem.name(), finalValue);
                lastWorkType = WorkType.WORK_TYPE_BY_PERIOD;
                minimalDemand();
                break;
            }
            case LEAVE_ONLY_LAST_WORK_TYPE: {
                if (lastWorkType == null) {
                    CONSOLE_LOGGER.warn("we didn't choose one of 3 options still!!!");
                } else {
                    lastWorkType.leaveLastWorkType(SCREEN_SHOOTER);
                    minimalDemand();
                }
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
            case EXPORT_SETTINGS: {
                File file = new File("export.properties");
                PropertiesFileUtils.writeToPropertiesFile(propertiesForExport, file);
                ConsoleUtils.printToConsole("Export file - " + file.getAbsolutePath());
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

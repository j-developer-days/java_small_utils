package com.jdev.screen.enums;

import com.jdev.console.ConsoleUtils;
import com.jdev.util.DateUtils;
import com.jdev.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MenuItem {

    TIME_OUT(1, "time out between screen shoots(in seconds)"),
    DELAY_ON_START_UP(2, "delay on start up(in seconds)"),
    WINDOW_SIZE(3, "size rectangle(example:0,0,100,150)(x,y,width,height) according"),
    FILE_FORMAT(4, "file format(png)"),
    FILE_NAME_PREFIX(5, "file name(MyImage)"),
    STORE_FOLDER(6, "store folder(absolute path to folder)"),
    DO_SCREEN_SHOOT_IF_MOUSE_MOVE(7, "do screen shoot if mouse move(true/false)"),
    WORK_TYPE(8, "please choose one of variants:"),
    WORK_TYPE_BY_COUNT(81, "count(should be great than 0)"),
    WORK_TYPE_BY_DATE(82, "by date time(date time should be great than current date time - " + DateUtils.getLocalDateTimeNowAsText()),
    WORK_TYPE_BY_PERIOD(83, "set period(period should be great than 0)"),
    LEAVE_ONLY_LAST_WORK_TYPE(84, "Leave only last work type"),
    RUN(9, "Run screen shoots"),
    RESET(10, "Reset previous parameters"),
    SHOW_MENU(11, "Show menu"),
    IMPORT_SETTINGS(25, "Import settings"),
    EXPORT_SETTINGS(29, "Export settings"),
    EXIT(0, "exit");

    private int menuNumber;
    private String menuDescription;

    public static void writeToConsole(MenuItem menuItem) {
        ConsoleUtils.printToConsole("#" + menuItem.menuNumber + " - " + menuItem.menuDescription);
    }

    private static String forImportSettings() {
        StringBuilder result = new StringBuilder(250);
        MenuItem[] values = MenuItem.values();
        for (MenuItem menuItem : values) {
            if (menuItem.equals(WORK_TYPE) || menuItem.equals(LEAVE_ONLY_LAST_WORK_TYPE)
                    || menuItem.equals(RESET) || menuItem.equals(SHOW_MENU) || menuItem.equals(IMPORT_SETTINGS) || menuItem.equals(EXIT)) {
                continue;
            }
            result.append(menuItem).append(StringUtils.NEW_LINE);
        }
        return result.toString();
    }

    public static MenuItem createMenuItem(int number) {
        MenuItem[] values = MenuItem.values();
        for (var i = 0; i < values.length; i++) {
            if (number == values[i].menuNumber) {
                return values[i];
            }
        }
        throw new RuntimeException("Can't find right number - " + number);
    }

    public void result() {
        ConsoleUtils.printToConsole(StringUtils.multipleCharByCount('\t', 5) + "Success added - " + this.menuDescription);
    }
}

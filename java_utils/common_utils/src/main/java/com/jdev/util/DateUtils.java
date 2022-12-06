package com.jdev.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    public static final String DEFAULT_DATE_TIME_FORMAT_AS_TEXT = "dd_MM_yyyy__HH_mm_ss";
    private static final String DEFAULT_DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    private static final char DOT_CHAR = '.';

    public static LocalDateTime createLocalDateTime(String dateTimeAsString) {
        return LocalDateTime.parse(dateTimeAsString, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
    }

    //TODO WIP
    public static LocalDateTime createLocalDateTimeManual3(String dateTimeAsString) {
        if (dateTimeAsString.isBlank()) {
            throw new RuntimeException("input can't be blank!");
        }
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        final int length = dateTimeAsString.length();
        int dayIndex = dateTimeAsString.indexOf(DOT_CHAR);
        if (dayIndex == -1 && (length == 1 || length == 2)) {
            return now.withDayOfMonth(Integer.valueOf(dateTimeAsString));
        }

        int monthIndex = dateTimeAsString.indexOf(DOT_CHAR, dayIndex);
        if (dayIndex == monthIndex && dayIndex == 0 && length == 1) {
            return now;
        } else if (dayIndex == monthIndex && dayIndex == -1) {
            now = now.withDayOfMonth(Integer.valueOf(dateTimeAsString));
        }
        if (dayIndex == monthIndex) {
            now = now.withDayOfMonth(Integer.valueOf(dateTimeAsString.substring(0, monthIndex)));
        }


        int yearIndex = dateTimeAsString.indexOf(DOT_CHAR, monthIndex);
//        int yearIndex = dateTimeAsString.substring(monthIndex).indexOf(StringUtils.SPACE);


        return now;
    }

    //TODO WIP
    public static LocalDateTime createLocalDateTimeManual2(String dateTimeAsString) {
        if (dateTimeAsString.isBlank()) {
            throw new RuntimeException("input can't be blank!");
        }
//        Pattern.compile("[\\d]{0,2}[.]{1}[\\d]{0,1}[1-2]{0,1}[.]{1}[\\d]{0,4}");
        LocalDateTime now = LocalDateTime.now();
        int day = now.getDayOfMonth();
        int month = now.getMonthValue();
        int year = now.getYear();

        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        String[] separateDateAndTime = dateTimeAsString.split(" ");
        if (separateDateAndTime.length == 1) {
            String[] dateOnly = separateDateAndTime[0].split("\\.");
            if (dateOnly.length == 0) {
                LocalDateTime.now();
            }
        } else if (separateDateAndTime.length == 2) {

        }

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    //TODO WIP
    public static LocalDateTime createLocalDateTimeManual(String dateTimeAsString) {
        if (dateTimeAsString.isBlank()) {
            throw new RuntimeException("input can't be blank!");
        }

        LocalDateTime now = LocalDateTime.now();
        int day = now.getDayOfMonth();
        int month = now.getMonthValue();
        int year = now.getYear();

        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        for (var i = 0; i < dateTimeAsString.length(); i++) {
            if (i == 0) {
                if (dateTimeAsString.charAt(0) == DOT_CHAR) {
                    continue;// "."
                }
                day = Integer.valueOf(dateTimeAsString.substring(0, 1));//"1" "9"
            } else if (i == 1) {
                if (dateTimeAsString.charAt(0) == DOT_CHAR) {
                    month = Integer.valueOf(dateTimeAsString.substring(1, 2));//".9"
                } else if (dateTimeAsString.charAt(1) == DOT_CHAR) {
                    day = Integer.valueOf(dateTimeAsString.substring(0, 1));//".9"
                    month = Integer.valueOf(dateTimeAsString.substring(2, 3));//".9"
                } else {
                    day = Integer.valueOf(dateTimeAsString.substring(0, 2));//"10"
                }
            } else if (i == 2) {
                if (dateTimeAsString.charAt(1) == DOT_CHAR) {
                    month = Integer.valueOf(dateTimeAsString.substring(2, 3));//"1.9"
                } else if (dateTimeAsString.charAt(2) == DOT_CHAR) {
                    day = Integer.valueOf(dateTimeAsString.substring(0, 2));//"10."
                }
            } else if (i == 3) {
                if (dateTimeAsString.charAt(1) == DOT_CHAR) {
                    if (dateTimeAsString.length() == 3) {
                        month = Integer.valueOf(dateTimeAsString.substring(2, 3));//"1.2"
                    } else if (dateTimeAsString.length() == 4) {
                        month = Integer.valueOf(dateTimeAsString.substring(2, 4));//"1.09"
                    }
                } else {
                    month = Integer.valueOf(dateTimeAsString.substring(3, 4));//"10.9"
                }
            } else if (i == 4) {
                month = Integer.valueOf(dateTimeAsString.substring(4, 5));
            }
        }

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    public static String getLocalDateTimeNowAsText() {
        return getLocalDateTimeNowAsText(DEFAULT_DATE_TIME_FORMAT);
    }

    public static String getLocalDateTimeNowAsText(String pattern) {
        return getLocalDateTimeAsText(LocalDateTime.now(), pattern);
    }

    public static String getLocalDateTimeAsText(LocalDateTime localDateTime) {
        return getLocalDateTimeAsText(localDateTime, DEFAULT_DATE_TIME_FORMAT);
    }

    public static String getLocalDateTimeAsText(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

}

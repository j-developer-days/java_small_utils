package com.jdev.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final String DEFAULT_DATE_TIME_FORMAT_AS_TEXT = "dd_MM_yyyy__HH_mm_ss";
    private static final String DEFAULT_DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

    public static String getLocalDateTimeNowAsText() {
        return getLocalDateTimeNowAsText(DEFAULT_DATE_TIME_FORMAT);
    }

    public static LocalDateTime createLocalDateTime(String dateTimeAsString) {
        return LocalDateTime.parse(dateTimeAsString, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
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

    public static ZonedDateTime convertToTimeZone(ZonedDateTime zonedDateTimeInput, String zoneIdTo) {
        return convertToTimeZone(zonedDateTimeInput, ZoneId.of(zoneIdTo));
    }

    public static ZonedDateTime convertToTimeZone(ZonedDateTime zonedDateTimeInput, ZoneId zoneIdTo) {
        return zonedDateTimeInput.withZoneSameInstant(zoneIdTo);
    }

}

package com.jdev.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String getLocalDateTimeAsText(){
        return getLocalDateTimeAsText("dd.MM.yyyy HH:mm:ss");
    }

    public static String getLocalDateTimeAsText(String pattern){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

}

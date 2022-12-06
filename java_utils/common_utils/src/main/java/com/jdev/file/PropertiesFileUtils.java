package com.jdev.file;

import com.jdev.console.ConsoleUtils;

import java.io.*;
import java.util.Properties;

public class PropertiesFileUtils {

    public static void writeToPropertiesFile(Properties properties, String filePath, String comment) {
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            properties.store(outputStream, comment);
        } catch (IOException e) {
            ConsoleUtils.logError("exception when write to properties file", e);
        }
    }

    public static void writeToPropertiesFile(Properties properties, String filePath) {
        writeToPropertiesFile(properties, filePath, null);
    }

    public static Properties readFromPropertiesFile(String filePath) {
        return readFromPropertiesFile(new File(filePath));
    }

    public static Properties readFromPropertiesFile(File file) {
        Properties properties = new Properties();
        try (InputStream fileInputStream = new FileInputStream(file)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            ConsoleUtils.logError("exception when read from properties file", e);
        }
        return properties;
    }
}

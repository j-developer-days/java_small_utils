package com.jdev.file;

import java.io.*;
import java.util.Properties;

public class PropertiesFileUtils {

    public static void writeToPropertiesFile(Properties properties, String filePath, String comment) {
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            properties.store(outputStream, comment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToPropertiesFile(Properties properties, String filePath) {
        writeToPropertiesFile(properties, filePath, null);
    }

    public static Properties readFromPropertiesFile(String filePath) {
        Properties properties = new Properties();
        try (InputStream fileInputStream = new FileInputStream(filePath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}

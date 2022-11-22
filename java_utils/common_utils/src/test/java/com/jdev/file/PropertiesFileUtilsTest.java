package com.jdev.file;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertiesFileUtilsTest {

    @Test
    void test_readFromPropertiesFile() {
        Properties properties = PropertiesFileUtils.readFromPropertiesFile(ClassLoader.getSystemClassLoader().getResource("test_prop.properties").getPath());
        System.out.println(properties);
        assertEquals(5, properties.size());
        assertEquals("Person", properties.getProperty("class.name"));
        assertEquals("age", properties.getProperty("class.field2"));
    }

    @Test
    void test_writeToPropertiesFile() {
        final String path = "test_write.properties";
        Properties properties = new Properties();
        properties.put("today.date", "22.11.2022");
        properties.put("person.age", "50");
        properties.put("person.salary", "1000");
        PropertiesFileUtils.writeToPropertiesFile(properties, path);

        Properties propertiesRead = PropertiesFileUtils.readFromPropertiesFile(path);
        assertEquals(3, propertiesRead.size());

    }

    @Test
    void test_writeToPropertiesFile_withComment() {
        Properties properties = new Properties();
        properties.put("today.date", "22.11.2022");
        properties.put("person.age", "50");
        properties.put("person.salary", "1000");
        PropertiesFileUtils.writeToPropertiesFile(properties, "test_write_comment.properties", "THis is my comment");

    }

}
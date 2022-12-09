package com.jdev.file;

import com.jdev.console.ConsoleUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertiesFileUtilsTest {

    @Test
    void test_readFromPropertiesFile() {
        Properties properties = PropertiesFileUtils.readFromPropertiesFile(ClassLoader.getSystemClassLoader().getResource("test_prop.properties").getPath());
        ConsoleUtils.printToConsole(properties);
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
        assertEqualsByProperty(properties, propertiesRead, "today.date");
        assertEqualsByProperty(properties, propertiesRead, "person.age");
        assertEqualsByProperty(properties, propertiesRead, "person.salary");
    }

    @Test
    void test_writeToPropertiesFile_withComment() throws IOException {
        Properties properties = new Properties();
        properties.put("today.date", "22.11.2022");
        properties.put("person.age", "50");
        properties.put("person.salary", "1000");
        final String comment = "THis is my comment";
        PropertiesFileUtils.writeToPropertiesFile(properties, "test_write_comment.properties", comment);

        Properties propertiesRead = PropertiesFileUtils.readFromPropertiesFile("test_write_comment.properties");
        assertEquals(3, propertiesRead.size());
        assertEqualsByProperty(properties, propertiesRead, "today.date");
        assertEqualsByProperty(properties, propertiesRead, "person.age");
        assertEqualsByProperty(properties, propertiesRead, "person.salary");
        assertEquals(comment, Files.readAllLines(Paths.get("test_write_comment.properties")).get(0).substring(1));
    }

    private void assertEqualsByProperty(Properties propertiesExpected, Properties propertiesActual,
                                        String propertyKey) {
        assertEquals(propertiesExpected.getProperty(propertyKey), propertiesActual.getProperty(propertyKey));
    }

}
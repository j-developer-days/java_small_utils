package com.jdev;

import com.jdev.console.ConsoleUtils;
import com.jdev.file.FileResource;
import com.jdev.file.PropertiesFileUtils;
import com.jdev.thread.ThreadUtils;
import com.jdev.util.RandomUtils;
import com.jdev.util.StringUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionSql {

    private static Connection connection;
    private static Properties properties;
    private static ConnectionSql instance;
    @Getter
    private volatile String uuid;

    private static Properties getProperties() {
        if (properties == null) {
            properties = PropertiesFileUtils.readFromPropertiesFile(FileResource.findFileOnClassPath(
                    "liquibase-db_performance.properties").getPath());
        }
        return properties;
    }

    public static synchronized ConnectionSql getInstanceThreadSafe() {
        return getInstanceInner(false);
    }

    public static synchronized ConnectionSql getInstanceWithSleepThreadSafe() {
        return getInstanceInner(true);
    }

    private static ConnectionSql getInstanceInner(boolean withSleep) {
        if (instance == null) {
            if (withSleep) {
                ThreadUtils.sleepThreadInSeconds(RandomUtils.randomFromTo(1, 5));
            }
            instance = new ConnectionSql();
            instance.uuid = UUID.randomUUID().toString();
            final String message = LocalDateTime.now() + StringUtils.TAB +
                    ThreadUtils.getCurrentThreadName() + StringUtils.TAB + StringUtils.TAB +
                    "uuid - " + instance.uuid;
            ConsoleUtils.printToConsole(message);
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Properties properties = getProperties();
                return connection = DriverManager.getConnection(properties.getProperty("url"),
                        properties.getProperty("username"), properties.getProperty("password"));
            } else {
                return connection;
            }
        } catch (SQLException e) {
            ConsoleUtils.logError("connection with db problem", e);
            throw new RuntimeException(e);
        }
    }

}

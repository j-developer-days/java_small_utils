package com.jdev.file;

import com.jdev.console.ConsoleUtils;

import java.io.File;
import java.util.StringTokenizer;

public class FileResource {

    private static final String JAVA_CLASS_PATH = "java.class.path";
    private static final String PATH_SEPARATOR = "path.separator";

    public static File getFile(Class<?> aClass, String resourceName, boolean getParent) {
        return getFile((getParent ? aClass.getClassLoader().getParent() : aClass.getClassLoader()).getResource(resourceName).getFile(), "get resource by class name - '" + aClass.getCanonicalName() + "', resource name - " + resourceName + "\t get parent - " + getParent);
    }

    public static File getFile(Class<?> aClass, String resourceName) {
        return getFile(aClass.getResource(resourceName).getFile(), "get resource by class name - '" + aClass.getCanonicalName() + "', resource name - " + resourceName);
    }

    public static File getFile(Class<?> aClass) {
        return getFile(aClass.getProtectionDomain().getCodeSource().getLocation().getFile(), "get resource by class name - '" + aClass.getCanonicalName());
    }

    public static File getFile(String filePath) {
        return getFile(filePath, "find file - '" + filePath + "'");
    }

    private static File getFile(String filePath, String exceptionMessage) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return file;
            }
        } catch (Exception e) {
            ConsoleUtils.logError(exceptionMessage, e);
        }
        return null;
    }

    public static File findFileOnClassPath(final String fileName) {
        final StringTokenizer tokenizer = new StringTokenizer(System.getProperty(JAVA_CLASS_PATH), System.getProperty(PATH_SEPARATOR));

        while (tokenizer.hasMoreTokens()) {
            final String pathElement = tokenizer.nextToken();
            final File directoryOrJar = getFile(pathElement, "current file from classpath is - " + pathElement);
            final File absoluteDirectoryOrJar = directoryOrJar.getAbsoluteFile();

            if (absoluteDirectoryOrJar.isFile()) {
                final File target = new File(absoluteDirectoryOrJar.getParent(), fileName);
                if (target.exists()) {
                    return target;
                }
            } else {
                final File target = new File(directoryOrJar, fileName);
                if (target.exists()) {
                    return target;
                }
            }
        }
        return null;
    }


}

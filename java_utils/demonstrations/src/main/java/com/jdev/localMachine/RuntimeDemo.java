package com.jdev.localMachine;

import com.jdev.console.ConsoleUtils;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class RuntimeDemo {

    private static final double KILO = 1_024.;

    public static void main(String[] args) throws IOException {
        ConsoleUtils.printToConsole("availableProcessors - " + Runtime.getRuntime().availableProcessors());
        ConsoleUtils.printToConsole("JRE version - " + Runtime.version());
        ConsoleUtils.printDelimiter('*');

        getJVMemory();
        ConsoleUtils.printDelimiter('*');
        getFileSystemSpaceInfo();
        ConsoleUtils.printDelimiter('*');
    }


    static void getJVMemory() {
        getInfo("JVM free memory", Runtime.getRuntime().freeMemory());
        getInfo("JVM max memory", Runtime.getRuntime().maxMemory());
        getInfo("JVM total memory", Runtime.getRuntime().totalMemory());
    }

    private static void getFileSystemSpaceInfo() throws IOException {
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path path : rootDirectories) {
            FileStore fileStore = Files.getFileStore(path);
            ConsoleUtils.printToConsole("path - " + path);
            long totalSpace = fileStore.getTotalSpace();
            long usableSpace = fileStore.getUsableSpace();
            getInfo("Total", totalSpace);
            getInfo("Free", usableSpace);
            getInfo("Used", totalSpace - usableSpace);
            getInfo("Unallocated", fileStore.getUnallocatedSpace());
        }
    }

    private static void getInfo(String spaceName, long space) {
        ConsoleUtils.printToConsole("\t\t" + spaceName + " space is - " + space + " byte or " + (space / KILO) + " kilobyte or " + (space / (Math.pow(KILO, 2)))
                + " megabyte or " + (space / (Math.pow(KILO, 3))) + " gigabyte");
    }

}

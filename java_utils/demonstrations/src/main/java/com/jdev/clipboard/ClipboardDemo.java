package com.jdev.clipboard;

import com.jdev.console.ConsoleUtils;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class ClipboardDemo {

    public static void main(String[] args) throws AWTException {
        showTypes();

        LocalTime localTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        ConsoleUtils.printToConsole("to clipboard - " + localTime);
        copyToClipboard("Text - " + localTime, false);

        getFromClipBoard();
//        pasteTextFromClipboard();
        getOsInfo();
        getStateOfLockingKeysInKeyboard();

        ConsoleUtils.printToConsole("screen resolution in dots-per-inch - " + Toolkit.getDefaultToolkit().getScreenResolution());
    }

    private static void copyToClipboard(String text, boolean isLastOperation) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
        if (isLastOperation || System.getProperty("os.name").equalsIgnoreCase("linux")) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void getFromClipBoard() {
        try {
            ConsoleUtils.printToConsole("From clipboard - " +
                    Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void pasteTextFromClipboard() throws AWTException {
        Robot robot = new Robot();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        robot.mouseMove((int)screenSize.getWidth() / 2, (int)screenSize.getHeight() / 2);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
    }

    private static void showTypes() {
        DataFlavor[] availableDataFlavors = Toolkit.getDefaultToolkit().getSystemClipboard().getAvailableDataFlavors();
        for (DataFlavor flavor : availableDataFlavors) {
            ConsoleUtils.printToConsole(flavor);
        }
    }

    private static void getOsInfo(){
        ConsoleUtils.printToConsole("OS name -> " + System.getProperty("os.name"));
        ConsoleUtils.printToConsole("OS version -> " + System.getProperty("os.version"));
        ConsoleUtils.printToConsole("OS Architecture -> " + System.getProperty("os.arch"));
    }
    
    private static void getStateOfLockingKeysInKeyboard(){
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        for (int i = 0; i < 21; i++){
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ConsoleUtils.printToConsole("_________________________________");
            ConsoleUtils.printToConsole("State CAPS_LOCK - " + defaultToolkit.getLockingKeyState(KeyEvent.VK_CAPS_LOCK));
            ConsoleUtils.printToConsole("State NUM_LOCK - " + defaultToolkit.getLockingKeyState(KeyEvent.VK_NUM_LOCK));
            ConsoleUtils.printToConsole("State SCROLL_LOCK - " + defaultToolkit.getLockingKeyState(KeyEvent.VK_SCROLL_LOCK));
        }
    }
}

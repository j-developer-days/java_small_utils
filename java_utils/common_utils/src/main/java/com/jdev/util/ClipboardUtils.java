package com.jdev.util;

import com.jdev.console.ConsoleUtils;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class ClipboardUtils {

    public static void copyToClipboard(String text, boolean isLastOperation) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
        if (isLastOperation || System.getProperty("os.name").equalsIgnoreCase("linux")) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                ConsoleUtils.logError("problem with sleeping thread", e);
            }
        }
    }

}

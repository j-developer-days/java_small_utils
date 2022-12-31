package com.jdev.mouse_info;

import com.jdev.console.ConsoleUtils;

import java.awt.*;
import java.awt.image.ColorModel;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MouseInfoDemo {

    public static void main(String[] args) throws InterruptedException {
//        showMouseInfo();
//        ConsoleUtils.printDelimiter('!', 250);

//        showCoordinate();
        getMouseCoordinateByInterval();
    }

    private static void getMouseCoordinateByInterval() throws InterruptedException {
        Scanner scannerFromConsole = new Scanner(System.in);

        int count = 1;
        boolean isSleep = true;

        for (;true;){
            if(isSleep){
                Thread.sleep(TimeUnit.SECONDS.toMillis(3));
            }else {
                isSleep = true;
            }

            Point location = MouseInfo.getPointerInfo().getLocation();
            ConsoleUtils.printToConsole("date - " + LocalDateTime.now() + "\tX = " + location.getX() + "\tY = " + location.getY());

            if(++count > 5){
                ConsoleUtils.printToConsole("Do you want to continue? \n If you want to continue please press 'c' or if you want to stop press 's', thanks!");
                count = 1;
                isSleep = false;
                if(scannerFromConsole.nextLine().equals("s")){
                    break;
                }
            }
        }

        ConsoleUtils.printToConsole("Stop!");
    }

    private static void showCoordinate() throws InterruptedException {
        for (;true;){
            Thread.sleep(5_000);
            Point location = MouseInfo.getPointerInfo().getLocation();
            ConsoleUtils.printToConsole("date - " + LocalDateTime.now() + "\tX = " + location.getX() + "\tY = " + location.getY());
        }
    }

    private static void showMouseInfo() {
        ConsoleUtils.printToConsole("get number of button on the mouse - " + MouseInfo.getNumberOfButtons());
        ConsoleUtils.printDelimiter('*', 100);

        Point pointLocation = MouseInfo.getPointerInfo().getLocation();
        ConsoleUtils.printToConsole("X = " + pointLocation.getX() + "\tY = " + pointLocation.getY());
        ConsoleUtils.printDelimiter('-');

        ConsoleUtils.printToConsole("get mouse info device configuration count - " + MouseInfo.getPointerInfo().getDevice().getConfigurations().length);
        ConsoleUtils.printDelimiter('$');

        GraphicsConfiguration defaultConfiguration = MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration();
        Rectangle bounds = defaultConfiguration.getBounds();
        showRectangle(bounds);
        ConsoleUtils.printDelimiter('-', 50);

        ColorModel colorModel = defaultConfiguration.getColorModel();
        ConsoleUtils.printToConsole("colorModel - " + colorModel);
        final var pixel = 100;
        ConsoleUtils.printToConsole("red - " + colorModel.getRed(pixel) + "\tgreen - " + colorModel.getGreen(pixel) + "\tblue - " + colorModel.getBlue(pixel));
        ConsoleUtils.printDelimiter('|', 50);

        ConsoleUtils.printToConsole("color model num components = " + colorModel.getNumComponents());
        System.out.print("color model transparency = " + colorModel.getTransparency() + "\t");
        switch (colorModel.getTransparency()){
            case Transparency.OPAQUE: {
                ConsoleUtils.printToConsole("OPAQUE");
                break;
            }
            case Transparency.BITMASK: {
                ConsoleUtils.printToConsole("BITMASK");
                break;
            }case Transparency.TRANSLUCENT: {
                ConsoleUtils.printToConsole("TRANSLUCENT");
                break;
            }
        }

    }

    private static void showRectangle(Rectangle rectangle) {
        ConsoleUtils.printToConsole("Full info from rectangle - " + rectangle);

        ConsoleUtils.printToConsole("X = " + rectangle.getX());
        ConsoleUtils.printToConsole("Y = " + rectangle.getY());
        ConsoleUtils.printToConsole("width = " + rectangle.getWidth());
        ConsoleUtils.printToConsole("height = " + rectangle.getHeight());
    }

}

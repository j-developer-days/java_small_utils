package com.jdev.mouse_info;

import com.jdev.utils.ConsoleUtil;

import java.awt.*;
import java.awt.image.ColorModel;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MouseInfoDemo {

    public static void main(String[] args) throws InterruptedException {
//        showMouseInfo();
//        ConsoleUtil.printDelimiter('!', 250);

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
            System.out.println("date - " + LocalDateTime.now() + "\tX = " + location.getX() + "\tY = " + location.getY());

            if(++count > 5){
                System.out.println("Do you want to continue? \n If you want to continue please press 'c' or if you want to stop press 's', thanks!");
                count = 1;
                isSleep = false;
                if(scannerFromConsole.nextLine().equals("s")){
                    break;
                }
            }
        }

        System.out.println("Stop!");
    }

    private static void showCoordinate() throws InterruptedException {
        for (;true;){
            Thread.sleep(5_000);
            Point location = MouseInfo.getPointerInfo().getLocation();
            System.out.println("date - " + LocalDateTime.now() + "\tX = " + location.getX() + "\tY = " + location.getY());
        }
    }

    private static void showMouseInfo() {
        System.out.println("get number of button on the mouse - " + MouseInfo.getNumberOfButtons());
        ConsoleUtil.printDelimiter('*', 100);

        Point pointLocation = MouseInfo.getPointerInfo().getLocation();
        System.out.println("X = " + pointLocation.getX() + "\tY = " + pointLocation.getY());
        ConsoleUtil.printDelimiter('-');

        System.out.println("get mouse info device configuration count - " + MouseInfo.getPointerInfo().getDevice().getConfigurations().length);
        ConsoleUtil.printDelimiter('$');

        GraphicsConfiguration defaultConfiguration = MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration();
        Rectangle bounds = defaultConfiguration.getBounds();
        showRectangle(bounds);
        ConsoleUtil.printDelimiter('-', 50);

        ColorModel colorModel = defaultConfiguration.getColorModel();
        System.out.println("colorModel - " + colorModel);
        final var pixel = 100;
        System.out.println("red - " + colorModel.getRed(pixel) + "\tgreen - " + colorModel.getGreen(pixel) + "\tblue - " + colorModel.getBlue(pixel));
        ConsoleUtil.printDelimiter('|', 50);

        System.out.println("color model num components = " + colorModel.getNumComponents());
        System.out.print("color model transparency = " + colorModel.getTransparency() + "\t");
        switch (colorModel.getTransparency()){
            case Transparency.OPAQUE: {
                System.out.println("OPAQUE");
                break;
            }
            case Transparency.BITMASK: {
                System.out.println("BITMASK");
                break;
            }case Transparency.TRANSLUCENT: {
                System.out.println("TRANSLUCENT");
                break;
            }
        }

    }

    private static void showRectangle(Rectangle rectangle) {
        System.out.println("Full info from rectangle - " + rectangle);

        System.out.println("X = " + rectangle.getX());
        System.out.println("Y = " + rectangle.getY());
        System.out.println("width = " + rectangle.getWidth());
        System.out.println("height = " + rectangle.getHeight());
    }

}

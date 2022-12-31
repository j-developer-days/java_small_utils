package com.jdev.mouse_info;


import com.jdev.console.ConsoleUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MultiResolutionImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MouseInformation {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws AWTException, InterruptedException, IOException {
//        getToolkitInfo();

//        robotInfo();

        mouseAutoMoveByTimer();
    }

    private static void mouseAutoMoveByTimer() throws AWTException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Robot robot = new Robot();
        while (true){
           int x =  RANDOM.nextInt((int)screenSize.getWidth());
           int y =  RANDOM.nextInt((int)screenSize.getHeight());

            ConsoleUtils.printToConsole("X = " + x + "\ty = " + y);
            robot.mouseMove(x, y);
            Color pixelColor = robot.getPixelColor(x, y);
            ConsoleUtils.printToConsole("color is - " + pixelColor);
            robot.delay(4_000);
            ConsoleUtils.printDelimiter('$');

        }
    }

    private static void robotInfo() throws AWTException, InterruptedException, IOException {
        showCurrentMouseLocation();
        ConsoleUtils.printDelimiter('#');

        Robot robot = new Robot();
        final var x = 1_000;
        final var y = 250;
//        robot.mouseMove(x, y);

        showCurrentMouseLocation();
        ConsoleUtils.printDelimiter('#');

        Color pixelColor = robot.getPixelColor(x, y);
        ConsoleUtils.printToConsole("color info - " + pixelColor);

//        clickMouseButton(InputEvent.BUTTON1_DOWN_MASK, robot, "Button1 - left button");
//        clickMouseButton(InputEvent.BUTTON2_DOWN_MASK, robot, "Button2 - wheel scroll middle button");
//        clickMouseButton(InputEvent.BUTTON3_DOWN_MASK, robot, "Button3 - right button");

        robot.delay(3_000);
//        robot.mouseWheel(150);

//        keyPress(KeyEvent.VK_W, robot);

        screenCapture(robot, new Rectangle(0, 0, 1000, 500), "image_w1000_h500", "jpg");
        screenCapture(robot, MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration().getBounds(), "image_full", "jpg");

        MultiResolutionImage multiResolutionScreenCapture = robot.createMultiResolutionScreenCapture(MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration().getBounds());
        multiResolutionScreenCapture.getResolutionVariants().forEach(image -> ConsoleUtils.printToConsole(image));

    }

    private static void screenCapture(Robot robot, Rectangle rectangle, String name, String fileFormat) throws IOException {
        BufferedImage screenCapture = robot.createScreenCapture(rectangle);
        File file = new File(name + "." + fileFormat);
        ImageIO.write(screenCapture, fileFormat, file);
    }

    private static void keyPress(int keyCode, Robot robot){
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

    private static void clickMouseButton(int buttonNumber, Robot robot, String buttonName) throws InterruptedException {
        Thread.sleep(3_000);
        robot.mousePress(buttonNumber);
        robot.mouseRelease(buttonNumber);
        ConsoleUtils.printToConsole("Clicked - " + buttonName);
    }

    private static void showCurrentMouseLocation() {
        Point location = MouseInfo.getPointerInfo().getLocation();
        ConsoleUtils.printToConsole("X = " + location.getX() + "\tY = " + location.getY());
    }

    private static void getToolkitInfo() {
        ConsoleUtils.printToConsole("screen resolution is - " + Toolkit.getDefaultToolkit().getScreenResolution());
        ConsoleUtils.printDelimiter('_', 200);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ConsoleUtils.printToConsole("H = " + screenSize.getHeight());
        ConsoleUtils.printToConsole("W = " + screenSize.getWidth());
        ConsoleUtils.printToConsole("Full info - " + screenSize.getSize());
    }

}

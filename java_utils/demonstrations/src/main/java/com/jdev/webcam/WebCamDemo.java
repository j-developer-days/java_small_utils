package com.jdev.webcam;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.jdev.util.DateUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WebCamDemo {

    public static void main(String[] args) throws InterruptedException, IOException {
//        webCam(7);
        webCamTakePhoto(2, 2);
    }

    private static void webCamTakePhoto(int count, int timeOutInSecond) throws InterruptedException, IOException {
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();
        System.out.println("webcam - " + webcam.isOpen());

        Dimension resolution = webcam.getDevice().getResolution();
        System.out.println("resolution - " + resolution.getHeight() + ", " + resolution.getWidth());

        final String fileFormat = "png";
        for (var i = 0; i < count; i++) {
            File file = new File("ImageFromWebCam_" + DateUtils.getLocalDateTimeNowAsText() + "." + fileFormat);
            ImageIO.write(webcam.getImage(), fileFormat, file);
            System.out.println("#" + i + " - " + file.getAbsolutePath());
            Thread.sleep(TimeUnit.SECONDS.toMillis(timeOutInSecond));
        }

        webcam.close();
    }

    private static void webCam(int seconds) throws InterruptedException {
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());

        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        panel.setToolTipText("Tooltip");
        panel.setForeground(Color.BLUE);

        JFrame window = new JFrame("Test webcam panel");
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);

        Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        webcam.close();
        window.dispose();
        System.exit(0);
    }

}

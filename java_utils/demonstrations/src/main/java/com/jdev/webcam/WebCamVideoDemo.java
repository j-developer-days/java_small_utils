package com.jdev.webcam;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.jdev.console.ConsoleUtils;
import io.humble.video.*;
import io.humble.video.awt.MediaPictureConverter;
import io.humble.video.awt.MediaPictureConverterFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


/*https://github.com/sarxos/webcam-capture/tree/fdfcaea80c8f50eeb8f7c24475ff85a3c1fdcb32/webcam-capture-examples*/
public class WebCamVideoDemo {

    public static void main(String[] args) throws IOException, InterruptedException, AWTException {
        final int duration = 10; // how long in seconds
        final int snaps = 5; // 5 snaps per second
        final String codecName = null; // null => guess from file name
        final String formatName = null; // null => guess from file name
        final String filename = "filename.mp4";

        recordScreen(filename, formatName, codecName, duration, snaps);
    }

    /**
     * Records the screen
     */
    private static void recordScreen(String filename, String formatName, String codecName, int duration, int snapsPerSecond) throws InterruptedException, IOException {
        /**
         * Set up the AWT infrastructure to take screenshots of the desktop.
         */

        final Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());

        final Rectangle size = new Rectangle(webcam.getViewSize());
        final Rational framerate = Rational.make(1, snapsPerSecond);

        /** First we create a muxer using the passed in filename and formatName if given. */
        final Muxer muxer = Muxer.make(filename, null, formatName);

        /**
         * Now, we need to decide what type of codec to use to encode video. Muxers have limited
         * sets of codecs they can use. We're going to pick the first one that works, or if the user
         * supplied a codec name, we're going to force-fit that in instead.
         */
        final MuxerFormat format = muxer.getFormat();
        final Codec codec;
        if (codecName == null) {
            codec = Codec.findEncodingCodec(format.getDefaultVideoCodecId());
        } else {
            codec = Codec.findEncodingCodecByName(codecName);
        }

        /**
         * Now that we know what codec, we need to create an encoder
         */
        Encoder encoder = Encoder.make(codec);

        /**
         * Video encoders need to know at a minimum: width height pixel format Some also need to
         * know frame-rate (older codecs that had a fixed rate at which video files could be written
         * needed this). There are many other options you can set on an encoder, but we're going to
         * keep it simpler here.
         */
        encoder.setWidth(size.width);
        encoder.setHeight(size.height);
        // We are going to use 420P as the format because that's what most video formats these days
        // use
        final PixelFormat.Type pixelformat = PixelFormat.Type.PIX_FMT_YUV420P;
        encoder.setPixelFormat(pixelformat);
        encoder.setTimeBase(framerate);

        /**
         * An annoynace of some formats is that they need global (rather than per-stream) headers,
         * and in that case you have to tell the encoder. And since Encoders are decoupled from
         * Muxers, there is no easy way to know this beyond
         */
        if (format.getFlag(MuxerFormat.Flag.GLOBAL_HEADER))
            encoder.setFlag(Encoder.Flag.FLAG_GLOBAL_HEADER, true);

        /** Open the encoder. */
        encoder.open(null, null);

        /** Add this stream to the muxer. */
        muxer.addNewStream(encoder);

        /** And open the muxer for business. */
        muxer.open(null, null);

        /**
         * Next, we need to make sure we have the right MediaPicture format objects to encode data
         * with. Java (and most on-screen graphics programs) use some variant of Red-Green-Blue
         * image encoding (a.k.a. RGB or BGR). Most video codecs use some variant of YCrCb
         * formatting. So we're going to have to convert. To do that, we'll introduce a
         * MediaPictureConverter object later. object.
         */
        MediaPictureConverter converter = null;
        final MediaPicture picture = MediaPicture
                .make(
                        encoder.getWidth(),
                        encoder.getHeight(),
                        pixelformat);
        picture.setTimeBase(framerate);

        /**
         * Open webcam so we can capture video feed.
         */

        webcam.open();

        /**
         * Now begin our main loop of taking screen snaps. We're going to encode and then write out
         * any resulting packets.
         */
        final MediaPacket packet = MediaPacket.make();
        for (int i = 0; i < duration / framerate.getDouble(); i++) {

            /**
             * Make the screen capture && convert image to TYPE_3BYTE_BGR
             */
            final BufferedImage image = webcam.getImage();
            final BufferedImage frame = convertToType(image, BufferedImage.TYPE_3BYTE_BGR);

            ConsoleUtils.printToConsole("Record frame " + frame);

            /**
             * This is LIKELY not in YUV420P format, so we're going to convert it using some handy
             * utilities.
             */
            if (converter == null) {
                converter = MediaPictureConverterFactory.createConverter(frame, picture);
            }
            converter.toPicture(picture, frame, i);

            do {
                encoder.encode(packet, picture);
                if (packet.isComplete()) {
                    muxer.write(packet, false);
                }
            } while (packet.isComplete());

            /** now we'll sleep until it's time to take the next snapshot. */
            Thread.sleep((long) (1000 * framerate.getDouble()));
        }

        /**
         * Encoders, like decoders, sometimes cache pictures so it can do the right key-frame
         * optimizations. So, they need to be flushed as well. As with the decoders, the convention
         * is to pass in a null input until the output is not complete.
         */
        do {
            encoder.encode(packet, null);
            if (packet.isComplete()) {
                muxer.write(packet, false);
            }
        } while (packet.isComplete());

        /**
         * Finally, let's clean up after ourselves.
         */

        webcam.close();
        muxer.close();
    }

    public static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
        BufferedImage image;

        // if the source image is already the target type, return the source image

        if (sourceImage.getType() == targetType)
            image = sourceImage;

            // otherwise create a new image of the target type and draw the new
            // image

        else {
            image = new BufferedImage(
                    sourceImage.getWidth(),
                    sourceImage.getHeight(), targetType);
            image.getGraphics().drawImage(sourceImage, 0, 0, null);
        }

        return image;
    }

}

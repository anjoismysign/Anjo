package me.anjoismysign.anjo.libraries;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author anjoismysign
 */
public class ResourceLib {

    /**
     * Gets a resource from the resources folder as InputStream
     *
     * @param fileName The name of the file
     * @return The InputStream of the file. null if not found.
     */
    public InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            System.out.println("File not found");
        }
        return inputStream;

    }

    /**
     * Gets an image from the resources folder
     *
     * @param fileName The name of the file
     * @return The BufferedImage of the file. null if not found.
     */
    public BufferedImage getImageFromResourceAsStream(String fileName) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getFileFromResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}

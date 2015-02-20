package org.ronsmits.plantuml;

import net.sourceforge.plantuml.SourceStringReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ron on 20-2-15.
 */
public abstract class Utils {
    /**
     * Util method to create an image.
     *
     * @param text
     * @return
     */
    public static BufferedImage createImage(String text) {
        BufferedImage image = null;
        ByteArrayOutputStream bous = new ByteArrayOutputStream();
        SourceStringReader reader = new SourceStringReader(text);
        try {
            String desc = reader.generateImage(bous);
            System.out.println(desc);
            byte[] data = bous.toByteArray();

            InputStream in = new ByteArrayInputStream(data);
            image = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}

package org.ronsmits.plantuml;

import net.sourceforge.plantuml.SourceStringReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ron on 17-2-15.
 */
public class Drawing extends JPanel implements TextListener {

    private BufferedImage bufferedImage;

    Drawing() {
        Dimension minimumSize = new Dimension(640, 480);
        setMinimumSize(minimumSize);
        setSize(minimumSize);
    }

    @Override
    public void textUpdated(String text) {
        ByteArrayOutputStream bous = new ByteArrayOutputStream();
        SourceStringReader reader = new SourceStringReader(text);
        try {
            String desc = reader.generateImage(bous);
            System.out.println(desc);
            byte[] data = bous.toByteArray();

            InputStream in = new ByteArrayInputStream(data);
            bufferedImage = ImageIO.read(in);
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (bufferedImage != null) {
            graphics.drawImage(bufferedImage, 0, 0, null);
        }

    }
}

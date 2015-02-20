package org.ronsmits.plantuml;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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
        bufferedImage = Utils.createImage(text);
        repaint();
    }


    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (bufferedImage != null) {
            graphics.drawImage(bufferedImage, 0, 0, null);
        }

    }
}

package org.heatmapvis.ui;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel(String resourcePath) {
        loadImage(resourcePath);
    }

    private void loadImage(String resourcePath) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.err.println("Resource not found: " + resourcePath);
                return;
            }
            image = ImageIO.read(is);
            setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

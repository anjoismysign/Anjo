package me.anjoismysign.anjo.swing;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

class ScalingImagePanel extends JPanel {
    private final Image image;

    public ScalingImagePanel(@NotNull Image image) {
        this.image = image;
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            int w = getWidth();
            int h = getHeight();
            double aspectRatio = (double) image.getWidth(this) / image.getHeight(this);
            int newWidth, newHeight;
            if (w / h > aspectRatio) {
                newHeight = h;
                newWidth = (int) (h * aspectRatio);
            } else {
                newWidth = w;
                newHeight = (int) (w / aspectRatio);
            }
            int x = (w - newWidth) / 2;
            int y = (h - newHeight) / 2;
            g2d.drawImage(image, x, y, newWidth, newHeight, this);
            g2d.dispose();
        }
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(10, 10); // Allow panel to be very small
    }
}

package com.fun.engineercompass;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUI extends JFrame {

     private int tileSize = 250;
    private int imageSize = (int) (tileSize * 0.85);
    private int middlePoint = tileSize / 2;

    private JLabel backgroundLabel;
    private ImageIcon compassIcon;

    private int lastX, lastY;
    private double rotationAngle = 0;
    private double sensitivity = 0.01; // Érzékenységi tényező

    public GUI() {
        super("Engineer Compass");

        setLayout(new FlowLayout());
        setSize(tileSize, tileSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setAlwaysOnTop(true);

        compassIcon = new ImageIcon(GUI.class.getResource("/testCompass.png"));

        Image scaledImage = compassIcon.getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        backgroundLabel = new JLabel("", scaledIcon, JLabel.CENTER);
        backgroundLabel.setOpaque(true);
        backgroundLabel.setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int currentX = e.getX();
                int currentY = e.getY();

                // Számítsd ki a forgatási szöget az egér mozgása alapján
                double deltaX = currentX - lastX;
                double deltaY = currentY - lastY;
                rotationAngle += sensitivity * Math.atan2(deltaY, deltaX);

                // Forgasd el a képet
                rotateImage(rotationAngle);

                // Frissítsd az utolsó pozíciót
                lastX = currentX;
                lastY = currentY;
            }
        });

        setContentPane(backgroundLabel);
    }

    private void rotateImage(double angle) {
        // Számítsd ki az új forgatott képet és állítsd be a JLabel-t
        ImageIcon rotatedIcon = rotateIcon(compassIcon, angle);
        backgroundLabel.setIcon(rotatedIcon);
    }

    private ImageIcon rotateIcon(ImageIcon icon, double angle) {
        // Számítsd ki az új forgatott ikont
        Image image = icon.getImage();
        Image rotatedImage = rotateImage(image, angle);
        return new ImageIcon(rotatedImage);
    }

    private Image rotateImage(Image image, double angle) {
        // Számítsd ki az új forgatott képet
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.rotate(angle, image.getWidth(null) / 2, image.getHeight(null) / 2);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bufferedImage;
    }
}
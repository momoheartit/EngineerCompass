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
import javax.swing.SwingUtilities;

public class GUI extends JFrame {

    private int tileSize = 300;
    private int imageSize = (int) (tileSize * 0.90);

    private JLabel backgroundLabel;
    private ImageIcon compassIcon;

    private int lastX, lastY;
    private double rotationAngle = 0;
    private double sensitivity = 0.02; 

    public GUI() {
        super("Engineer Compass");

        setLayout(new FlowLayout());
        setSize(tileSize, tileSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setAlwaysOnTop(true);
        setResizable(false);

        compassIcon = new ImageIcon(GUI.class.getResource("compass.png"));

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

                ImageIcon rotatedIcon = rotateIcon(compassIcon, rotationAngle);
                Image scaledRotatedImage = rotatedIcon.getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
                backgroundLabel.setIcon(new ImageIcon(scaledRotatedImage));

                lastX = currentX;
                lastY = currentY;
            }
        });

        setContentPane(backgroundLabel);
    }

    private ImageIcon rotateIcon(ImageIcon icon, double angle) {
        // Számítsd ki az új forgatott ikont
        Image image = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.rotate(angle, image.getWidth(null) / 2, image.getHeight(null) / 2);
        g.drawImage(image, 0, 0, null);
                
        g.dispose();
        return new ImageIcon(bufferedImage);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }
}

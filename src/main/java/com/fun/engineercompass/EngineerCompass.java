package com.fun.engineercompass;

import javax.swing.SwingUtilities;

public class EngineerCompass {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}

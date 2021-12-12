package org.kelompok3;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas{
    static private final Dimension screenSize = calculateSize();

    public Window() {
        var frame = new JFrame();
        frame.setMinimumSize(screenSize);
        frame.setMaximumSize(screenSize);
        frame.setPreferredSize(screenSize);
        frame.setResizable(false);
        frame.pack();

        frame.setTitle("Congklak me.akhsaul.congklak.Game");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Contract(" -> new")
    static @NotNull Dimension calculateSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        double fraction = (20.0 / 100.0) * height;
        height -= (int) fraction;
        int width = height + 200;
        return new Dimension(width, height);
    }
}

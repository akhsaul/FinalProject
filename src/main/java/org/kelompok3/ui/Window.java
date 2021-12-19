package org.kelompok3.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Window extends JFrame {

    public Window(@NotNull Dimension size, JPanel content) throws IOException {
        super(size.width + "x" + size.height);

        setLayout(new BorderLayout());
        var img = ImageIO.read(
                Objects.requireNonNull(this.getClass().getClassLoader().getResource("p8.png"))
        );
        setContentPane(new JLabel(new ImageIcon(img)));
        //var panel = new JPanel();
        //var contentPane = getContentPane();
        //contentPane.setLayout(new BorderLayout());
        //contentPane.setLayout(new CardLayout());
        //contentPane.add(panel, BorderLayout.CENTER);
        //contentPane.add(panel, "card1");
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //game.start();
    }

    public static void main(String[] args) throws IOException {
        FlatLaf.setup(new FlatDarculaLaf());
        JFrame.setDefaultLookAndFeelDecorated(true);
        //new Window(new Dimension(800, 600), new Launcher());
    }
}

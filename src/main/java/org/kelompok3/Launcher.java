package org.kelompok3;

import com.formdev.flatlaf.FlatDarculaLaf;
import net.miginfocom.swing.MigLayout;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class Launcher extends JFrame {
    Launcher() {
        initComponents();

        // TODO
        //  addListener in button to launch Server
        //SpringApplication.run(ServerApp.class);

    }

    @Contract(value = " -> new", pure = true)
    static @NotNull LookAndFeel theme(){
        return new FlatDarculaLaf();
    }

    private void initComponents() {
        FlatDarculaLaf.setup();
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
                "insets 0,hidemode 3,gap 0 0",
                // columns
                "[fill]" +
                        "[fill]",
                // rows
                "[fill]" +
                        "[fill]" +
                        "[fill]"));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Launcher();
    }
}
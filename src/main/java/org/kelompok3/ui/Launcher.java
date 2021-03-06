package org.kelompok3.ui;

import org.kelompok3.Utils;
import org.kelompok3.server.ServerApp;
import org.springframework.boot.SpringApplication;

import javax.swing.*;
import java.awt.*;

public class Launcher extends JFrame {
    public Launcher() {
        initComponents();
        initListener();
    }

    public static void main(String[] args) {
        Utils.initTheme();
        new Launcher();
    }

    private void initListener() {
        desktop.addActionListener(e -> {
            new MainGUI();
            this.dispose();
        });
        web.addActionListener(e -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    SpringApplication.run(ServerApp.class);
                    var url = "http://127.0.0.1:8080/congklak";
                    //Desktop.getDesktop().browse(new URI(url));
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url).waitFor();
                    this.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void initComponents() {
        label1 = new JLabel();
        web = new JButton();
        desktop = new JButton();

        //======== this ========
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("Peluncur Aplikasi");
        label1.setFont(label1.getFont().deriveFont(18f));

        //---- web ----
        web.setText("Web");

        //---- desktop ----
        desktop.setText("Desktop");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(web, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(desktop, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addGap(95, 95, 95))
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(label1)
                                .addGap(140, 140, 140))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(label1)
                                .addGap(60, 60, 60)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(web, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(desktop, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(52, Short.MAX_VALUE))
        );
        setSize(400, 300);
        setLocationRelativeTo(null);

        setTitle("Peluncur Aplikasi");
        setIconImage(Utils.getImgRes("assets/icon.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private JLabel label1;
    private JButton web;
    private JButton desktop;
}

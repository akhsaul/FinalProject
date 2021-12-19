package org.kelompok3.ui;

import org.kelompok3.Utils;
import org.kelompok3.database.DBConnector;

import javax.swing.*;
import java.awt.*;
/*
 * Created by JFormDesigner on Mon Dec 13 18:02:08 ICT 2021
 */


/**
 * @author georgeriv
 */
public class MainGUI extends JFrame {
    public MainGUI() {
        initComponents();
        initListener();
    }
    
    private void initListener(){
        infoBtn.addActionListener(e -> {
            new Info(this);
        });
        settingBtn.addActionListener(e -> {
            new Setting();
            this.dispose();
        });
        playBtn.addActionListener(e->{
            new Suit();
            this.dispose();
        });
        scoreBtn.addActionListener(e->{
            new Score();
            this.dispose();
        });
    }


    private void initComponents() {
        setContentPane(Utils.getBackgroundImg("background.png"));
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - georgeriv
        label = new JLabel();
        playBtn = new JButton();
        infoBtn = new JButton();
        scoreBtn = new JButton();
        settingBtn = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        Container contentPane = getContentPane();

        //---- label ----
        label.setText("GAME CONGKLAK");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Lexend", label.getFont().getStyle() | Font.BOLD, 40));
        label.setForeground(Color.white);

        //---- playBtn ----
        playBtn.setText("Mainkan");

        //---- infoBtn ----
        infoBtn.setIcon(Utils.getIcon("info.png"));
        infoBtn.setBorder(null);
        infoBtn.setBorderPainted(false);
        infoBtn.setContentAreaFilled(false);

        //---- scoreBtn ----
        scoreBtn.setText("Lihat Skor");

        //---- settingBtn ----
        settingBtn.setIcon(Utils.getIcon("setting.png"));
        settingBtn.setBorderPainted(false);
        settingBtn.setBorder(null);
        settingBtn.setContentAreaFilled(false);

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(infoBtn)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(settingBtn)
                                .addGap(40, 40, 40))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(215, 215, 215)
                                .addComponent(label)
                                .addContainerGap(214, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(playBtn, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(scoreBtn, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
                                .addGap(329, 329, 329))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(infoBtn)
                                        .addComponent(settingBtn))
                                .addGap(40, 40, 40)
                                .addComponent(label)
                                .addGap(84, 84, 84)
                                .addComponent(playBtn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(73, 73, 73)
                                .addComponent(scoreBtn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(120, Short.MAX_VALUE))
        );
        setSize(800, 600);
        setLocationRelativeTo(null);

        setTitle("Game Congklak");
        setIconImage(Utils.getImgRes("icon.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - georgeriv
    private JLabel label;
    private JButton playBtn;
    private JButton infoBtn;
    private JButton scoreBtn;
    private JButton settingBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

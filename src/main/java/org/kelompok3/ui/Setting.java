package org.kelompok3.ui;

import org.kelompok3.Utils;
import org.kelompok3.core.State;
import org.kelompok3.database.DBConnector;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;
/*
 * Created by JFormDesigner on Thu Dec 16 14:23:17 ICT 2021
 */


/**
 * @author georgeriv
 */
public class Setting extends JFrame {
    public Setting() {
        initComponents();
        initListener();
    }

    public static void prepareAll(){
        try {
            // check if the database has data setting or not
            var result = DBConnector.getSetting();
            // will return false if database does not have data setting
            if (result.next()) {
                State.setPlayerName(result.getString("player_name"));
                State.setEnableBgm(result.getBoolean("bgm_enabled"));
                State.setEnableSfx(result.getBoolean("bgm_enabled"));
            } else {
                // insert data setting for the first time
                DBConnector.initSetting();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // always close
            DBConnector.closeStatement();
        }
    }

    private void initListener() {
        backBtn.addActionListener(e -> {
            if (!State.getPlayerName().equals(name.getText())) {
                Utils.warnMessage(this, "Nama pemain mengalami perubahan.\nTekan tombol SIMPAN terlebih dahulu!");
            } else {
                new MainGUI();
                this.dispose();
            }
        });
        save.addActionListener(e -> {
            final var pattern = Pattern.compile("[^a-zA-Z0-9_]", Pattern.MULTILINE);
            final var txt = name.getText().trim();
            final var matcher = pattern.matcher(txt);
            final var wrongTxt = new StringBuilder();
            while (matcher.find()) {
                wrongTxt.append(matcher.group(0));
            }

            // verify before saving
            if (!wrongTxt.isEmpty()) {
                Utils.warnMessage(this, "Nama pemain tidak benar !\nNama pemain tidak boleh mengandung '" + wrongTxt + "'");
            } else {
                if (!txt.isBlank() && !txt.isEmpty() && (txt.length() < 5 || txt.length() > 9)) {
                    Utils.warnMessage(this, "Nama pemain minimal 5 karakter dan maksimal 9 karakter");
                } else {
                    var oldName = State.getPlayerName();
                    State.setPlayerName(txt);
                    DBConnector.saveSetting(oldName, State.getPlayerName(), State.isEnableBgm(), State.isEnableSfx());
                }
            }
        });
        bgm.addActionListener(e -> State.setEnableBgm(bgm.isSelected()));
        sfx.addActionListener(e -> State.setEnableSfx(sfx.isSelected()));
    }

    private void initComponents() {
        setContentPane(Utils.getBackgroundImg("background.png"));
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - georgeriv
        label1 = new JLabel();
        bgm = new JCheckBox();
        sfx = new JCheckBox();
        name = new JTextField();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        backBtn = new JButton();
        save = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("PENGATURAN");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setFont(new Font("Lexend", label1.getFont().getStyle() | Font.BOLD, 40));
        label1.setForeground(Color.white);

        //---- bgm ----
        bgm.setFont(new Font("Comic Sans MS", bgm.getFont().getStyle(), 15));
        bgm.setSelected(State.isEnableBgm());

        //---- sfx ----
        sfx.setFont(new Font("Comic Sans MS", sfx.getFont().getStyle(), 15));
        sfx.setSelected(State.isEnableSfx());

        //---- name ----
        name.setText(State.getPlayerName());
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setFont(new Font("Comic Sans MS", name.getFont().getStyle(), 15));

        //---- label2 ----
        label2.setText("Nama Pemain :");
        label2.setFont(new Font("Comic Sans MS", label2.getFont().getStyle(), 15));
        label2.setHorizontalAlignment(SwingConstants.LEFT);
        label2.setForeground(Color.white);

        //---- label3 ----
        label3.setText("Aktifkan Bgm ?");
        label3.setFont(new Font("Comic Sans MS", label3.getFont().getStyle(), 15));
        label3.setHorizontalAlignment(SwingConstants.LEFT);
        label3.setForeground(Color.white);

        //---- label4 ----
        label4.setText("Aktifkan Sfx ?");
        label4.setFont(new Font("Comic Sans MS", label4.getFont().getStyle(), 15));
        label4.setHorizontalAlignment(SwingConstants.LEFT);
        label4.setForeground(Color.white);

        //---- backBtn ----
        backBtn.setText("KEMBALI");

        //---- save ----
        save.setText("SIMPAN");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(265, 265, 265)
                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label4, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label3, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
                                                .addGap(30, 30, 30)
                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                        .addComponent(sfx)
                                                        .addComponent(bgm)
                                                        .addComponent(name, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addComponent(backBtn)
                                                .addGap(138, 138, 138)
                                                .addComponent(label1))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(365, 365, 365)
                                                .addComponent(save, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addComponent(backBtn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(120, 120, 120)
                                                .addComponent(label1)))
                                .addGap(80, 80, 80)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label2)
                                        .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(bgm)
                                        .addComponent(label3))
                                .addGap(40, 40, 40)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(sfx)
                                        .addComponent(label4))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                                .addComponent(save, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46))
        );
        setSize(800, 600);
        setLocationRelativeTo(null);

        setTitle("Game Congklak - Pengaturan");
        setIconImage(Utils.getImgRes("icon.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - georgeriv
    private JLabel label1;
    private JCheckBox bgm;
    private JCheckBox sfx;
    private JTextField name;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JButton backBtn;
    private JButton save;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

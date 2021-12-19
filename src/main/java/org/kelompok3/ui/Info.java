package org.kelompok3.ui;

import org.kelompok3.Utils;

import java.awt.*;
import java.awt.Window;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Fri Dec 17 00:58:55 ICT 2021
 */


/**
 * @author georgeriv
 */
public class Info extends JDialog {
    public Info(Window owner) {
        super(owner);
        initComponents();
        txtTutorial.setCaretPosition(0);
        txtRules.setCaretPosition(0);
        backBtn.addActionListener(e -> this.dispose());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - georgeriv
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        tabbedPane1 = new JTabbedPane();
        scrollPane1 = new JScrollPane();
        txtTutorial = new JTextArea();
        scrollPane2 = new JScrollPane();
        txtRules = new JTextArea();
        panel1 = new JPanel();
        label6 = new JLabel();
        textArea1 = new JTextArea();
        label5 = new JLabel();
        label4 = new JLabel();
        label3 = new JLabel();
        label2 = new JLabel();
        label1 = new JLabel();
        buttonBar = new JPanel();
        backBtn = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {

                //======== tabbedPane1 ========
                {

                    //======== scrollPane1 ========
                    {

                        //---- txtTutorial ----
                        txtTutorial.setText(Utils.readFile("tutorial.txt"));
                        txtTutorial.setEditable(false);
                        txtTutorial.setLineWrap(true);
                        txtTutorial.setWrapStyleWord(true);
                        scrollPane1.setViewportView(txtTutorial);
                    }
                    tabbedPane1.addTab("Cara Bermain", scrollPane1);

                    //======== scrollPane2 ========
                    {

                        //---- txtRules ----
                        txtRules.setText(Utils.readFile("rules.txt"));
                        txtRules.setEditable(false);
                        txtRules.setLineWrap(true);
                        txtRules.setWrapStyleWord(true);
                        scrollPane2.setViewportView(txtRules);
                    }
                    tabbedPane1.addTab("Aturan Bermain", scrollPane2);

                    //======== panel1 ========
                    {

                        //---- label6 ----
                        label6.setText("Syamsul Maarif");
                        label6.setHorizontalAlignment(SwingConstants.CENTER);

                        //---- textArea1 ----
                        textArea1.setText("Aplikasi ini memakai beberapa library, antara lain:\n- flatlaf themes\n- vlcj\n- jackson\n- mysql\n- spring boot");
                        textArea1.setEditable(false);
                        textArea1.setBorder(null);

                        //---- label5 ----
                        label5.setText("Ikhsan Maulana");
                        label5.setHorizontalAlignment(SwingConstants.CENTER);

                        //---- label4 ----
                        label4.setText("Nursela Salsabilla Basuni");
                        label4.setHorizontalAlignment(SwingConstants.CENTER);

                        //---- label3 ----
                        label3.setText("Andi Syafei");
                        label3.setHorizontalAlignment(SwingConstants.CENTER);

                        //---- label2 ----
                        label2.setText("Dibuat oleh");
                        label2.setHorizontalAlignment(SwingConstants.CENTER);

                        //---- label1 ----
                        label1.setText("Permainan Congklak");
                        label1.setHorizontalAlignment(SwingConstants.CENTER);

                        GroupLayout panel1Layout = new GroupLayout(panel1);
                        panel1.setLayout(panel1Layout);
                        panel1Layout.setHorizontalGroup(
                                panel1Layout.createParallelGroup()
                                        .addGroup(panel1Layout.createSequentialGroup()
                                                .addGap(156, 156, 156)
                                                .addComponent(textArea1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                                .addContainerGap(222, Short.MAX_VALUE)
                                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(label4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(label5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(label6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(label3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addGap(223, 223, 223))
                        );
                        panel1Layout.setVerticalGroup(
                                panel1Layout.createParallelGroup()
                                        .addGroup(panel1Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(label1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(label2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(label3)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(label4)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(label5)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(label6)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(textArea1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        );
                    }
                    tabbedPane1.addTab("Tentang", panel1);
                }

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                        contentPanelLayout.createParallelGroup()
                                .addComponent(tabbedPane1, GroupLayout.Alignment.TRAILING)
                );
                contentPanelLayout.setVerticalGroup(
                        contentPanelLayout.createParallelGroup()
                                .addComponent(tabbedPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                );
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //---- backBtn ----
                backBtn.setText("KEMBALI");
                buttonBar.add(backBtn, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setSize(600, 400);
        setLocationRelativeTo(null);

        setTitle("Game Congklak - Informasi");
        setIconImage(Utils.getImgRes("icon.png"));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - georgeriv
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JTabbedPane tabbedPane1;
    private JScrollPane scrollPane1;
    private JTextArea txtTutorial;
    private JScrollPane scrollPane2;
    private JTextArea txtRules;
    private JPanel panel1;
    private JLabel label6;
    private JTextArea textArea1;
    private JLabel label5;
    private JLabel label4;
    private JLabel label3;
    private JLabel label2;
    private JLabel label1;
    private JPanel buttonBar;
    private JButton backBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

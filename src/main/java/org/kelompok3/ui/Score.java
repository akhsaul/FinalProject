package org.kelompok3.ui;

import org.kelompok3.Utils;
import org.kelompok3.database.DBConnector;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/*
 * Created by JFormDesigner on Thu Dec 16 10:41:52 ICT 2021
 */


/**
 * @author georgeriv
 */
public class Score extends JFrame {
    public Score() {
        initComponents();
        initListener();
        initTable();
    }

    private void initTable() {
        try {
            var result = DBConnector.getScore();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            while (result.next()) {
                var name = result.getString("player_name");
                var score = result.getInt("score");
                var status = result.getString("status");
                Object[] row = {name, score, status};
                model.addRow(row);
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
            new MainGUI();
            this.dispose();
        });
    }

    private void initComponents() {
        setContentPane(Utils.getBackgroundImg("background.png"));
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - georgeriv
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        backBtn = new JButton();

        //======== this ========
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("SKOR");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setFont(new Font("Lexend", label1.getFont().getStyle() | Font.BOLD, 40));
        label1.setForeground(Color.white);

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.setModel(new DefaultTableModel(
                    new Object[][]{},
                    new String[]{"Nama Pemain", "Skor", "Status"}
            ) {
                final Class<?>[] columnTypes = new Class<?>[]{
                        String.class, Integer.class, String.class
                };
                final boolean[] columnEditable = new boolean[]{
                        false, false, false
                };

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return columnTypes[columnIndex];
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            {
                table1.setShowHorizontalLines(true);
                table1.setShowVerticalLines(true);
                table1.getTableHeader().setReorderingAllowed(false);
            }
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);

            var columns = table1.getColumnModel().getColumns();
            while (columns.hasMoreElements()) {
                var column = columns.nextElement();
                column.setResizable(false);
                column.setCellRenderer(centerRenderer);
            }
            scrollPane1.setViewportView(table1);
        }

        //---- backBtn ----
        backBtn.setText("KEMBALI");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(173, 173, 173)
                                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addComponent(backBtn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                .addGap(190, 190, 190)
                                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)))
                                .addGap(173, 173, 173))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(50, 50, 50)
                                                .addComponent(label1))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addComponent(backBtn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        setSize(800, 600);
        setLocationRelativeTo(null);

        setTitle("Game Congklak - Score");
        setIconImage(Utils.getImgRes("icon.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - georgeriv
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton backBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

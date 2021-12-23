package org.kelompok3.ui;

import org.jetbrains.annotations.NotNull;
import org.kelompok3.Utils;
import org.kelompok3.core.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/*
 * Created by JFormDesigner on Wed Dec 15 23:26:33 ICT 2021
 */


/**
 * @author georgeriv
 */
public class Suit extends JFrame {
    private static Hand computerHand = Hand.INIT;
    private static Hand playerHand = Hand.INIT;
    private static Worker task = null;

    public Suit() {
        initComponents();
        initListener();
        initWorker();
        task.doJob(1, false);
    }

    private void initWorker(){
        task = new Worker();
        task.addPropertyChangeListener(e -> {
            if ("progress".equals(e.getPropertyName())) {
                if (task.dispose) {
                    // prevent this method called twice
                    task.jobComplete();
                    new Board();
                    this.dispose();
                }
            }
        });
    }

    private void initPlayer() {
        List<LittleHole> cHole = new ArrayList<>();
        List<LittleHole> pHole = new ArrayList<>();
        var x = 0;
        for (int i = 7; i >= 1; i--) {
            cHole.add(new LittleHole("C-" + i));
            pHole.add(new LittleHole("P-" + i));
            x++;
        }

        var cBigHole = new BigHole("C-L");
        var pBigHole = new BigHole("P-L");

        State.setHumanPLayer(new Human(2, pHole, pBigHole));
        State.setComputerPlayer(new Computer(1, cHole, cBigHole));
    }

    private void checkWinner() {
        initPlayer();
        Integer winnerId = null;

        if (playerHand.equals(computerHand)) {
            task.stopJob();
            task = null;
            // the Thread will be suspended
            // until JOptionPane return value
            var result = Utils.infoMessage(this,
                    "Tangan anda dan tangan computer sama.\nSilahkan diulang");

            if (JOptionPane.YES_OPTION == result || JOptionPane.CLOSED_OPTION == result) {
                // re-init the Worker
                initWorker();
                // remove img because player hand same like computer hand
                player.setIcon(null);
                task.doJob(1, false);
            }
        } else if (playerHand.equals(Hand.KERTAS) && computerHand.equals(Hand.GUNTING)) {
            // gunting win
            //System.out.println(computerHand);
            winnerId = State.getComputerPlayer().id;
        } else if (playerHand.equals(Hand.GUNTING) && computerHand.equals(Hand.KERTAS)) {
            // gunting win
            //System.out.println(playerHand);
            winnerId = State.getHumanPLayer().id;
        } else if (playerHand.equals(Hand.BATU) && computerHand.equals(Hand.KERTAS)) {
            // kertas win
            //System.out.println(computerHand);
            winnerId = State.getComputerPlayer().id;
        } else if (playerHand.equals(Hand.KERTAS) && computerHand.equals(Hand.BATU)) {
            // kertas win
            //System.out.println(playerHand);
            winnerId = State.getHumanPLayer().id;
        } else if (playerHand.equals(Hand.GUNTING) && computerHand.equals(Hand.BATU)) {
            // batu win
            //System.out.println(computerHand);
            winnerId = State.getComputerPlayer().id;
        } else if (playerHand.equals(Hand.BATU) && computerHand.equals(Hand.GUNTING)) {
            // batu win
            //System.out.println(playerHand);
            winnerId = State.getHumanPLayer().id;
        }

        if (winnerId != null) {
            State.setCurrentTurn(winnerId);
            task.doJob(0, true);
        }
    }

    private enum Hand {
        INIT,
        BATU,
        KERTAS,
        GUNTING
    }

    private void initListener() {
        kertas.addActionListener(e -> {
            playerHand = Hand.KERTAS;
            player.setIcon(Utils.getIcon("kertas_kiri.png"));
            checkWinner();
        });
        gunting.addActionListener(e -> {
            playerHand = Hand.GUNTING;
            player.setIcon(Utils.getIcon("gunting_kiri.png"));
            checkWinner();
        });
        batu.addActionListener(e -> {
            playerHand = Hand.BATU;
            player.setIcon(Utils.getIcon("batu_kiri.png"));
            checkWinner();
        });
    }

    private void initComponents() {
        setContentPane(Utils.getBackgroundImg("background.png"));
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - georgeriv
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        computer = new JLabel();
        player = new JLabel();
        kertas = new JButton();
        gunting = new JButton();
        batu = new JButton();

        //======== this ========
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("SUIT");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setFont(new Font("Lexend", label1.getFont().getStyle() | Font.BOLD, 40));
        label1.setForeground(Color.white);

        //---- label2 ----
        label2.setText("vs");
        label2.setFont(new Font("Comic Sans MS", label2.getFont().getStyle(), 22));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setForeground(Color.white);

        //---- label3 ----
        label3.setText("Komputer");
        label3.setFont(new Font("Comic Sans MS", label3.getFont().getStyle(), 18));
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        label3.setForeground(Color.white);

        //---- label4 ----
        label4.setText(State.getPlayerName());
        label4.setFont(new Font("Comic Sans MS", label4.getFont().getStyle(), 18));
        label4.setHorizontalAlignment(SwingConstants.CENTER);
        label4.setForeground(Color.white);

        //---- computer ----
        computer.setIcon(Utils.getIcon("batu_kanan.png"));
        computer.setBorder(new LineBorder(Color.white, 2, true));

        //---- player ----
        player.setBorder(new LineBorder(Color.white, 2, true));

        //---- label5 ----
        label5.setText("Pilih salah satu");
        label5.setFont(new Font("Comic Sans MS", label5.getFont().getStyle(), 18));
        label5.setHorizontalAlignment(SwingConstants.CENTER);
        label5.setForeground(Color.white);

        //---- kertas ----
        kertas.setIcon(Utils.getIcon("kertas.png"));
        kertas.setContentAreaFilled(false);
        kertas.setBorder(new LineBorder(Color.white, 2, true));

        //---- gunting ----
        gunting.setIcon(Utils.getIcon("gunting.png"));
        gunting.setContentAreaFilled(false);
        gunting.setBorder(new LineBorder(Color.white, 2, true));

        //---- batu ----
        batu.setIcon(Utils.getIcon("batu.png"));
        batu.setContentAreaFilled(false);
        batu.setBorder(new LineBorder(Color.white, 2, true));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(346, 346, 346)
                                                .addComponent(label1))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(188, 188, 188)
                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addComponent(kertas)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(gunting)
                                                                .addGap(55, 55, 55)
                                                                .addComponent(batu))
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(100, 100, 100)
                                                                                .addComponent(label2))
                                                                        .addComponent(player, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
                                                                .addGap(100, 100, 100)
                                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                                        .addComponent(computer)
                                                                        .addComponent(label3, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))))))
                                .addGap(188, 188, 188))
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                .addGap(321, 321, 321)
                                .addComponent(label5, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
                                .addGap(321, 321, 321))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(label1)
                                .addGap(40, 40, 40)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label3)
                                        .addComponent(label2)
                                        .addComponent(label4))
                                .addGap(28, 28, 28)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(computer)
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(player, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                                                .addGap(45, 45, 45)
                                                .addComponent(label5)))
                                .addGap(25, 25, 25)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(batu)
                                                .addComponent(gunting))
                                        .addComponent(kertas))
                                .addContainerGap())
        );
        setSize(800, 600);
        setLocationRelativeTo(null);

        setTitle("Game Congklak - Suit");
        setIconImage(Utils.getImgRes("icon.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - georgeriv
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private static JLabel computer;
    private static JLabel player;
    private JButton batu;
    private JLabel label5;
    private JButton gunting;
    private JButton kertas;

    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private static class Worker extends SwingWorker<Integer, Icon> {
        private int x = 1;
        private boolean stop = false;
        private boolean sleep = false;
        boolean dispose = false;

        public void stopJob() {
            stop = true;
        }

        public void jobComplete() {
            stopJob();
            dispose = false;
        }

        /**
         * job-0 is dispose.
         * <p>
         * another job is do loop
         */
        public void doJob(int job, boolean needSleep) {
            sleep = needSleep;
            if (job == 0){
                dispose = true;
            }
            this.execute();
        }

        public void changeImg() throws InterruptedException {
            switch (x) {
                case 1 -> {
                    publish(Utils.getIcon("kertas_kanan.png"));
                    computerHand = Hand.KERTAS;
                    ++x;
                }
                case 2 -> {
                    publish(Utils.getIcon("gunting_kanan.png"));
                    computerHand = Hand.GUNTING;
                    ++x;
                }
                default -> {
                    publish(Utils.getIcon("batu_kanan.png"));
                    computerHand = Hand.BATU;
                    x = 1;
                }
            }

            // simulate latency between loop
            Thread.sleep(250);
        }

        @Override
        protected Integer doInBackground() throws InterruptedException {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                if (stop) {
                    break;
                }

                if (sleep) {
                    // wait for 2 Second
                    // to make sure, JFrame called repaint()
                    // after change icon
                    Thread.sleep(2000);
                }

                if (dispose) {
                    setProgress(i);
                } else {
                    changeImg();
                }
            }
            return x;
        }

        @Override
        protected void process(@NotNull List<Icon> chunks) {
            for (Icon ic : chunks) {
                computer.setIcon(ic);
            }
        }
    }
}

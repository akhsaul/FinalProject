package org.kelompok3.ui;

import org.jetbrains.annotations.NotNull;
import org.kelompok3.Utils;
import org.kelompok3.core.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/*
 * Created by JFormDesigner on Thu Dec 16 10:54:43 ICT 2021
 */


/**
 * @author georgeriv
 */
public class Board extends JFrame {
    private boolean wait = false;
    private static int seedInHand = 0;
    private static Move currentMove = null;
    private static int X = 0;
    private static int Y = 0;
    private static int minX = 0;
    private static int minY = 0;
    private static int maxX = 0;
    private static int maxY = 0;
    private static int btnIdx = 0;
    private static Timer timer = null;
    private static boolean RUNNING = false;
    private static java.util.List<LittleHole> computerHoles = null;
    private static java.util.List<LittleHole> playerHoles = null;

    public Board() {
        initComponents();
        initListener();
        Utils.initAudioPlayer();
        computerHoles = State.getComputerPlayer().littleHole;
        playerHoles = State.getHumanPLayer().littleHole;

        if (State.isComputerTurn()) {
            var solution = State.getComputerPlayer().getSolution(State.getHumanPLayer().littleHole);
            var hole = solution.getHole();
            RUNNING = true;
            moveDownRight(hole.getX(), hole.getY(), hole.index);
        }
    }

    public static void main(String[] args) {
        java.util.List<LittleHole> cHoles = new ArrayList<>();
        java.util.List<LittleHole> pHoles = new ArrayList<>();
        var x = 0;
        for (int i = 7; i >= 1; i--) {
            cHoles.add(new LittleHole("C-" + i, x));
            pHoles.add(new LittleHole("P-" + i, x));
            x++;
        }

        var cBigHole = new BigHole("C-L");
        var pBigHole = new BigHole("P-L");

        State.setHumanPLayer(new Human(2, pHoles, pBigHole));
        State.setComputerPlayer(new Computer(1, cHoles, cBigHole));
        State.setCurrentTurn(1);

        Utils.initTheme();
        new Board();
    }

    private enum Move {
        KIRI,
        BAWAH,
        ATAS,
        KANAN,
        KANAN_KIRI,
        KIRI_KANAN
    }

    private void moveRightLeft() {
        X = 598;
        Y = 100;
        if (seedInHand > 0) {
            ActionListener taskPerformer = evt -> {
                if (X == 598) {
                    hand.setLocation(X, Y);
                    hand.setIcon(Utils.getIcon("ClosedHand_kanan.png"));
                    X++;
                } else {
                    Utils.playSfxDown();
                    hand.setIcon(Utils.getIcon("OpenedHand_kanan.png"));
                    State.getComputerPlayer().bigHole.addSeed(1, true);
                    seedInHand--;
                    timer.stop();
                    firePropertyChange("move", currentMove, Move.KIRI);
                }
            };
            timer = new Timer(50, taskPerformer);
            timer.start();
        }
    }

    private void moveLeftRight() {
        X = 0;
        Y = 100;
        if (seedInHand > 0) {
            ActionListener taskPerformer = evt -> {
                if (X == 0) {
                    hand.setLocation(X, Y);
                    hand.setIcon(Utils.getIcon("ClosedHand_kiri.png"));
                    X++;
                } else {
                    Utils.playSfxDown();
                    hand.setIcon(Utils.getIcon("OpenedHand_kiri.png"));
                    State.getHumanPLayer().bigHole.addSeed(1, true);
                    timer.stop();
                    firePropertyChange("move", currentMove, Move.KANAN);
                }
            };
            timer = new Timer(50, taskPerformer);
            timer.start();
        }
    }

    private void moveDownRight(int x, int y, int buttonIndex) {
        if (!wait) {
            X = x;
            Y = y;
            maxY = 55;
            maxX = 490;
            minX = x;
            minY = y;
            currentMove = Move.BAWAH;
            btnIdx = buttonIndex;

            if (RUNNING) {
                ActionListener taskPerformer = evt -> {
                    if (Y == 0) {
                        hand.setIcon(Utils.getIcon("OpenedHand_atas.png"));
                    }

                    switch (currentMove) {
                        case BAWAH -> {
                            if (Y <= maxY) {
                                hand.setLocation(X, Y);
                                Y += 5;
                            } else {
                                Utils.playSfxPick();
                                hand.setIcon(Utils.getIcon("ClosedHand_atas.png"));
                                if (seedInHand == 0) {
                                    Utils.playSfxPick();
                                    seedInHand = State.getComputerPlayer().littleHole.get(btnIdx).takeSeed(true);
                                } else {
                                    Utils.playSfxDown();
                                    State.getComputerPlayer().littleHole.get(btnIdx).addSeed(1, true);
                                    seedInHand--;
                                }
                                currentMove = Move.KANAN;
                            }
                        }
                        case KANAN -> {
                            if (X <= maxX) {
                                if (seedInHand > 1) {
                                    if (X == (minX + 65)) {
                                        Utils.playSfxDown();
                                        hand.setIcon(Utils.getIcon("OpenedHand_atas.png"));
                                        State.getComputerPlayer().littleHole.get(++btnIdx).addSeed(1, true);
                                        seedInHand--;
                                        ++X;
                                        timer.setDelay(1000);
                                    } else if (X == (minX + 66)) {
                                        hand.setIcon(Utils.getIcon("ClosedHand_atas.png"));
                                        minX = (minX + 65);
                                        --X;
                                        timer.setDelay(50);
                                    } else {
                                        X += 5;
                                        hand.setLocation(X, Y);
                                    }
                                } else {
                                    currentMove = Move.BAWAH;
                                    timer.stop();
                                    timer.restart();
                                }
                            } else {
                                wait = false;
                                timer.stop();
                                hand.setIcon(Utils.getIcon("ClosedHand_atas.png"));
                                firePropertyChange("move", currentMove, Move.KANAN_KIRI);
                            }
                        }
                    }
                };
                timer = new Timer(50, taskPerformer);
                wait = true;
                timer.start();
            }
        }
    }

    private void moveUpLeft(int x, int y, int buttonIndex) {
        if (!wait) {
            X = x;
            Y = y;
            maxY = y;
            maxX = x;
            minX = 100;
            minY = 150;
            currentMove = Move.ATAS;
            btnIdx = buttonIndex;
            if (RUNNING) {
                ActionListener taskPerformer = evt -> {
                    if (Y == 195) {
                        hand.setIcon(Utils.getIcon("OpenedHand_bawah.png"));
                    }
                    switch (currentMove) {
                        case ATAS -> {
                            if (Y >= minY) {
                                hand.setLocation(X, Y);
                                Y -= 5;
                            } else {
                                hand.setIcon(Utils.getIcon("ClosedHand_bawah.png"));
                                if (seedInHand == 0) {
                                    Utils.playSfxPick();
                                    seedInHand = State.getHumanPLayer().littleHole.get(btnIdx).takeSeed(true);
                                } else {
                                    Utils.playSfxDown();
                                    State.getHumanPLayer().littleHole.get(btnIdx).addSeed(1, true);
                                    seedInHand--;
                                }
                                currentMove = Move.KIRI;
                            }
                        }
                        case KIRI -> {
                            if (X >= minX) {
                                if (seedInHand > 1) {
                                    if (X == (maxX - 65)) {
                                        Utils.playSfxDown();
                                        hand.setIcon(Utils.getIcon("OpenedHand_bawah.png"));
                                        State.getHumanPLayer().littleHole.get(++btnIdx).addSeed(1, true);
                                        seedInHand--;
                                        --X;
                                        timer.setDelay(1000);
                                    } else if (X == (maxX - 66)) {
                                        hand.setIcon(Utils.getIcon("ClosedHand_bawah.png"));
                                        maxX = (maxX - 65);
                                        ++X;
                                        timer.setDelay(50);
                                    } else {
                                        X -= 5;
                                        hand.setLocation(X, Y);
                                    }
                                } else {
                                    currentMove = Move.ATAS;
                                    timer.stop();
                                    timer.restart();
                                }
                            } else {
                                wait = false;
                                timer.stop();
                                hand.setIcon(Utils.getIcon("ClosedHand_bawah.png"));
                                firePropertyChange("move", currentMove, Move.KIRI_KANAN);
                            }
                        }
                    }
                };
                timer = new Timer(50, taskPerformer);
                wait = true;
                timer.start();
            }
        }
    }

    private void initListener() {
        /*
        cBtn7.addActionListener(e -> {
            moveDownRight(100, 0, 1);
        });
        cBtn6.addActionListener(e -> {
            moveDownRight(165, 0, 2);
        });
        cBtn5.addActionListener(e -> {
            moveDownRight(230, 0, 3);
        });
        cBtn4.addActionListener(e -> {
            moveDownRight(295, 0, 4);
        });
        cBtn3.addActionListener(e -> {
            moveDownRight(360, 0, 5);
        });
        cBtn2.addActionListener(e -> {
            moveDownRight(425, 0, 6);
        });
        cBtn1.addActionListener(e -> {
            moveDownRight(490, 0, 7);
        });
         */

        addPropertyChangeListener("move", e -> {
            if (e.getNewValue() == Move.KIRI_KANAN) {
                if (State.isComputerTurn()){
                    firePropertyChange("move", e.getOldValue(), Move.KANAN);
                }else {
                    moveLeftRight();
                }
            } else if (e.getNewValue() == Move.KANAN) {
                moveDownRight(100, 0,6 - btnIdx);
            } else if (e.getNewValue() == Move.KANAN_KIRI) {
                moveRightLeft();
            } else if (e.getNewValue() == Move.KIRI) {
                if (seedInHand <= 1) {
                    if (State.isComputerTurn()) {
                        var solution = State.getComputerPlayer().getSolution(State.getHumanPLayer().littleHole);
                        var hole = solution.getHole();
                        moveDownRight(hole.getX(), hole.getY(), hole.index);
                    }
                } else {
                    moveUpLeft(490, 195, 6 - btnIdx);
                }
            }
        });

        pBtn7.addActionListener(e -> moveUpLeft(490, 195, 0));
        pBtn6.addActionListener(e -> moveUpLeft(425, 195, 1));
        pBtn5.addActionListener(e -> moveUpLeft(360, 195, 2));
        pBtn4.addActionListener(e -> moveUpLeft(295, 195, 3));
        pBtn3.addActionListener(e -> moveUpLeft(230, 195, 4));
        pBtn2.addActionListener(e -> moveUpLeft(165, 195, 5));
        pBtn1.addActionListener(e -> moveUpLeft(100, 195, 6));

        Player player = State.getComputerPlayer();
        BigHole hole = player.bigHole;
        addBtn(player.littleHole,
                new JLabel[]{cLabel7, cLabel6, cLabel5, cLabel4, cLabel3, cLabel2, cLabel1},
                new JButton[]{cBtn7, cBtn6, cBtn5, cBtn4, cBtn3, cBtn2, cBtn1},
                new int[]{100, 165, 230, 295, 360, 425, 490}, 0);
        hole.setLabel(cSkor);
        hole.setLabelImg(cBig);

        player = State.getHumanPLayer();
        hole = player.bigHole;
        addBtn(player.littleHole,
                new JLabel[]{pLabel7, pLabel6, pLabel5, pLabel4, pLabel3, pLabel2, pLabel1},
                new JButton[]{pBtn7, pBtn6, pBtn5, pBtn4, pBtn3, pBtn2, pBtn1},
                new int[]{490, 425, 360, 295, 230, 165, 100}, 195);
        hole.setLabel(pSkor);
        hole.setLabelImg(pBig);
    }

    private void addBtn(java.util.List<LittleHole> list,
                        JLabel[] labels, JButton[] buttons, int[] x, int y) {
        for (int i = 0; i < buttons.length; i++) {
            var hole = list.get(i);
            hole.setButton(buttons[i], x[i], y);
            hole.setLabel(labels[i]);
        }
    }

    private void initComponents() {
        setContentPane(Utils.getBackgroundImg("background_board.png"));
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - georgeriv
        layerBot = new JLayeredPane();
        playerName = new JLabel();
        pSkor = new JLabel();
        label5 = new JLabel();
        label1 = new JLabel();
        layerMid = new JLayeredPane();
        hand = new JLabel();
        pBig = new JLabel();
        cLabel7 = new JLabel();
        cBtn7 = new JButton();
        cLabel6 = new JLabel();
        cBtn6 = new JButton();
        cLabel5 = new JLabel();
        cBtn5 = new JButton();
        cLabel4 = new JLabel();
        cBtn4 = new JButton();
        cLabel3 = new JLabel();
        cBtn3 = new JButton();
        cLabel2 = new JLabel();
        cBtn2 = new JButton();
        cLabel1 = new JLabel();
        cBtn1 = new JButton();
        cBig = new JLabel();
        pLabel1 = new JLabel();
        pBtn1 = new JButton();
        pLabel2 = new JLabel();
        pBtn2 = new JButton();
        pLabel3 = new JLabel();
        pBtn3 = new JButton();
        pLabel4 = new JLabel();
        pBtn4 = new JButton();
        pLabel5 = new JLabel();
        pBtn5 = new JButton();
        pLabel6 = new JLabel();
        pBtn6 = new JButton();
        pLabel7 = new JLabel();
        pBtn7 = new JButton();
        label3 = new JLabel();
        layerTop = new JLayeredPane();
        cSkor = new JLabel();
        label4 = new JLabel();
        computerName = new JLabel();
        label2 = new JLabel();

        //======== this ========
        Container contentPane = getContentPane();

        //======== layerBot ========
        {

            //---- playerName ----
            playerName.setText("Pemain");
            playerName.setFont(new Font("Comic Sans MS", playerName.getFont().getStyle(), 18));
            playerName.setHorizontalAlignment(SwingConstants.CENTER);
            playerName.setForeground(Color.black);
            layerBot.add(playerName, JLayeredPane.DEFAULT_LAYER);
            playerName.setBounds(73, 25, 115, playerName.getPreferredSize().height);

            //---- pSkor ----
            pSkor.setText("0");
            pSkor.setHorizontalAlignment(SwingConstants.CENTER);
            pSkor.setFont(new Font("Comic Sans MS", pSkor.getFont().getStyle(), 18));
            pSkor.setForeground(Color.black);
            layerBot.add(pSkor, JLayeredPane.DEFAULT_LAYER);
            pSkor.setBounds(190, 5, 60, 65);

            //---- label5 ----
            label5.setHorizontalAlignment(SwingConstants.CENTER);
            label5.setIcon(Utils.getIcon("player_skor.png"));
            layerBot.add(label5, JLayeredPane.DEFAULT_LAYER);
            label5.setBounds(new Rectangle(new Point(188, 0), label5.getPreferredSize()));

            //---- label1 ----
            label1.setIcon(Utils.getIcon("player.png"));
            layerBot.add(label1, JLayeredPane.DEFAULT_LAYER);
            label1.setBounds(new Rectangle(new Point(0, 0), label1.getPreferredSize()));
        }

        //======== layerMid ========
        {
            layerMid.setDoubleBuffered(true);

            //---- hand ----
            hand.setHorizontalAlignment(SwingConstants.CENTER);
            hand.setIcon(Utils.getIcon("hand_null.png"));
            layerMid.add(hand, JLayeredPane.DEFAULT_LAYER);
            hand.setBounds(new Rectangle(new Point(100, 155), hand.getPreferredSize()));

            //---- pBig ----
            pBig.setHorizontalAlignment(SwingConstants.CENTER);
            pBig.setIcon(Utils.getIcon("hole_B.png"));
            pBig.setBorder(null);
            layerMid.add(pBig, JLayeredPane.DEFAULT_LAYER);
            pBig.setBounds(new Rectangle(new Point(10, 105), pBig.getPreferredSize()));

            //---- cLabel7 ----
            initBtn(cLabel7);
            cLabel7.setBounds(135, 30, 30, 30);

            //---- cBtn7 ----
            cBtn7.setContentAreaFilled(false);
            cBtn7.setBorder(null);
            cBtn7.setIcon(Utils.getIcon("hole_L_7.png"));
            layerMid.add(cBtn7, JLayeredPane.DEFAULT_LAYER);
            cBtn7.setBounds(new Rectangle(new Point(120, 78), cBtn7.getPreferredSize()));

            //---- cLabel6 ----
            initBtn(cLabel6);
            cLabel6.setBounds(200, 30, 30, 30);

            //---- cBtn6 ----
            cBtn6.setContentAreaFilled(false);
            cBtn6.setBorder(null);
            cBtn6.setIcon(Utils.getIcon("hole_L_7.png"));
            layerMid.add(cBtn6, JLayeredPane.DEFAULT_LAYER);
            cBtn6.setBounds(185, 78, 60, 60);

            //---- cLabel5 ----
            initBtn(cLabel5);
            cLabel5.setBounds(265, 30, 30, 30);

            //---- cBtn5 ----
            cBtn5.setContentAreaFilled(false);
            cBtn5.setBorder(null);
            cBtn5.setIcon(Utils.getIcon("hole_L_7.png"));
            layerMid.add(cBtn5, JLayeredPane.DEFAULT_LAYER);
            cBtn5.setBounds(253, 78, 60, 60);

            //---- cLabel4 ----
            initBtn(cLabel4);
            cLabel4.setBounds(335, 30, 30, 30);

            //---- cBtn4 ----
            cBtn4.setContentAreaFilled(false);
            cBtn4.setBorder(null);
            cBtn4.setIcon(Utils.getIcon("hole_L_7.png"));
            layerMid.add(cBtn4, JLayeredPane.DEFAULT_LAYER);
            cBtn4.setBounds(320, 78, 60, 60);

            //---- cLabel3 ----
            initBtn(cLabel3);
            cLabel3.setBounds(400, 30, 30, 30);

            //---- cBtn3 ----
            cBtn3.setContentAreaFilled(false);
            cBtn3.setBorder(null);
            cBtn3.setIcon(Utils.getIcon("hole_L_7.png"));
            layerMid.add(cBtn3, JLayeredPane.DEFAULT_LAYER);
            cBtn3.setBounds(386, 78, 60, 60);

            //---- cLabel2 ----
            initBtn(cLabel2);
            cLabel2.setBounds(470, 30, 30, 30);

            //---- cBtn2 ----
            cBtn2.setContentAreaFilled(false);
            cBtn2.setBorder(null);
            cBtn2.setIcon(Utils.getIcon("hole_L_7.png"));
            layerMid.add(cBtn2, JLayeredPane.DEFAULT_LAYER);
            cBtn2.setBounds(455, 78, 60, 60);

            //---- cLabel1 ----
            initBtn(cLabel1);
            cLabel1.setBounds(535, 30, 30, 30);

            //---- cBtn1 ----
            cBtn1.setContentAreaFilled(false);
            cBtn1.setBorder(null);
            cBtn1.setIcon(Utils.getIcon("hole_L_7.png"));
            layerMid.add(cBtn1, JLayeredPane.DEFAULT_LAYER);
            cBtn1.setBounds(520, 78, 60, 60);

            //---- cBig ----
            cBig.setHorizontalAlignment(SwingConstants.CENTER);
            cBig.setIcon(Utils.getIcon("hole_B.png"));
            cBig.setBorder(null);
            layerMid.add(cBig, JLayeredPane.DEFAULT_LAYER);
            cBig.setBounds(595, 105, 90, 90);

            //---- pLabel1 ----
            initBtn(pLabel1);
            pLabel1.setBounds(135, 240, 30, 30);

            //---- pBtn1 ----
            pBtn1.setContentAreaFilled(false);
            pBtn1.setBorder(null);
            pBtn1.setIcon(Utils.getIcon("hole_L_7.png"));
            layerMid.add(pBtn1, JLayeredPane.DEFAULT_LAYER);
            pBtn1.setBounds(120, 166, 60, 60);

            //---- pLabel2 ----
            initBtn(pLabel2);
            pLabel2.setBounds(200, 240, 30, 30);

            //---- pBtn2 ----
            pBtn2.setContentAreaFilled(false);
            pBtn2.setBorder(null);
            pBtn2.setIcon(Utils.getIcon("hole_L_7.png"));
            layerMid.add(pBtn2, JLayeredPane.DEFAULT_LAYER);
            pBtn2.setBounds(new Rectangle(new Point(185, 166), pBtn2.getPreferredSize()));

            //---- pLabel3 ----
            initBtn(pLabel3);
            pLabel3.setBounds(265, 240, 30, 30);

            //---- pBtn3 ----
            pBtn3.setContentAreaFilled(false);
            pBtn3.setBorder(null);
            pBtn3.setIcon(Utils.getIcon("hole_L_7.png"));
            layerMid.add(pBtn3, JLayeredPane.DEFAULT_LAYER);
            pBtn3.setBounds(251, 166, 60, 60);

            //---- pLabel4 ----
            initBtn(pLabel4);
            pLabel4.setBounds(335, 240, 30, 30);

            //---- pBtn4 ----
            pBtn4.setContentAreaFilled(false);
            pBtn4.setBorder(null);
            pBtn4.setIcon(Utils.getIcon("hole_L_7.png"));
            layerMid.add(pBtn4, JLayeredPane.DEFAULT_LAYER);
            pBtn4.setBounds(new Rectangle(new Point(320, 166), pBtn4.getPreferredSize()));

            //---- pLabel5 ----
            initBtn(pLabel5);
            pLabel5.setBounds(400, 240, 30, 30);

            //---- pBtn5 ----
            pBtn5.setContentAreaFilled(false);
            pBtn5.setBorder(null);
            pBtn5.setIcon(Utils.getIcon("hole_L_7.png"));
            layerMid.add(pBtn5, JLayeredPane.DEFAULT_LAYER);
            pBtn5.setBounds(385, 166, 60, 60);

            //---- pLabel6 ----
            initBtn(pLabel6);
            pLabel6.setBounds(470, 240, 30, 30);

            //---- pBtn6 ----
            pBtn6.setContentAreaFilled(false);
            pBtn6.setBorder(null);
            pBtn6.setIcon(Utils.getIcon("hole_L_7.png"));
            layerMid.add(pBtn6, JLayeredPane.DEFAULT_LAYER);
            pBtn6.setBounds(455, 166, 60, 60);

            //---- pLabel7 ----
            initBtn(pLabel7);
            pLabel7.setBounds(535, 240, 30, 30);

            //---- pBtn7 ----
            pBtn7.setContentAreaFilled(false);
            pBtn7.setBorder(null);
            pBtn7.setIcon(Utils.getIcon("hole_L_7.png"));
            layerMid.add(pBtn7, JLayeredPane.DEFAULT_LAYER);
            pBtn7.setBounds(520, 166, 60, 60);

            //---- label3 ----
            label3.setIcon(Utils.getIcon("congklak_bg.png"));
            label3.setHorizontalAlignment(SwingConstants.CENTER);
            layerMid.add(label3, JLayeredPane.DEFAULT_LAYER);
            label3.setBounds(new Rectangle(new Point(0, 48), label3.getPreferredSize()));
        }

        //======== layerTop ========
        {

            //---- cSkor ----
            cSkor.setText("0");
            cSkor.setHorizontalAlignment(SwingConstants.CENTER);
            cSkor.setFont(new Font("Comic Sans MS", cSkor.getFont().getStyle(), 18));
            cSkor.setForeground(Color.black);
            layerTop.add(cSkor, JLayeredPane.DEFAULT_LAYER);
            cSkor.setBounds(5, 5, 60, 65);

            //---- label4 ----
            label4.setHorizontalAlignment(SwingConstants.CENTER);
            label4.setIcon(Utils.getIcon("computer_skor.png"));
            layerTop.add(label4, JLayeredPane.DEFAULT_LAYER);
            label4.setBounds(new Rectangle(new Point(0, 0), label4.getPreferredSize()));

            //---- computerName ----
            computerName.setText("Komputer");
            computerName.setFont(new Font("Comic Sans MS", computerName.getFont().getStyle(), 18));
            computerName.setHorizontalAlignment(SwingConstants.CENTER);
            computerName.setForeground(Color.black);
            layerTop.add(computerName, JLayeredPane.DEFAULT_LAYER);
            computerName.setBounds(70, 25, 115, computerName.getPreferredSize().height);

            //---- label2 ----
            label2.setIcon(Utils.getIcon("computer.png"));
            layerTop.add(label2, JLayeredPane.DEFAULT_LAYER);
            label2.setBounds(new Rectangle(new Point(55, 0), label2.getPreferredSize()));
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(layerBot, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                                .addGap(0, 451, Short.MAX_VALUE)
                                                .addComponent(layerTop, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
                                                .addGap(40, 40, 40))))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(layerMid, GroupLayout.PREFERRED_SIZE, 698, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 52, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(layerTop, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(layerMid, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(layerBot, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40))
        );
        setSize(800, 600);
        setLocationRelativeTo(null);

        setTitle("Game Congklak - Board");
        setIconImage(Utils.getImgRes("icon.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void initBtn(@NotNull JLabel btn) {
        btn.setText("7");
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setBorder(new LineBorder(Color.white));
        btn.setForeground(Color.white);
        btn.setFont(btn.getFont().deriveFont(15f));
        layerMid.add(btn, JLayeredPane.DEFAULT_LAYER);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - georgeriv
    private JLayeredPane layerBot;
    private JLabel playerName;
    private JLabel pSkor;
    private JLabel label5;
    private JLabel label1;
    private JLayeredPane layerMid;
    private JLabel hand;
    private JLabel pBig;
    private JLabel cLabel7;
    private JButton cBtn7;
    private JLabel cLabel6;
    private JButton cBtn6;
    private JLabel cLabel5;
    private JButton cBtn5;
    private JLabel cLabel4;
    private JButton cBtn4;
    private JLabel cLabel3;
    private JButton cBtn3;
    private JLabel cLabel2;
    private JButton cBtn2;
    private JLabel cLabel1;
    private JButton cBtn1;
    private JLabel cBig;
    private JLabel pLabel1;
    private JButton pBtn1;
    private JLabel pLabel2;
    private JButton pBtn2;
    private JLabel pLabel3;
    private JButton pBtn3;
    private JLabel pLabel4;
    private JButton pBtn4;
    private JLabel pLabel5;
    private JButton pBtn5;
    private JLabel pLabel6;
    private JButton pBtn6;
    private JLabel pLabel7;
    private JButton pBtn7;
    private JLabel label3;
    private JLayeredPane layerTop;
    private JLabel cSkor;
    private JLabel label4;
    private JLabel computerName;
    private JLabel label2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

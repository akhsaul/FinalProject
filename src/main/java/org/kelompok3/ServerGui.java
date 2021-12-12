package org.kelompok3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ServerGui extends JFrame {
    TrayIcon trayIcon;
    SystemTray tray;
    PopupMenu popup;

    ServerGui() {
        super("Server GUI");

        try {
            System.out.println("setting look and feel");
            UIManager.setLookAndFeel(Launcher.theme());
        } catch (Exception e) {
            System.out.println("Unable to set LookAndFeel");
        }

        if (SystemTray.isSupported()) {
            System.out.println("system tray supported");
            tray = SystemTray.getSystemTray();
            popup = new PopupMenu();

            addItemTray("Exit", e -> {
                System.out.println("Exiting....");
                System.exit(0);
            });

            addItemTray("Open", e -> {
                setVisible(true);
                setExtendedState(JFrame.NORMAL);
            });

            Image image = getImg("/media/faisal/DukeImg/Duke256.png");
            trayIcon = new TrayIcon(image, "SystemTray Demo", popup);
            trayIcon.setImageAutoSize(true);
        } else {
            System.out.println("system tray not supported");
        }

        addWindowStateListener(e -> {
            var state = e.getNewState();
            if (state == ICONIFIED || state == 7) {
                try {
                    tray.add(trayIcon);
                    setVisible(false);
                } catch (AWTException ex) {
                    System.out.println("Unable to add to tray");
                    ex.printStackTrace();
                }
            } else if (state == MAXIMIZED_BOTH || state == NORMAL) {
                tray.remove(trayIcon);
                setVisible(true);
            }
        });

        setIconImage(getImg("Duke256.png"));

        setVisible(true);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Image getImg(String filename){
        return Toolkit.getDefaultToolkit().getImage(filename);
    }

    private void addItemTray(String label, ActionListener listener) {
        if (popup != null) {
            var item = new MenuItem(label);
            item.addActionListener(listener);
            popup.add(item);
        }
    }
}
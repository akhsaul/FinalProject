package org.kelompok3;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kelompok3.core.State;
import org.springframework.util.ResourceUtils;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public final class Utils {
    public static int WIDTH = 816;
    public static int HEIGHT = 639;
    private static LookAndFeel theme = null;
    private static AudioPlayerComponent sound = null;
    private static AudioPlayerComponent sfxDown = null;
    private static AudioPlayerComponent sfxPick = null;
    private static SystemTray systemTray = null;
    private static PopupMenu popupMenu = null;
    public static String workingDir = System.getProperty("java.io.tmpdir") + "/congklak/";

    private Utils() {
    }

    public static <T> int lastIndex(@NotNull List<T> list) {
        return list.size() - 1;
    }

    @Contract(" -> new")
    public static @NotNull Dimension screenSize() {
        var height = Toolkit.getDefaultToolkit().getScreenSize().height;
        double fraction = (20.0 / 100.0) * height;
        height -= (int) fraction;
        var width = height + 200;
        return new Dimension(width, height);
    }

    public static @NotNull URL getRes(String path) {
        URL url = null;
        try {
            url = ResourceUtils.getURL("classpath:static/" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // throw error if null
        return notNull(url);
    }

    /**
     * @return Image or null
     */
    public static BufferedImage getImgRes(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getRes(path));
        } catch (Exception e) {
            System.out.println("IOException Happened");
            e.printStackTrace();
        }
        // throw error if null
        return notNull(img);
    }

    @Contract("_ -> new")
    public static @NotNull JLabel getBackgroundImg(String path) {
        return new JLabel(getIcon(path));
    }

    @Contract("_ -> new")
    public static @NotNull ImageIcon getIcon(String path) {
        return new ImageIcon(getImgRes(path));
    }

    public static <T> T notNull(T obj) {
        return Objects.requireNonNull(obj);
    }

    private static @NotNull AudioPlayerComponent initAudioPlayer(String filename, boolean repeat) {
        var component = new AudioPlayerComponent();
        component.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void error(MediaPlayer mediaPlayer) {
                System.out.println("Failed to load Audio. " + mediaPlayer.media().info().mrl());
                System.exit(1);
            }
        });
        component.mediaPlayer().audio().setVolume(100);
        try {
            var file = new File(workingDir);
            file.mkdirs();

            var path = workingDir + filename;
            file = new File(path);
            if (!file.exists()) {
                getRes(filename).openStream().transferTo(
                        new BufferedOutputStream(new FileOutputStream(file))
                );
            }

            // vlcj can't read file in jar
            component.mediaPlayer().media().prepare(path);
            component.mediaPlayer().controls().setRepeat(repeat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return component;
    }

    public static void initAudioPlayer() {
        if (sound == null) {
            sound = initAudioPlayer("bgm.mp3", true);
        }
        if (sfxDown == null) {
            sfxDown = initAudioPlayer("sfx_down.wav", false);
        }
        if (sfxPick == null) {
            sfxPick = initAudioPlayer("sfx_pick.wav", false);
        }
    }

    public static @NotNull String readFile(@NotNull String path) {
        byte[] data = null;
        try {
            data = new BufferedInputStream(getRes(path).openStream()).readAllBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(notNull(data));
    }

    public static synchronized void stopSound() {
        assert sound != null;
        sound.mediaPlayer().controls().stop();
    }

    public static synchronized void playSound() {
        if (State.isEnableBgm()) {
            sound.mediaPlayer().controls().play();
        }
    }

    public static synchronized void playSfxDown() {
        if (State.isEnableSfx()) {
            sfxDown.mediaPlayer().controls().play();
        }
    }

    public static synchronized void playSfxPick() {
        if (State.isEnableSfx()) {
            sfxPick.mediaPlayer().controls().play();
        }
    }

    private static void installTheme() {
        assert theme != null;
        FlatLaf.setup(theme);
        JFrame.setDefaultLookAndFeelDecorated(true);
    }

    public static synchronized void initTheme() {
        if (theme == null) {
            theme = new FlatDarculaLaf();
            installTheme();
        }
    }

    public static synchronized void setTheme(int num) {
        switch (num) {
            case 1 -> theme = new FlatDarculaLaf();
            case 2 -> theme = new FlatLightLaf();
            default -> throw new IllegalArgumentException("Wrong number of theme");
        }
        installTheme();
    }

    private static synchronized void initPopupMenu() {
        if (popupMenu == null) {
            popupMenu = new PopupMenu();
        }
    }

    public static synchronized void addTrayMenu(@NotNull String label, @Nullable ActionListener listener) {
        addTrayMenu(new MenuItem(), listener);
    }

    public static synchronized void addTrayMenu(@Nullable MenuItem item, @Nullable ActionListener listener) {
        initPopupMenu();
        if (item != null) {
            item.addActionListener(listener);
            popupMenu.add(item);
        }
    }

    public static synchronized void iniSystemTray() {
        if (SystemTray.isSupported()) {
            try {
                if (systemTray == null) {
                    systemTray = SystemTray.getSystemTray();
                    initPopupMenu();
                    var trayIcon = new TrayIcon(Utils.getImgRes("static/icon.png"), "Game Congklak", popupMenu);
                    trayIcon.setImageAutoSize(true);
                    systemTray.add(trayIcon);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized @Nullable SystemTray getSystemTray() {
        return systemTray;
    }

    public static int warnMessage(@NotNull Component parent, String msg) {
        return JOptionPane.showOptionDialog(parent, msg, "Peringatan!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, null, null);
    }

    public static int infoMessage(Component parent, String msg) {
        return JOptionPane.showOptionDialog(parent, msg, "Info",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, null, null);
    }
}






package org.kelompok3

import com.formdev.flatlaf.FlatDarculaLaf
import com.formdev.flatlaf.FlatLaf
import org.kelompok3.core.State
import org.springframework.util.ResourceUtils
import uk.co.caprica.vlcj.player.base.ControlsApi
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent
import java.awt.Component
import java.awt.Image
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.imageio.ImageIO
import javax.swing.*
import kotlin.system.exitProcess

object Utils {
    @JvmField
    val workingDir = System.getProperty("java.io.tmpdir") + "/congklak/"
    private var soundController: ControlsApi? = null
    private var sfxController: ControlsApi? = null

    @JvmStatic
    fun initPlayer() {
        if (soundController == null) {
            soundController = initAudioPlayer("bgm.mp3", true)
        }
    }

    @Synchronized
    @JvmStatic
    fun playSound() {
        if (State.isEnableBgm()) {
            notNull(soundController).play()
        }
    }

    @Synchronized
    @JvmStatic
    fun stopSound() {
        notNull(soundController).stop()
    }

    private fun initAudioPlayer(fileName: String, repeat: Boolean = false): ControlsApi {
        return AudioPlayerComponent().mediaPlayer().apply {
            events().addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {
                override fun error(mediaPlayer: MediaPlayer?) {
                    println("Failed to load Audio. " + mediaPlayer?.media()?.info()?.mrl());
                    exitProcess(-1)
                }
            })
            audio().setVolume(100)
            val fullPath = workingDir + fileName
            val file: File = File(workingDir).let {
                it.mkdirs()
                File(fullPath)
            }
            if (!file.exists()) {
                getResource(fileName).transferTo(BufferedOutputStream(FileOutputStream(file)))
            }

            media().prepare(fullPath)
            controls().repeat = repeat
        }.controls()
    }

    @JvmOverloads
    @JvmStatic
    fun build(
        frame: Window,
        title: String,
        prefix: String = "Game Congklak - ",
        location: Component? = null,
        background: String? = null,
        closeOperation: Int = WindowConstants.EXIT_ON_CLOSE,
        icon: String = "assets/icon.png"
    ) {
        frame.setTitle(prefix + title)
        background?.let { frame.setBackground(getImage(it)) }
        frame.setIcon(getImage(icon))
        frame.setDefaultCloseOperation(closeOperation)
        frame.setResizable(false)
        frame.setLocationRelativeTo(location)
    }

    @JvmOverloads
    @JvmStatic
    fun warn(parent: Component? = null, message: String): Int {
        return JOptionPane.showOptionDialog(
            parent, message, "Peringatan!",
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
            null, null, null
        )
    }

    @JvmOverloads
    @JvmStatic
    fun info(parent: Component? = null, message: String): Int {
        return JOptionPane.showOptionDialog(
            parent, message, "Informasi",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, null, null
        );
    }

    @JvmStatic
    fun openBrowser(url: String) {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler $url").waitFor()
    }

    @JvmStatic
    fun readFileAsString(path: String): String {
        return String(getResource(path).readAllBytes())
    }

    @JvmStatic
    fun getImage(path: String): Image {
        return ImageIO.read(getResource(path))
    }

    @JvmStatic
    fun getIcon(path: String): ImageIcon {
        return ImageIcon(getImage(path))
    }

    @JvmStatic
    fun getBackground(path: String): JLabel {
        return JLabel(getIcon(path))
    }

    @JvmStatic
    fun initTheme(frame: () -> JFrame) {
        FlatLaf.setup(FlatDarculaLaf())
        JFrame.setDefaultLookAndFeelDecorated(true)
        frame()
        initPlayer()
    }

    @JvmStatic
    fun getResource(path: String): BufferedInputStream {
        return notNull(ResourceUtils.getURL("classpath:static/$path")).openStream().buffered()
    }

    @JvmOverloads
    @JvmStatic
    fun require(value: Boolean, exception: Exception = IllegalArgumentException("Failed requirement.")) {
        if (!value) {
            throw exception
        }
    }

    @JvmOverloads
    @JvmStatic
    fun <T : Any> notNull(obj: T?, exception: Exception = IllegalArgumentException("Required value was null.")): T {
        if (obj != null) {
            return obj
        } else {
            throw exception
        }
    }
}
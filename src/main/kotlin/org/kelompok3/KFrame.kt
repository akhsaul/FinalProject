package org.kelompok3

import java.awt.Component
import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel

open class KFrame : Window, JFrame() {
    init {
        isVisible = true
    }

    override fun setBackground(background: Image) {
        contentPane = JLabel(ImageIcon(background))
    }

    override fun setTitle(name: String){
        super.setTitle(name)
    }

    override fun setIcon(icon: Image) {
        super.setIconImage(icon)
    }

    override fun setDefaultCloseOperation(value: Int) {
        super.setDefaultCloseOperation(value)
    }

    override fun setResizable(value: Boolean){
        super.setResizable(value)
    }

    override fun setLocationRelativeTo(location: Component?){
        super.setLocationRelativeTo(location)
    }
}
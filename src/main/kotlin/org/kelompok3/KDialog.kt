package org.kelompok3

import java.awt.Component
import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JDialog
import javax.swing.JLabel

open class KDialog: Window, JDialog {
    init {
        isVisible = true
    }

    constructor(owner: java.awt.Window): super(owner)
    constructor(): super()

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
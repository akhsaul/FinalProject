package org.kelompok3

import java.awt.Component
import java.awt.Image

interface Window {
    fun setBackground(background: Image)
    fun setTitle(name: String)
    fun setIcon(icon: Image)
    fun setResizable(value: Boolean)
    fun setDefaultCloseOperation(value: Int)
    fun setLocationRelativeTo(location: Component?)
}
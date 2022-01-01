package org.kelompok3.ui

import org.kelompok3.KDialog
import org.kelompok3.Utils
import java.awt.*
import javax.swing.*
import javax.swing.border.EmptyBorder

class Info(owner: Window) : KDialog(owner) {
    val backBtn = JButton()

    init {
        Utils.build(this, "Informasi", closeOperation = DISPOSE_ON_CLOSE)
        initComponents()
        backBtn.addActionListener {
            this.dispose()
        }
    }

    private fun initComponents() {
        val panel1 = JPanel()
        val dialogPane = JPanel()
        val contentPanel = JPanel()
        val tabbedPane1 = JTabbedPane()
        val scrollPane1 = JScrollPane()
        val scrollPane2 = JScrollPane()
        val textArea1 = JTextArea()
        val label6 = JLabel()
        val label5 = JLabel()
        val label4 = JLabel()
        val label3 = JLabel()
        val label2 = JLabel()
        val label1 = JLabel()
        val buttonBar = JPanel()
        val txtTutorial = JTextArea()
        val txtRules = JTextArea()

        contentPane.layout = BorderLayout()

        dialogPane.border = EmptyBorder(12, 12, 12, 12)
        dialogPane.layout = BorderLayout()

        txtTutorial.text = Utils.readFileAsString("tutorial.txt")
        txtTutorial.isEditable = false
        txtTutorial.lineWrap = true
        txtTutorial.wrapStyleWord = true
        txtTutorial.caretPosition = 0
        scrollPane1.setViewportView(txtTutorial)
        tabbedPane1.addTab("Cara Bermain", scrollPane1)

        txtRules.text = Utils.readFileAsString("rules.txt")
        txtRules.isEditable = false
        txtRules.lineWrap = true
        txtRules.wrapStyleWord = true
        txtRules.caretPosition = 0
        scrollPane2.setViewportView(txtRules)
        tabbedPane1.addTab("Aturan Bermain", scrollPane2)

        label6.text = "Syamsul Maarif"
        label6.horizontalAlignment = SwingConstants.CENTER

        textArea1.text = """
            Aplikasi ini memakai beberapa library, antara lain:
            - flatlaf themes
            - vlcj
            - jackson
            - mysql
            - spring boot
        """.trimIndent()
        textArea1.isEditable = false
        textArea1.border = null

        label5.text = "Ikhsan Maulana"
        label5.horizontalAlignment = SwingConstants.CENTER

        label4.text = "Nursela Salsabilla Basuni"
        label4.horizontalAlignment = SwingConstants.CENTER

        label3.text = "Andi Syafei"
        label3.horizontalAlignment = SwingConstants.CENTER

        label2.text = "Dibuat oleh"
        label2.horizontalAlignment = SwingConstants.CENTER

        label1.text = "Permainan Congklak"
        label1.horizontalAlignment = SwingConstants.CENTER

        val panel1Layout = GroupLayout(panel1)
        panel1.layout = panel1Layout
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup()
                .addGroup(
                    panel1Layout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addComponent(
                            textArea1,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        )
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt())
                )
                .addGroup(
                    GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addContainerGap(222, Short.MAX_VALUE.toInt())
                        .addGroup(
                            panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label2, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
                                .addGroup(
                                    panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(
                                            label4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE.toInt()
                                        )
                                        .addComponent(
                                            label5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE.toInt()
                                        )
                                        .addComponent(
                                            label6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE.toInt()
                                        )
                                        .addComponent(
                                            label3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                            Short.MAX_VALUE.toInt()
                                        )
                                )
                        )
                        .addGap(223, 223, 223)
                )
        )
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup()
                .addGroup(
                    panel1Layout.createSequentialGroup()
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
                        .addPreferredGap(
                            LayoutStyle.ComponentPlacement.RELATED,
                            GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE.toInt()
                        )
                        .addComponent(
                            textArea1,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        )
                )
        )

        tabbedPane1.addTab("Tentang", panel1)

        val contentPanelLayout = GroupLayout(contentPanel)
        contentPanel.layout = contentPanelLayout
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup()
                .addComponent(tabbedPane1, GroupLayout.Alignment.TRAILING)
        )
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup()
                .addComponent(
                    tabbedPane1, GroupLayout.Alignment.TRAILING,
                    GroupLayout.DEFAULT_SIZE, 275,
                    Short.MAX_VALUE.toInt()
                )
        )
        dialogPane.add(contentPanel, BorderLayout.CENTER)

        buttonBar.border = EmptyBorder(12, 0, 0, 0)
        buttonBar.layout = GridBagLayout().apply {
            columnWidths = intArrayOf(0, 80)
            columnWeights = doubleArrayOf(1.0, 0.0)
        }

        backBtn.text = "KEMBALI"
        buttonBar.add(
            backBtn, GridBagConstraints(
                1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                Insets(0, 0, 0, 0), 0, 0
            )
        )

        dialogPane.add(buttonBar, BorderLayout.SOUTH)

        contentPane.add(dialogPane, BorderLayout.CENTER)
        setSize(600, 400)
    }
}
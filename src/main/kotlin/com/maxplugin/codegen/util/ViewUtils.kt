package com.maxplugin.codegen.util

import java.awt.GridBagConstraints
import java.awt.Insets
import javax.swing.JTextField


fun constraintsLeft(x: Int, y: Int) = GridBagConstraints().apply {
    weightx = 0.15
    gridx = x
    gridy = y
    insets = Insets(0, 8, 0, 0)
    fill = GridBagConstraints.HORIZONTAL
}

fun constraintsRight(x: Int, y: Int) = GridBagConstraints().apply {
    weightx = 0.85
    gridx = x
    gridy = y
    gridwidth = 2
    fill = GridBagConstraints.HORIZONTAL
}

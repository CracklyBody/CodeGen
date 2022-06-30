package com.maxplugin.codegen

import com.intellij.openapi.ui.ComboBox
import com.maxplugin.codegen.main.CustomVariablesPanel
import com.maxplugin.codegen.main.NewScreenState
import com.maxplugin.codegen.model.AndroidComponent
import com.maxplugin.codegen.model.ArchitectureType
import com.maxplugin.codegen.util.*
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class NewScreenPanel : JPanel() {

    val nameTextField = JTextField()
    val packageTextField = JTextField()
    val basePackagePathTextField = JTextField()

    val architectureTypeComboBox = ComboBox<ArchitectureType>()
    val androidComponentComboBox = ComboBox(AndroidComponent.values())
    val projectModuleComboBox = ComboBox<com.maxplugin.codegen.model.ProjectModule>()
    val customVariablesPanel = CustomVariablesPanel()

    var onCategoryIndexChanged: ((Int) -> Unit)? = null

    private var listenersBlocked = false

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        add(
            JPanel().apply {
                layout = GridBagLayout()
                add(JLabel("Name:"), constraintsLeft(0, 0))
                add(nameTextField, constraintsRight(1, 0))
                add(JLabel("Category:"), constraintsLeft(0, 1))
                add(architectureTypeComboBox, constraintsRight(1, 1))
                add(JLabel("Module:"), constraintsLeft(0, 2))
                add(projectModuleComboBox, constraintsRight(1, 2))
                add(JLabel("Package:"), constraintsLeft(0, 3))
                add(packageTextField, constraintsRight(1, 3))
                add(JLabel("Base Package Path:"), constraintsLeft(0, 4))
                add(basePackagePathTextField, constraintsRight(1, 4))
                add(JLabel("Android Component:"), constraintsLeft(0, 5))
                add(androidComponentComboBox, constraintsRight(1, 5))
                architectureTypeComboBox.addActionListener { if (!listenersBlocked) onCategoryIndexChanged?.invoke(architectureTypeComboBox.selectedIndex) }
            }
        )
        add(customVariablesPanel)
    }

    override fun getPreferredSize() = Dimension(350, 110)

    fun render(state: NewScreenState) = state.run {
        listenersBlocked = true
        packageTextField.updateText(packageName)
        basePackagePathTextField.updateText(basePackagePath)

        if (projectModuleComboBox.itemCount == 0) {
            projectModuleComboBox.removeAllItems()
            projectModules.forEach { projectModuleComboBox.addItem(it) }
        }

        projectModuleComboBox.selectedItem = selectedModule

        if (architectureTypeComboBox.itemCount == 0) {
            categories.forEach { architectureTypeComboBox.addItem(it) }
        }
        architectureTypeComboBox.selectedIndex = 0
        listenersBlocked = false
    }
}
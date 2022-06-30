package com.maxplugin.codegen

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.fields.ExtendableTextField
import com.intellij.ui.layout.panel
import com.maxplugin.codegen.main.NewScreenState
import javax.swing.JComponent

class CreatePageObjectDialog(
    private val project: Project
): DialogWrapper(project) {

    private val panel = NewScreenPanel()

    private lateinit var textField: ExtendableTextField
    init {
        init()
        title = "Generate code"
        panel.onCategoryIndexChanged = {  }
        NewScreenState().render()
    }
//    override fun createNorthPanel(): JComponent {
//        return panel {
//            titledRow("Enter class name") {
//            }
//            row {
//                textField = ExtendableTextField()
//                textField()
//            }
//        }
//    }

//    override fun createCenterPanel(): JComponent {
//        return JPanel(BorderLayout())
//    }

    private fun NewScreenState.render() = panel.render(this)


    fun getData(): CreatePageObjectResult {
        return CreatePageObjectResult(
            textField.text,
            project,
            ""
        )
    }

    override fun createCenterPanel(): JComponent = panel

}
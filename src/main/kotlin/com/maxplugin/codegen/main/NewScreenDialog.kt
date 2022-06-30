package com.maxplugin.codegen.main

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.maxplugin.codegen.NewScreenPanel
import com.maxplugin.codegen.core.UI
import com.maxplugin.codegen.dagger.DaggerNewScreenComponent
import com.maxplugin.codegen.data.file.CurrentPath
import com.maxplugin.codegen.model.ArchitectureType
import com.maxplugin.codegen.model.ProjectModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NewScreenDialog(project: Project, currentPath: CurrentPath?) : DialogWrapper(true), CoroutineScope {

    @Inject
    lateinit var viewModel: NewScreenViewModel

    private val job = SupervisorJob()
    private val panel = NewScreenPanel()

    override val coroutineContext: CoroutineContext = Dispatchers.UI + job

    init {
        DaggerNewScreenComponent.factory().create(project, currentPath).inject(this)
        launch { viewModel.state.collect { it.render() } }
        launch { viewModel.effect.collect { handleEffect(it) } }
        panel.onCategoryIndexChanged = { launch { viewModel.actionFlow.emit(NewScreenAction.CategoryIndexChanged(it)) } }
        init()
    }

    private fun handleEffect(effect: NewScreenEffect) = when (effect) {
        NewScreenEffect.Close -> close(OK_EXIT_CODE)
    }

    private fun NewScreenState.render() = panel.render(this)

    override fun doOKAction() {
        launch {
            viewModel.actionFlow.emit(
                NewScreenAction.OkClicked(
                    panel.packageTextField.text,
                    panel.basePackagePathTextField.text,
                    panel.nameTextField.text,
                    panel.androidComponentComboBox.selectedIndex,
                    panel.projectModuleComboBox.selectedItem as ProjectModule,
                    panel.architectureTypeComboBox.selectedItem as ArchitectureType,
                    panel.customVariablesPanel.customVariablesMap
                )
            )
        }
    }

    override fun createCenterPanel() = panel

    override fun dispose() {
        job.cancel()
        viewModel.clear()
        super.dispose()
    }
}

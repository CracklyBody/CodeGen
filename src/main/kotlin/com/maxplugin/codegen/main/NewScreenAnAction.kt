package com.maxplugin.codegen.main

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.module.ModuleUtil
import com.maxplugin.codegen.data.file.CurrentPath
import com.maxplugin.codegen.model.ProjectModule

class NewScreenAnAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val currentPath = event.getData(PlatformDataKeys.VIRTUAL_FILE)?.let {
            val moduleName = ModuleUtil.findModuleForFile(it, event.project!!)?.name ?: ""
            val projectModule = ProjectModule(moduleName, moduleName.replace("${event.project!!.name}.", ""))
            CurrentPath(it.path, it.isDirectory, projectModule)
        }
        NewScreenDialog(event.project!!, currentPath).show()
    }
}

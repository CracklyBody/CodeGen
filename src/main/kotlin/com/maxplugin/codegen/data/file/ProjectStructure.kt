package com.maxplugin.codegen.data.file

import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.maxplugin.codegen.model.ProjectModule
import org.jetbrains.kotlin.idea.util.sourceRoots
import javax.inject.Inject

class ProjectStructure @Inject constructor(private val project: Project) {

    fun findSourceRoots(module: ProjectModule): List<SourceRoot> =
        ModuleManager.getInstance(project)
            .findModuleByName(module.name)
            ?.sourceRoots
            ?.map { SourceRoot(project, it) }
            ?: throw IllegalStateException("${module.name} module doesn't exist!")

    fun getAllModules() = ModuleManager.getInstance(project).modules.map { it.name }

    fun getProjectName() = project.name

    fun getProjectPath() = project.basePath ?: ""
}

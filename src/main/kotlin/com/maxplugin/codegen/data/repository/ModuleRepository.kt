package com.maxplugin.codegen.data.repository

import com.maxplugin.codegen.data.file.ProjectStructure
import com.maxplugin.codegen.model.ProjectModule
import javax.inject.Inject

class ModuleRepository @Inject constructor(
    private val projectStructure: ProjectStructure
) {

    fun getAllModules() =
        projectStructure.getAllModules()
            .filter { it != projectStructure.getProjectName() }
            .map {
                ProjectModule(
                    name = it,
                    nameWithoutPrefix = it.replace("${projectStructure.getProjectName()}.", "")
                )
            }
}

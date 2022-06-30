package com.maxplugin.codegen.data.repository

import com.maxplugin.codegen.data.file.ProjectStructure
import com.maxplugin.codegen.model.DEFAULT_SOURCE_SET
import com.maxplugin.codegen.model.ProjectModule
import javax.inject.Inject

class SourceRootRepository @Inject constructor(
    private val projectStructure: ProjectStructure
) {

    fun findCodeSourceRoot(module: ProjectModule, sourceSet: String = DEFAULT_SOURCE_SET) =
        projectStructure.findSourceRoots(module).firstOrNull {
            val pathTrimmed = it.path.removeModulePathPrefix(module)
            pathTrimmed.contains("src", true) &&
                pathTrimmed.contains(sourceSet) &&
                !pathTrimmed.contains("assets", true) &&
                !pathTrimmed.contains("res", true)
        }

    fun findResourcesSourceRoot(module: ProjectModule) =
        projectStructure.findSourceRoots(module).firstOrNull {
            val pathTrimmed = it.path.removeModulePathPrefix(module)
            pathTrimmed.contains("src", true) &&
                pathTrimmed.contains("main", true) &&
                pathTrimmed.contains("res", true)
        }

    private fun String.removeModulePathPrefix(module: ProjectModule) =
        removePrefix(projectStructure.getProjectPath() + "/" + module.nameWithoutPrefix.replace(".", "/"))
}

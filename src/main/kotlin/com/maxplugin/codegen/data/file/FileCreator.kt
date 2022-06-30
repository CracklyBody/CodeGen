package com.maxplugin.codegen.data.file

import com.maxplugin.codegen.data.repository.SettingsRepository
import com.maxplugin.codegen.data.repository.SourceRootRepository
import com.maxplugin.codegen.model.AndroidComponent
import com.maxplugin.codegen.model.ArchitectureType
import com.maxplugin.codegen.model.CustomVariable
import com.maxplugin.codegen.model.ProjectModule
import com.maxplugin.codegen.util.FileType
import javax.inject.Inject

private const val LAYOUT_DIRECTORY = "layout"

class FileCreator @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val sourceRootRepository: SourceRootRepository
) {

    fun createScreenFiles(
        packageName: String,
        basePackagePath: String,
        screenName: String,
        androidComponent: AndroidComponent,
        module: ProjectModule,
        architectureType: ArchitectureType,
        customVariablesMap: Map<CustomVariable, String>
    ) {
        settingsRepository.loadScreenElements(architectureType).apply {
            filter { it.relatedAndroidComponent == AndroidComponent.NONE || it.relatedAndroidComponent == androidComponent}
                .forEach {
                    val file = File(
                        it.fileName(screenName, packageName, basePackagePath, androidComponent.displayName, customVariablesMap),
                        it.body(screenName, packageName, basePackagePath, androidComponent.displayName, customVariablesMap),
                        it.fileType
                    )
                    if (it.fileType == FileType.LAYOUT_XML) {
                        val resourcesSubdirectory = findResourcesSubdirectory(module)
                        if (resourcesSubdirectory != null) {
                            addFile(resourcesSubdirectory, file, it.subdirectory)
                        }
                    } else {
                        val codeSubdirectory = findCodeSubdirectory(packageName, module, it.sourceSet)
                        if (codeSubdirectory != null) {
                            addFile(codeSubdirectory, file, it.subdirectory)
                        }
                    }
                }
        }
    }

    private fun addFile(directory: Directory, file: File, subdirectory: String) {
        if (subdirectory.isNotEmpty()) {
            var newSubdirectory = directory
            subdirectory.split("/").forEach { segment ->
                newSubdirectory = directory.findSubdirectory(segment) ?: directory.createSubdirectory(segment)
            }
            newSubdirectory.addFile(file)
        } else {
            directory.addFile(file)
        }
    }

    private fun findCodeSubdirectory(packageName: String, module: ProjectModule, sourceSet: String): Directory? =
        sourceRootRepository.findCodeSourceRoot(module, sourceSet)?.run {
            var subdirectory = directory
            packageName.split(".").forEach {
                subdirectory = subdirectory.findSubdirectory(it) ?: subdirectory.createSubdirectory(it)
            }
            return subdirectory
        }

    private fun findResourcesSubdirectory(module: ProjectModule) =
        sourceRootRepository.findResourcesSourceRoot(module)?.directory?.run {
            findSubdirectory(LAYOUT_DIRECTORY) ?: createSubdirectory(LAYOUT_DIRECTORY)
        }
}
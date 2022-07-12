package com.maxplugin.codegen.data.file

import android.databinding.tool.ext.toCamelCase
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiPackage
import com.maxplugin.codegen.data.repository.SettingsRepository
import com.maxplugin.codegen.data.repository.SourceRootRepository
import com.maxplugin.codegen.model.AndroidComponent
import com.maxplugin.codegen.model.ArchitectureType
import com.maxplugin.codegen.model.CustomVariable
import com.maxplugin.codegen.model.ProjectModule
import com.maxplugin.codegen.util.FileType
import com.maxplugin.codegen.util.FileUtils
import com.maxplugin.codegen.util.toSnakeCase
import javax.inject.Inject

private const val LAYOUT_DIRECTORY = "layout"

class FileCreator @Inject constructor(
    private val project: Project,
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
        customVariablesMap: Map<CustomVariable, String>,
        addRecyclerView: Boolean
    ) {
        settingsRepository.loadScreenElements(architectureType).apply {
            filter { it.relatedAndroidComponent == AndroidComponent.NONE || it.relatedAndroidComponent == androidComponent}
                .forEach {
                    val file = File(
                        it.fileName(screenName, packageName, basePackagePath, androidComponent.displayName, customVariablesMap, addRecyclerView),
                        it.body(screenName, packageName, basePackagePath, androidComponent.displayName, customVariablesMap, addRecyclerView),
                        it.fileType
                    )
                    if (it.fileType == FileType.LAYOUT_XML) {
                        val resourcesSubdirectory = findResourcesSubdirectory(module)
                        if (resourcesSubdirectory != null) {
                            addFile(resourcesSubdirectory, file, it.subdirectory)
                        }
                    } else {
                        if (it.fileNameTemplate.endsWith("Adapter")) {
                            if (addRecyclerView) {
                                val codeSubdirectory = findCodeSubdirectory("$packageName.adapter", module, it.sourceSet)
                                if (codeSubdirectory != null) {
                                    addFile(codeSubdirectory, file, it.subdirectory)
                                }
                            }
                        }
                        else {
                            val codeSubdirectory = findCodeSubdirectory(packageName, module, it.sourceSet)
                            if (codeSubdirectory != null) {
                                addFile(codeSubdirectory, file, it.subdirectory)
                            }
                        }
                    }
                }
            val appComponent = FileUtils.getClassByName("AppComponent", project)
            if (appComponent != null) {
                PsiDocumentManager.getInstance(project).getDocument(appComponent.containingFile)?.let {
                    val lastImportIndex = it.text.lastIndexOf("import")
                    val lastIndexImportEnd = it.text.indexOf('\n', lastImportIndex)
                    val lastImport = it.text.substring(lastImportIndex, lastIndexImportEnd)
                    val last = it.text.indexOfLast{ it == '}'}
                    WriteCommandAction.runWriteCommandAction(project
                    ) {
                        it.replaceString(
                            last,
                            last,
                            "\n    fun plus(${screenName.decapitalize()}Module: ${screenName.toCamelCase()}Module): ${screenName.toCamelCase()}Component\n"
                        )
                        it.replaceString(
                            lastImportIndex,
                            lastIndexImportEnd,
                            "${lastImport}\nimport $packageName.*"
                        )
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
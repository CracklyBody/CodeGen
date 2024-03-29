package com.maxplugin.codegen.model

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache
import com.maxplugin.codegen.util.FileType
import com.maxplugin.codegen.util.toSnakeCase
import java.io.Serializable
import javax.inject.Inject

private const val UNNAMED_ELEMENT = "UnnamedElement"
const val DEFAULT_SOURCE_SET = "main"

data class ScreenElement(
    var name: String = "",
    var template: String = "",
    var fileType: FileType = FileType.KOTLIN,
    var fileNameTemplate: String = "",
    var relatedAndroidComponent: AndroidComponent = AndroidComponent.NONE,
    var categoryId: Int = 0,
    var subdirectory: String = "",
    var sourceSet: String = DEFAULT_SOURCE_SET,
    val project: Project
) : Serializable {

    override fun toString() = name

    fun body(
        screenName: String,
        packageName: String,
        basePackagePath: String,
        androidComponent: String,
        customVariablesMap: Map<CustomVariable, String>
    ) =
        template
            .replaceVariables(screenName, packageName, basePackagePath, androidComponent)
            .replaceCustomVariables(customVariablesMap)

    fun fileName(
        screenName: String,
        packageName: String,
        basePackagePath: String,
        androidComponent: String,
        customVariablesMap: Map<CustomVariable, String>
    ) =
        fileNameTemplate
            .replaceVariables(screenName, packageName, basePackagePath, androidComponent)
            .replaceCustomVariables(customVariablesMap)
            .run {
                if (fileType == FileType.LAYOUT_XML)
                    toLowerCase()
                else
                    this
            }

    private fun String.replaceVariables(
        screenName: String,
        packageName: String,
        basePackagePath: String,
        androidComponent: String
    ) =
        replace(Variable.NAME.value, screenName)
            .replace(Variable.NAME_SNAKE_CASE.value, screenName.toSnakeCase())
            .replace(Variable.NAME_LOWER_CASE.value, screenName.decapitalize())
            .replace(Variable.SCREEN_ELEMENT.value, name)
            .replace(Variable.PACKAGE_NAME.value, packageName)
            .replace(Variable.BASE_PACKAGE_NAME.value, basePackagePath)
            .replace(Variable.ANDROID_COMPONENT_NAME.value, androidComponent)
            .replace(Variable.ANDROID_COMPONENT_NAME_LOWER_CASE.value, androidComponent.decapitalize())
            .replace(Regex("(?<!\\w)${Variable.FIND_CLASS.value}\\w+")) { matchResult ->
                val className = matchResult.value.replace(Variable.FIND_CLASS.value, "")
                getClassPathByName(className, project)?: className
            }

    private fun String.replaceCustomVariables(variables: Map<CustomVariable, String>): String {
        var updatedString = this
        variables.forEach { (variable, text) ->
            updatedString = updatedString.replace("%${variable.name}%", text)
        }
        return updatedString
    }

    private fun getClassByName(className: String, project: Project): PsiClass? {
        return try {
//            FilenameIndex.getFilesByName(project, "UpScreenMessage", GlobalSearchScope.everythingScope(project)).getOrNull(0) as? PsiClass
//                ?:
            PsiShortNamesCache.getInstance(project).getClassesByName(className, GlobalSearchScope.projectScope(project)).getOrNull(0)
        } catch (e: Exception) {
            null
        }
    }
    private fun getClassPathByName(className: String, project: Project): String? {
        return getClassByName(className, project)?.qualifiedName
    }
}

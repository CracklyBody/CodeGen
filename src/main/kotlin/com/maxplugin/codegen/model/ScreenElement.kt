package com.maxplugin.codegen.model

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache
import com.maxplugin.codegen.model.template.mvp.adapter.*
import com.maxplugin.codegen.util.FileType
import com.maxplugin.codegen.util.FileUtils
import com.maxplugin.codegen.util.toSnakeCase
import java.io.Serializable

private const val UNNAMED_ELEMENT = "UnnamedElement"
const val DEFAULT_SOURCE_SET = "main"

data class ScreenElement(
    var name: String = "",
    var template: String = "",
    var fileType: FileType = FileType.KOTLIN,
    var fileNameTemplate: String = "",
    var relatedAndroidComponent: AndroidComponent = AndroidComponent.NONE,
    var architectureType: ArchitectureType = ArchitectureType.NONE,
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
        customVariablesMap: Map<CustomVariable, String>,
        addRecyclerView: Boolean
    ) =
        template
            .replaceVariables(screenName, packageName, basePackagePath, androidComponent,addRecyclerView)
            .replaceCustomVariables(customVariablesMap)

    fun fileName(
        screenName: String,
        packageName: String,
        basePackagePath: String,
        androidComponent: String,
        customVariablesMap: Map<CustomVariable, String>,
        addRecyclerView: Boolean
    ) =
        fileNameTemplate
            .replaceVariables(screenName, packageName, basePackagePath, androidComponent, addRecyclerView)
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
        androidComponent: String,
        needRecyclerView: Boolean
    ) =
        replace(Variable.RECYCLER_VIEW_LAYOUT.value, if(needRecyclerView) ADAPTER_LAYOUT_IMPL else "")
            .replace(Variable.RECYCLER_VIEW_ADAPTER_FRAGMENT_IMPORT.value, if(needRecyclerView) ADAPTER_FRAGMENT_IMPORT else "")
            .replace(Variable.RECYCLER_VIEW_ADAPTER_DECLARATION.value, if(needRecyclerView) ADAPTER_FRAGMENT_DECLARATION else "")
            .replace(Variable.RECYCLER_VIEW_LAYOUT_DECLARATION.value, if(needRecyclerView) ADAPTER_LAYOUT_FRAGMENT_DECLARATION else "")
            .replace(Variable.RECYCLER_VIEW_ADAPTER_SWAP_ITEMS_IMPL.value, if(needRecyclerView) ADAPTER_FRAGMENT_SHOW_ITEMS_IMPL_ITEMS_IMPL else "")
            .replace(Variable.RECYCLER_VIEW_ADAPTER_SWAP_ITEMS_CONTRACT.value, if(needRecyclerView) ADAPTER_CONTRACT_SHOW_ITEMS else "")
            .replace(Variable.RECYCLER_VIEW_ADAPTER_SWAP_ITEMS_PRESENTER.value, if(needRecyclerView) ADAPTER_PRESENTER_SHOW_ITEMS_IMPL else "")
            .replace(Variable.RECYCLER_VIEW_ADAPTER_ON_ITEM_CLICKED_CONTRACT.value, if(needRecyclerView) ADAPTER_CONTRACT_ON_ITEM_CLICKED else "")
            .replace(Variable.RECYCLER_VIEW_ADAPTER_ON_ITEM_CLICKED_PRESENTER_IMPL.value, if(needRecyclerView) ADAPTER_PRESENTER_ON_ITEM_CLICKED_IMPL else "")
            .replace(Variable.NAME.value, screenName)
            .replace(Variable.NAME_SNAKE_CASE.value, screenName.toSnakeCase())
            .replace(Variable.NAME_LOWER_CASE.value, screenName.decapitalize())
            .replace(Variable.SCREEN_ELEMENT.value, name)
            .replace(Variable.PACKAGE_NAME.value, packageName)
            .replace(Variable.BASE_PACKAGE_NAME.value, basePackagePath)
            .replace(Variable.ANDROID_COMPONENT_NAME.value, androidComponent)
            .replace(Variable.ANDROID_COMPONENT_NAME_LOWER_CASE.value, androidComponent.decapitalize())
            .replace(Regex("(?<!\\w)${Variable.FIND_CLASS.value}\\w+")) { matchResult ->
                val className = matchResult.value.replace(Variable.FIND_CLASS.value, "")
                FileUtils.getClassPathByName(className, project)?: className
            }

    private fun String.replaceCustomVariables(variables: Map<CustomVariable, String>): String {
        var updatedString = this
        variables.forEach { (variable, text) ->
            updatedString = updatedString.replace("%${variable.name}%", text)
        }
        return updatedString
    }
}

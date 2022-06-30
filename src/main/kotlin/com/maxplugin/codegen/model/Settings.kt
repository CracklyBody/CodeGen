package com.maxplugin.codegen.model

import com.intellij.openapi.project.Project
import com.maxplugin.codegen.data.PersistentSettingsData
import com.maxplugin.codegen.model.template.mvp.DEFAULT_CONTRACT_MVP_TEMPLATE
import com.maxplugin.codegen.model.template.mvp.DEFAULT_DI_MVP_TEMPLATE
import com.maxplugin.codegen.model.template.mvp.DEFAULT_FRAGMENT_MVP_TEMPLATE
import com.maxplugin.codegen.model.template.mvp.DEFAULT_PRESENTER_MVP_TEMPLATE
import com.maxplugin.codegen.util.FileType
import java.io.Serializable

val DEFAULT_ACTIVITY_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}\n\nimport androidx.appcompat.app.AppCompatActivity\n\nclass ${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value} : AppCompatActivity"
val DEFAULT_FRAGMENT_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}\n\nimport androidx.fragment.app.Fragment\n\nclass ${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value} : Fragment"
val DEFAULT_VIEW_MODEL_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}\n\nimport androidx.lifecycle.ViewModel\n\nclass ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value} : ViewModel()"
val DEFAULT_VIEW_MODEL_TEST_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}\n\nclass ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}"
val DEFAULT_REPOSITORY_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}.repository\n\nclass ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}"

private fun defaultScreenElements(project: Project) = mutableListOf(
    ScreenElement("Activity", DEFAULT_ACTIVITY_TEMPLATE, FileType.KOTLIN, "${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value}", AndroidComponent.ACTIVITY, project = project),
    ScreenElement("Fragment", DEFAULT_FRAGMENT_MVP_TEMPLATE, FileType.KOTLIN, "${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value}", AndroidComponent.FRAGMENT, categoryId = 0, project = project),
    ScreenElement("Presenter", DEFAULT_PRESENTER_MVP_TEMPLATE, FileType.KOTLIN, "${Variable.NAME.value}Presenter", AndroidComponent.FRAGMENT, categoryId = 0, project = project),
    ScreenElement("DI", DEFAULT_DI_MVP_TEMPLATE, FileType.KOTLIN, "${Variable.NAME.value}DI", AndroidComponent.FRAGMENT, categoryId = 0, project = project),
    ScreenElement("Contract", DEFAULT_CONTRACT_MVP_TEMPLATE, FileType.KOTLIN, "${Variable.NAME.value}Contract", AndroidComponent.FRAGMENT, categoryId = 0, project = project),
    ScreenElement("ViewModel", DEFAULT_VIEW_MODEL_TEMPLATE, FileType.KOTLIN, FileType.KOTLIN.defaultFileName, project = project),
    ScreenElement("ViewModelTest", DEFAULT_VIEW_MODEL_TEST_TEMPLATE, FileType.KOTLIN, FileType.KOTLIN.defaultFileName, sourceSet = "test", project = project),
    ScreenElement("Repository", DEFAULT_REPOSITORY_TEMPLATE, FileType.KOTLIN, FileType.KOTLIN.defaultFileName, subdirectory = "repository", project = project),
    ScreenElement("layout", FileType.LAYOUT_XML.defaultTemplate, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName, project = project)
)

private fun defaultArchitectureTypes() = mutableListOf(
    ArchitectureType(0, "MVP"),
    ArchitectureType(1, "MVVM"),
    ArchitectureType(2, "MVI"),
)

data class Settings(
    val project: Project,
    val userSettingsState: PersistentSettingsData,
    var screenElements: MutableList<ScreenElement> = defaultScreenElements(project),
    var architectureTypes: MutableList<ArchitectureType> = defaultArchitectureTypes()
) : Serializable

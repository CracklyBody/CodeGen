package com.maxplugin.codegen.data.repository

import com.intellij.openapi.project.Project
import com.maxplugin.codegen.data.ScreenGeneratorComponent
import com.maxplugin.codegen.model.ArchitectureType
import com.maxplugin.codegen.model.CategoryScreenElements
import com.maxplugin.codegen.model.Settings
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val project: Project) {

    @Inject
    lateinit var screenGeneratorComponent: ScreenGeneratorComponent

    fun loadCategoriesWithScreenElements(): List<CategoryScreenElements> {
        val settings = loadSettings()
        return settings.architectureTypes.map { category ->
            CategoryScreenElements(
                category,
                settings.screenElements.filter { it.categoryId == category.id }
            )
        }
    }

    fun update(settings: Settings) = screenGeneratorComponent.run {
        this.settings = settings
    }

    fun loadArchitectureType(): List<ArchitectureType> = loadSettings().architectureTypes

    fun loadScreenElements(categoryId: Int) =
        loadSettings().screenElements.filter { it.categoryId == categoryId }

    private fun loadSettings() = screenGeneratorComponent.settings
}

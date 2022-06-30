package com.maxplugin.codegen.main

import com.maxplugin.codegen.model.AndroidComponent
import com.maxplugin.codegen.model.ArchitectureType
import com.maxplugin.codegen.model.CustomVariable
import com.maxplugin.codegen.model.ProjectModule

sealed class NewScreenAction {
    data class OkClicked(
        val packageName: String,
        val basePackagePath: String,
        val screenName: String,
        val androidComponentIndex: Int,
        val module: ProjectModule,
        val architectureType: ArchitectureType,
        val customVariablesMap: Map<CustomVariable, String>,
        val addRecyclerView: Boolean
    ) : NewScreenAction()

    data class CategoryIndexChanged(val index: Int) : NewScreenAction()

    object Init : NewScreenAction()
}

package com.maxplugin.codegen.main

import com.maxplugin.codegen.model.ArchitectureType
import com.maxplugin.codegen.model.ProjectModule


data class NewScreenState(
    val packageName: String = "",
    val basePackagePath: String = "",
    val projectModules: List<ProjectModule> = emptyList(),
    val selectedModule: ProjectModule? = null,
    val categories: List<ArchitectureType> = emptyList(),
    val selectedArchitectureType: ArchitectureType? = null
)

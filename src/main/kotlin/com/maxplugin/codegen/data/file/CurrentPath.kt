package com.maxplugin.codegen.data.file

import com.maxplugin.codegen.model.ProjectModule

data class CurrentPath(val path: String, val isDirectory: Boolean, val projectModule: ProjectModule)

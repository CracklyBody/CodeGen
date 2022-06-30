package com.maxplugin.codegen.model

data class ProjectModule(
    val name: String,
    val nameWithoutPrefix: String
) {
    override fun toString() = nameWithoutPrefix
}

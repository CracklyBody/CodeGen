package com.maxplugin.codegen.model

private const val DEFAULT_NAME = "UnnamedCategory"

data class ArchitectureType(
    var id: Int = 0,
    var name: String = "",
    var customVariables: List<CustomVariable> = emptyList()
) {

    override fun toString() = name

    companion object {
        fun getDefault(id: Int) = ArchitectureType(id, DEFAULT_NAME)
    }
}

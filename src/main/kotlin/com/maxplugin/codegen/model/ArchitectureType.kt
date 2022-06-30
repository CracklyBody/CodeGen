package com.maxplugin.codegen.model

private const val DEFAULT_NAME = "UnnamedCategory"

enum class ArchitectureType(
    var id: Int = 0,
    var typeName: String = "",
    var customVariables: List<CustomVariable> = emptyList()
) {
    NONE(0, "NONE"),
    MVP(1, "MVP"),
    MVVM(2, "MVVM"),
    MVI(3, "MVI");
    override fun toString() = typeName
}

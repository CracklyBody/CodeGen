package com.maxplugin.codegen.data.file

import com.maxplugin.codegen.util.FileType


data class File(
    val name: String,
    val content: String,
    val fileType: FileType
)

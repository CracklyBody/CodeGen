package com.maxplugin.codegen.data.file

import com.maxplugin.codegen.data.repository.SourceRootRepository
import javax.inject.Inject

class PackageExtractor @Inject constructor(
    private val currentPath: CurrentPath?,
    private val sourceRootRepository: SourceRootRepository
) {

    fun extractFromCurrentPath(): String {
        val sourceRootPath = currentPath?.let { sourceRootRepository.findCodeSourceRoot(currentPath.projectModule)?.path }
        return if (currentPath != null && sourceRootPath != null && currentPath.path != sourceRootPath && currentPath.path.contains(
                sourceRootPath
            )
        ) {
            currentPath.path.removePrefix("$sourceRootPath/")
                .removeFilePath(currentPath.isDirectory)
                .replace("/", ".")
        } else {
            ""
        }
    }

    private fun String.removeFilePath(isDirectory: Boolean) =
        if (!isDirectory) {
            removeRange(indexOfLast { it == '/' }, length)
        } else {
            this
        }
}

package com.maxplugin.codegen.util

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache

object FileUtils {
    fun getClassByName(className: String, project: Project): PsiClass? {
        return try {
//            FilenameIndex.getFilesByName(project, "UpScreenMessage", GlobalSearchScope.everythingScope(project)).getOrNull(0) as? PsiClass
//                ?:
            PsiShortNamesCache.getInstance(project).getClassesByName(className, GlobalSearchScope.projectScope(project)).getOrNull(0)
        } catch (e: Exception) {
            null
        }
    }
    fun getClassPathByName(className: String, project: Project): String? {
        return getClassByName(className, project)?.qualifiedName
    }
}

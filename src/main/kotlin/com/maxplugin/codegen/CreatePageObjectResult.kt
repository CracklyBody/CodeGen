package com.maxplugin.codegen

import android.databinding.tool.ext.toCamelCase
import com.intellij.openapi.project.Project
import com.intellij.patterns.PsiJavaPatterns.psiClass
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache


data class CreatePageObjectResult(
    val className: String,
    val project: Project,
    var packageName: String
) {
    fun getPageObjectFragmentClassFileName(): String {
        return "${className}Fragment.kt"
    }

    fun getPageObjectPresenterClassFileName(): String {
        return "${className}Presenter.kt"
    }

    fun getPageObjectDIClassFileName(): String {
        return "${className}DI.kt"
    }

    fun getPageObjectContractClassFileName(): String {
        return "${className}Contract.kt"
    }

    fun getPageObjectClassFileName(): String {
        return "${className}.kt"
    }

    fun getClassByName(className: String): PsiClass? {
        return try {
           FilenameIndex.getFilesByName(project, "UpScreenMessage", GlobalSearchScope.everythingScope(project)).getOrNull(0) as? PsiClass ?:
           PsiShortNamesCache.getInstance(project).getClassesByName(className, GlobalSearchScope.projectScope(project)).getOrNull(0)
        } catch (e: Exception) {
            null
        }
    }
    fun getClassPathByName(className: String): String {
        return getClassByName(className)?.qualifiedName ?: ""
    }

}
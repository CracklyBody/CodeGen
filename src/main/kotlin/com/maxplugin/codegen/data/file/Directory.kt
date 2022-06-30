package com.maxplugin.codegen.data.file

import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.codeStyle.CodeStyleManager
import com.maxplugin.codegen.CodeGenAction
import com.maxplugin.codegen.util.FileType
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.idea.core.ShortenReferences
import org.jetbrains.kotlin.idea.formatter.commitAndUnblockDocument
import org.jetbrains.kotlin.idea.util.application.executeWriteCommand
import org.jetbrains.kotlin.psi.KtFile

class Directory(
    private val project: Project,
    private val psiDirectory: PsiDirectory
) {

    companion object {
        private const val COMMAND_NAME = "CodeGenActionCommand"

    }

    fun findSubdirectory(name: String) = psiDirectory.findSubdirectory(name)?.let { Directory(project, it) }

    fun createSubdirectory(name: String) = Directory(project, psiDirectory.createSubdirectory(name))

    fun addFile(file: File) {
        val language = when (file.fileType) {
            FileType.KOTLIN -> KotlinLanguage.INSTANCE
            FileType.LAYOUT_XML -> XMLLanguage.INSTANCE
        }
        val psiFile = PsiFileFactory.getInstance(project).createFileFromText("${file.name}.${file.fileType.extension}", language, file.content)
        if (file.fileType == FileType.LAYOUT_XML) {
            psiDirectory.add(psiFile)
        } else {
            project.executeWriteCommand(COMMAND_NAME) {
                val addedFile = psiDirectory.add(psiFile) as KtFile
                addedFile.commitAndUnblockDocument()
                ShortenReferences.DEFAULT.process(addedFile)
                CodeStyleManager.getInstance(project).reformat(addedFile)
            }
        }
    }
}

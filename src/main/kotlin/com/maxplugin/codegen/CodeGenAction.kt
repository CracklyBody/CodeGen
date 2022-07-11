package com.maxplugin.codegen

import com.intellij.dvcs.ui.NewBranchAction.icon
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.fileChooser.FileSystemTree
import com.intellij.openapi.fileChooser.ex.FileChooserKeys
import com.intellij.openapi.fileChooser.ex.FileSystemTreeImpl
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.fileTypes.UnknownFileType
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.ui.UIBundle
import com.maxplugin.codegen.data.file.CurrentPath
import com.maxplugin.codegen.model.ProjectModule
import com.maxplugin.codegen.util.getPageObjectContractClassText
import com.maxplugin.codegen.util.getPageObjectDIClassText
import com.maxplugin.codegen.util.getPageObjectFragmentClassText
import com.maxplugin.codegen.util.getPageObjectPresenterClassText
import org.jetbrains.kotlin.idea.core.ShortenReferences
import org.jetbrains.kotlin.idea.core.getPackage
import org.jetbrains.kotlin.idea.formatter.commitAndUnblockDocument
import org.jetbrains.kotlin.idea.util.application.executeWriteCommand
import org.jetbrains.kotlin.j2k.getContainingMethod
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtPsiFactory
import java.io.IOException
import java.net.URL


class CodeGenAction : AnAction() {

    companion object {
        private const val COMMAND_NAME = "CodeGenActionCommand"

    }

    private val KOTLIN_FILE_TYPE = FileTypeManager.getInstance().getFileTypeByExtension(".kt")

    override fun actionPerformed(e: AnActionEvent) {
        val dialog = e.project?.let { CreatePageObjectDialog(it) }?.also { it.show() }
        if (dialog?.isOK == true) {
//            val tree = e.getData(FileSystemTree.DATA_KEY)
//            if (tree != null) {
//                e.presentation.isEnabled = true
////                val addedFile = tree.add(pageObjectKtFile) as KtFile
//                update(tree, e)
//                actionPerformed(tree, e)
//            } else {
//                e.presentation.isEnabled = false
//            }
//            val file = e.getData(CommonDataKeys.PSI_FILE) as KtFile
            val file = e.getDataContext().getData(DataConstants.VIRTUAL_FILE) as VirtualFile

            val currentPath = file.let {
                val moduleName = ModuleUtil.findModuleForFile(it, e.project!!)?.name ?: ""
                val projectModule = ProjectModule(moduleName, moduleName.replace("${e.project!!.name}.", ""))
                CurrentPath(it.path, it.isDirectory, projectModule)
            }
            handleDialogResult(dialog.getData(), file)
        }
    }


    private fun handleDialogResult(result: CreatePageObjectResult, virtualFile: VirtualFile) {
        val project = result.project
        val directory = PsiManager.getInstance(project).findDirectory(virtualFile)!!
        val packageName = directory.getPackage()?.qualifiedName ?: ""
        val newResult = result.copy(packageName = packageName)



//        val javaFile = file[0].containingFile as PsiJavaFile
//        val psiPackage = JavaPsiFacade.getInstance(project).findPackage(javaFile.packageName)

        val pageObjectFragmentClassText = newResult.getPageObjectFragmentClassText()
        val classFragmentFileName = result.getPageObjectFragmentClassFileName()

        val pageObjectContractClassText = newResult.getPageObjectContractClassText()
        val classContractFileName = result.getPageObjectContractClassFileName()

        val pageObjectDIClassText = newResult.getPageObjectDIClassText()
        val classDIFileName = result.getPageObjectDIClassFileName()

        val pageObjectPresenterClassText = newResult.getPageObjectPresenterClassText()
        val classPresenterFileName = result.getPageObjectPresenterClassFileName()

        createFile(project, classFragmentFileName, pageObjectFragmentClassText, directory)
        createFile(project, classContractFileName, pageObjectContractClassText, directory)
        createFile(project, classDIFileName, pageObjectDIClassText, directory)
        createFile(project, classPresenterFileName, pageObjectPresenterClassText, directory)
    }

    private fun createFile(
        project: Project,
        classFragmentFileName: String,
        pageObjectClassText: String,
        directory: PsiDirectory
    ) {
        val ktPsiFactory = KtPsiFactory(project)
        val pageObjectFragmentKtFile = ktPsiFactory.createFile(classFragmentFileName, pageObjectClassText)
        executeWriteFile(project, directory, pageObjectFragmentKtFile)
    }

    private fun executeWriteFile(
        project: Project,
        directory: PsiDirectory,
        pageObjectFragmentKtFile: KtFile
    ) {
        project.executeWriteCommand(COMMAND_NAME) {
            val addedFile = directory.add(pageObjectFragmentKtFile) as KtFile
    //            val rootDir = ClassUtil.sourceRoot(directory)
            addedFile.getContainingMethod()
            addedFile.commitAndUnblockDocument()
            ShortenReferences.DEFAULT.process(addedFile)
            CodeStyleManager.getInstance(project).reformat(addedFile)
        }
    }


    protected fun update(fileSystemTree: FileSystemTree, e: AnActionEvent) {
        val presentation: Presentation = e.presentation
        presentation.setVisible(true)
        val selectedFile: VirtualFile? = fileSystemTree.newFileParent
        presentation.setEnabled(selectedFile != null)
        // FORK DIFF (got rid of layered "new" icon because it's ugly)
        presentation.setIcon(icon)
    }

    protected fun actionPerformed(fileSystemTree: FileSystemTree, e: AnActionEvent) {
        var initialContent = e.getData(FileChooserKeys.NEW_FILE_TEMPLATE_TEXT)
        // FORK DIFF (don't really care if initial content is null)
        if (initialContent == null) initialContent = ""
        e.project?.let { createNewFile(fileSystemTree, it, KOTLIN_FILE_TYPE, initialContent) }
    }

    fun kotlinCompilerIsOnClassPath(): Boolean {
        // TODO in case of IJ update, remove old and download new version of compiler jar
        return isOnClasspath("org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler")
    }

    fun isOnClasspath(className: String): Boolean {
        val resource: URL = this::class.java.getClassLoader().getResource(className.replace(".", "/") + ".class")
        return resource != null
    }

    private fun createNewFile(
        fileSystemTree: FileSystemTree,
        project: Project,
        fileType: FileType,
        initialContent: String
    ) {
        var file: VirtualFile = fileSystemTree.newFileParent ?: return
        if (!file.isDirectory()) file = file.getParent()
        if (file == null) return
        var newFileName: String?
        while (true) {
            newFileName = Messages.showInputDialog(
                UIBundle.message("create.new.file.enter.new.file.name.prompt.text"),
                UIBundle.message("new.file.dialog.title"), Messages.getQuestionIcon()
            )
            if (newFileName == null) {
                return
            }
            if ("" == newFileName.trim { it <= ' ' }) {
                Messages.showMessageDialog(
                    UIBundle.message("create.new.file.file.name.cannot.be.empty.error.message"),
                    UIBundle.message("error.dialog.title"), Messages.getErrorIcon()
                )
                continue
            }
            val failReason = createNewFile(
                project,
                fileSystemTree as FileSystemTreeImpl,
                file,
                newFileName,
                fileType,
                initialContent
            )
            if (failReason != null) {
                Messages.showMessageDialog(
                    UIBundle.message("create.new.file.could.not.create.file.error.message", newFileName),
                    UIBundle.message("error.dialog.title"), Messages.getErrorIcon()
                )
                continue
            }
            return
        }
    }

    // modified copy of com.intellij.openapi.fileChooser.ex.FileSystemTreeImpl.createNewFile()
    private fun createNewFile(
        project: Project, fileSystemTree: FileSystemTreeImpl,
        parentDirectory: VirtualFile, newFileName: String,
        fileType: FileType?, initialContent: String?
    ): Exception? {
        val failReason = arrayOf<Exception?>(null)
        CommandProcessor.getInstance().executeCommand(
            project, Runnable {
                ApplicationManager.getApplication().runWriteAction(object : Runnable {
                    override fun run() {
                        try {
                            var newFileNameWithExtension = newFileName
                            if (fileType != null && fileType !is UnknownFileType) {
                                newFileNameWithExtension =
                                    if (newFileName.endsWith('.' + fileType.getDefaultExtension())) newFileName else newFileName + '.' + fileType.getDefaultExtension()
                            }
                            val file: VirtualFile = parentDirectory.createChildData(this, newFileNameWithExtension)
                            VfsUtil.saveText(file, initialContent ?: "")
                            fileSystemTree.updateTree()
                            fileSystemTree.select(file, null)
                        } catch (e: IOException) {
                            failReason[0] = e
                        }
                    }
                })
            },
            UIBundle.message("file.chooser.create.new.file.command.name"),
            null
        )
        return failReason[0]
    }
}
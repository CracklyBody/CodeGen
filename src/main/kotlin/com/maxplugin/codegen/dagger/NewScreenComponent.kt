package com.maxplugin.codegen.dagger

import com.intellij.openapi.project.Project
import com.maxplugin.codegen.data.file.CurrentPath
import com.maxplugin.codegen.main.NewScreenDialog
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NewScreenModule::class
    ]
)
interface NewScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance project: Project, @BindsInstance currentPath: CurrentPath?): NewScreenComponent
    }

    fun inject(dialog: NewScreenDialog)
}

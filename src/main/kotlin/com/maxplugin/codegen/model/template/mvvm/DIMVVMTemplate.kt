package com.maxplugin.codegen.model.template.mvvm

import com.maxplugin.codegen.model.Variable

val DEFAULT_DI_MVVM_TEMPLATE = """package ${Variable.PACKAGE_NAME.value}
    
@dagger.Subcomponent(modules = [${Variable.NAME.value}Module::class, ${Variable.NAME.value}ViewModelModule::class])
interface ${Variable.NAME.value}Component {

    fun inject(activity: ${Variable.NAME.value}Fragment)
}

@dagger.Module
class ${Variable.NAME.value}Module() {}

@dagger.Module
abstract class ${Variable.NAME.value}ViewModelModule {
    @dagger.Binds
    @dagger.multibindings.IntoMap
    @${Variable.FIND_CLASS.value}ViewModelKey(${Variable.NAME.value}ViewModel::class)
    internal abstract fun viewModel(viewModel: ${Variable.NAME.value}ViewModel): androidx.lifecycle.ViewModel
}

        """
package com.maxplugin.codegen.dagger

import com.maxplugin.codegen.core.Reducer
import com.maxplugin.codegen.main.NewScreenAction
import com.maxplugin.codegen.main.NewScreenEffect
import com.maxplugin.codegen.main.NewScreenState
import com.maxplugin.codegen.reducer.InitReducer
import com.maxplugin.codegen.reducer.OkClickedReducer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Module
abstract class NewScreenModule {

    @Binds
    @IntoMap
    @NewScreenActionKey(NewScreenAction.Init::class)
    abstract fun bindInitReducer(reducer: InitReducer): Reducer

    @Binds
    @IntoMap
    @NewScreenActionKey(NewScreenAction.OkClicked::class)
    abstract fun bindOkClickedReducer(reducer: OkClickedReducer): Reducer

//    @Binds
//    @IntoMap
//    @NewScreenActionKey(NewScreenAction.CategoryIndexChanged::class)
//    abstract fun bindCategoryIndexChangedReducer(reducer: CategoryIndexChangedReducer): Reducer

    companion object {

        @Provides
        @Singleton
        fun provideState() = MutableStateFlow(NewScreenState())

        @Provides
        @Singleton
        fun provideEffect() = MutableSharedFlow<NewScreenEffect>()

        @Provides
        @Singleton
        fun provideAction() = MutableSharedFlow<NewScreenAction>()
    }
}

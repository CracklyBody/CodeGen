package com.maxplugin.codegen.main

import com.maxplugin.codegen.core.Reducer
import com.maxplugin.codegen.core.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class NewScreenViewModel @Inject constructor(
    state: MutableStateFlow<NewScreenState>,
    effect: MutableSharedFlow<NewScreenEffect>,
    actionFlow: MutableSharedFlow<NewScreenAction>,
    reducers: Map<Class<out NewScreenAction>, @JvmSuppressWildcards Provider<Reducer>>,
) : ViewModel<NewScreenState, NewScreenEffect, NewScreenAction>(state, effect, actionFlow, reducers) {

    init {
        launch { actionFlow.emit(NewScreenAction.Init) }
    }
}

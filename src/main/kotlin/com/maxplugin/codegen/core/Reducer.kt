package com.maxplugin.codegen.core

sealed interface Reducer {

    interface Suspend<T> : Reducer {
        suspend operator fun invoke(action: T)
    }

    interface Blocking<T> : Reducer {
        operator fun invoke(action: T)
    }
}

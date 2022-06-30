package com.maxplugin.codegen.dagger

import com.maxplugin.codegen.main.NewScreenAction
import kotlin.reflect.KClass
import dagger.MapKey


@MapKey
annotation class NewScreenActionKey(val value: KClass<out NewScreenAction>)

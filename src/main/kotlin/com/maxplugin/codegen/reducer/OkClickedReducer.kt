package com.maxplugin.codegen.reducer


import com.maxplugin.codegen.core.Reducer
import com.maxplugin.codegen.data.PersistentSettingsData
import com.maxplugin.codegen.data.file.FileCreator
import com.maxplugin.codegen.data.file.WriteActionDispatcher
import com.maxplugin.codegen.main.NewScreenAction
import com.maxplugin.codegen.main.NewScreenEffect
import com.maxplugin.codegen.model.AndroidComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class OkClickedReducer @Inject constructor(
    private val effect: MutableSharedFlow<NewScreenEffect>,
    private val fileCreator: FileCreator,
    private val writeActionDispatcher: WriteActionDispatcher,
) : Reducer.Suspend<NewScreenAction.OkClicked> {

    override suspend fun invoke(action: NewScreenAction.OkClicked) {
        writeActionDispatcher.dispatch {
            fileCreator.createScreenFiles(
                action.packageName,
                action.basePackagePath,
                action.screenName,
                AndroidComponent.values()[action.androidComponentIndex],
                action.module,
                action.architectureType,
                action.customVariablesMap,
            )
        }
        PersistentSettingsData.getInstance().state?.projectBasePath = action.basePackagePath
        PersistentSettingsData.getInstance().state?.lastChosenArchitectureId = action.architectureType.id
        PersistentSettingsData.getInstance().state?.lastChosenAndroidComponentId = action.androidComponentIndex
        effect.emit(NewScreenEffect.Close)
    }
}

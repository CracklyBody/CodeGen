package com.maxplugin.codegen.reducer

import com.intellij.openapi.project.Project
import com.maxplugin.codegen.core.Reducer
import com.maxplugin.codegen.data.PersistentSettingsData
import com.maxplugin.codegen.data.file.CurrentPath
import com.maxplugin.codegen.data.file.PackageExtractor
import com.maxplugin.codegen.data.repository.ModuleRepository
import com.maxplugin.codegen.data.repository.SettingsRepository
import com.maxplugin.codegen.main.NewScreenAction
import com.maxplugin.codegen.main.NewScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class InitReducer @Inject constructor(
    private val state: MutableStateFlow<NewScreenState>,
    private val packageExtractor: PackageExtractor,
    private val moduleRepository: ModuleRepository,
    private val currentPath: CurrentPath?,
    private val settingsRepository: SettingsRepository,
) : Reducer.Blocking<NewScreenAction.Init> {

    override fun invoke(action: NewScreenAction.Init) =
        state.update {
            it.copy(
                packageName = packageExtractor.extractFromCurrentPath(),
                basePackagePath = PersistentSettingsData.getInstance().state?.projectBasePath ?: packageExtractor.extractFromCurrentPath(),
                projectModules = moduleRepository.getAllModules(),
                selectedModule = currentPath?.projectModule,
                categories = settingsRepository.loadArchitectureType(),
                selectedArchitectureType = settingsRepository.loadArchitectureType().first()
            )
        }
}

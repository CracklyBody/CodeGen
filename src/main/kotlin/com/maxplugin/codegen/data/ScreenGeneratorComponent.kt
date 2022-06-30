package com.maxplugin.codegen.data

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import com.maxplugin.codegen.model.Settings
import java.io.Serializable
import javax.inject.Inject

//@State(
//    name = "ScreenGeneratorConfiguration",
//    storages = [Storage(value = "screenGeneratorConfiguration.xml")]
//)
class ScreenGeneratorComponent @Inject constructor(project: Project) {


    var settings: Settings = Settings(project, PersistentSettingsData.getInstance().state ?: PersistentSettingsData())

}

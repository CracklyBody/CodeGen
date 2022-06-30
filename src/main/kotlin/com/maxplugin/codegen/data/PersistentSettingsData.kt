package com.maxplugin.codegen.data

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import java.io.Serializable

@State(
    name = "PersistentSettingsData",
    storages = [Storage(value = "persistentSettingsData.xml")]
)
class PersistentSettingsData(
    var projectBasePath: String = "",
    var lastChosenArchitectureId: Int = 0,
    var lastChosenAndroidComponentId: Int = 0,
) : Serializable, PersistentStateComponent<PersistentSettingsData> {

    companion object {
        @JvmStatic
        fun getInstance(): PersistentStateComponent<PersistentSettingsData> {
            return ServiceManager.getService(PersistentSettingsData::class.java)
        }
    }

    override fun getState(): PersistentSettingsData = this

    override fun loadState(state: PersistentSettingsData) {
        XmlSerializerUtil.copyBean(state, this)
    }
}
